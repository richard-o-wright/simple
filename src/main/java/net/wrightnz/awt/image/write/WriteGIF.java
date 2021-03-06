package net.wrightnz.awt.image.write;

import java.awt.Image;
import java.awt.image.PixelGrabber;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;


/**
 * WriteGIF is a class that takes an Image and saves it to
 * a GIF format file.
 * Based upon gifsave.c, which was written and released by 
 * Sverre H. Huseby. Ported to Java by Adam Doppelt of Brown 
 * University.  
 */
public class WriteGIF {

   private short width_, height_;
   private int numColors_;
   private byte pixels_[], colors_[];
    
    public static void doIt(Image img, String fname) throws IOException {
        // encode the image as a GIF
        WriteGIF encode = new WriteGIF(img);
        BufferedOutputStream output = new BufferedOutputStream(
            new FileOutputStream(fname));
        try{
            encode.write(output);
        }
        finally{
            output.close();
        }
    }
   
   public WriteGIF(Image image) throws IOException{
      width_ = (short)image.getWidth(null);
      height_ = (short)image.getHeight(null);

      int values[] = new int[width_ * height_];
      PixelGrabber grabber = new PixelGrabber(image, 0, 0, width_, height_, values, 0, width_);
   
      try{
         if(grabber.grabPixels() == false){
            throw new IOException("Grabber returned false: " + grabber.status());
         }
      }
      catch (InterruptedException e){
         throw new IOException("Grabber was interrupted: " + e);
      }
   
      //--------------------------------------------------------------
      // TODO -> Possible Speed up - do it a row at a time, a la ACME
      //--------------------------------------------------------------
      byte r[][] = new byte[width_][height_];
      byte g[][] = new byte[width_][height_];
      byte b[][] = new byte[width_][height_];
      int index = 0;

      for (int y = 0; y < height_; ++y){
         for (int x = 0; x < width_; ++x){
            r[x][y] = (byte)((values[index] >> 16) & 0xFF);
            g[x][y] = (byte)((values[index] >> 8) & 0xFF);
            b[x][y] = (byte)((values[index]) & 0xFF);  
            ++index;
         }
      }
      ToIndexedColor(r, g, b);
   }


   //--------------------------------------------------------------
   // CONSTRUCTOR
   //--------------------------------------------------------------
   public WriteGIF(byte r[][], byte g[][], byte b[][]) 
   	throws IOException {
      width_ = (short)(r.length);
      height_ = (short)(r[0].length);

      ToIndexedColor(r, g, b);
   }

   public void write(OutputStream output) throws IOException{
   
      BitUtils.writeString(output, "GIF87a");
      ScreenDescriptor sd = new ScreenDescriptor(width_, height_,
                     numColors_);
      sd.Write(output);
      output.write(colors_, 0, colors_.length);
      ImageDescriptor id = new ImageDescriptor(width_, height_, ',');
      id.write(output);

      byte codesize = BitUtils.BitsNeeded(numColors_);
      if (codesize == 1) {
          ++codesize;
      }
      output.write(codesize);

      LZWCompressor.LZWCompress(output, codesize, pixels_);
      output.write(0);

      id = new ImageDescriptor((byte)0, (byte)0, ';');
      id.write(output);
      output.flush();
   }

   void ToIndexedColor(byte r[][], byte g[][], byte b[][]) 
      throws IOException {
      pixels_ = new byte[width_ * height_];
      colors_ = new byte[256 * 3];
      int colornum = 0;
      for (int x = 0; x < width_; ++x){
         for (int y = 0; y < height_; ++y){
            int search;
            for (search = 0; search < colornum; ++search){
               if (colors_[search * 3] == r[x][y] &&
                  	colors_[search * 3 + 1] == g[x][y] &&
                  	colors_[search * 3 + 2] == b[x][y])
               {
                  break;
               }
            }

            // If there are more than 256 colors invoke
            // the color quantization procedure.
            //quantization();
            if (search > 255){
               throw new IOException("Too many colors.");
            }

            pixels_[y * width_ + x] = (byte)search;
      
            if (search == colornum){
               colors_[search * 3]     = r[x][y];
               colors_[search * 3 + 1] = g[x][y];
               colors_[search * 3 + 2] = b[x][y];
               ++colornum;
            }
         }
      }
      numColors_ = 1 << BitUtils.BitsNeeded(colornum);
      byte copy[] = new byte[numColors_ * 3];
      System.arraycopy(colors_, 0, copy, 0, numColors_ * 3);
      colors_ = copy;
   }
}

class BitFile{
   OutputStream output_;
   byte buffer_[];
   int index_, bitsLeft_;

   public BitFile(OutputStream output){
      output_ = output;
      buffer_ = new byte[256];
      index_ = 0;
      bitsLeft_ = 8;
   }

   public void Flush() throws IOException{
      int numBytes = index_ + (bitsLeft_ == 8 ? 0 : 1);
      if (numBytes > 0){
         output_.write(numBytes);
         output_.write(buffer_, 0, numBytes);
         buffer_[0] = 0;
         index_ = 0;
         bitsLeft_ = 8;
      }
   }

   public void WriteBits(int bits, int numbits) throws IOException{
      // int bitsWritten = 0;
      int numBytes = 255;
      do
      {
         if ((index_ == 254 && bitsLeft_ == 0) || index_ > 254)
         {
            output_.write(numBytes);
            output_.write(buffer_, 0, numBytes);

            buffer_[0] = 0;
            index_ = 0;
            bitsLeft_ = 8;
         }

         if (numbits <= bitsLeft_)
         {
            buffer_[index_] |= (bits & ((1 << numbits) - 1)) <<
               (8 - bitsLeft_);
            // bitsWritten += numbits;
            bitsLeft_ -= numbits;
            numbits = 0;
         }
         else
         {
            buffer_[index_] |= (bits & ((1 << bitsLeft_) - 1)) <<
               (8 - bitsLeft_);
            // bitsWritten += bitsLeft_;
            bits >>= bitsLeft_;
            numbits -= bitsLeft_;
            buffer_[++index_] = 0;
            bitsLeft_ = 8;
         }
      }
      while (numbits != 0);
   }
}

class LZWStringTable{
   private final static int RES_CODES = 2;
   private final static short HASH_FREE = (short)0xFFFF;
   private final static short NEXT_FIRST = (short)0xFFFF;
   private final static int MAXBITS = 12;
   private final static int MAXSTR = (1 << MAXBITS);
   private final static short HASHSIZE = 9973;
   private final static short HASHSTEP = 2039;

   byte strChr_[];
   short strNxt_[];
   short strHsh_[];
   short numStrings_;

   public LZWStringTable(){
      strChr_ = new byte[MAXSTR];
      strNxt_ = new short[MAXSTR];
      strHsh_ = new short[HASHSIZE];    
   }

   public int AddCharString(short index, byte b){
      int hshidx;

      if (numStrings_ >= MAXSTR){
         return 0xFFFF;
      }
   
      hshidx = Hash(index, b);
      while (strHsh_[hshidx] != HASH_FREE){
         hshidx = (hshidx + HASHSTEP) % HASHSIZE;
      }
   
      strHsh_[hshidx] = numStrings_;
      strChr_[numStrings_] = b;
      strNxt_[numStrings_] = (index != HASH_FREE) ? index : NEXT_FIRST;

      return numStrings_++;
   }
    
   public short FindCharString(short index, byte b){
      int hshidx, nxtidx;

      if (index == HASH_FREE){
         return b;
      }

      hshidx = Hash(index, b);
      while ((nxtidx = strHsh_[hshidx]) != HASH_FREE){
         if (strNxt_[nxtidx] == index && strChr_[nxtidx] == b){
            return (short)nxtidx;
         }
         hshidx = (hshidx + HASHSTEP) % HASHSIZE;
      }
      return (short)0xFFFF;
   }

   public void ClearTable(int codesize)
   {
      numStrings_ = 0;
   
      for (int q = 0; q < HASHSIZE; q++)
      {
         strHsh_[q] = HASH_FREE;
      }

      int w = (1 << codesize) + RES_CODES;
      for (int q = 0; q < w; q++)
      {
         AddCharString((short)0xFFFF, (byte)q);
      }
   }
    
   static public int Hash(short index, byte lastbyte){
      return ((int)((short)(lastbyte << 8) ^ index) & 0xFFFF) % HASHSIZE;
   }
}

class LZWCompressor{
   public static void LZWCompress(OutputStream output, int codesize,
      byte toCompress[]) throws IOException{
      byte c;
      short index;
      int clearcode, endofinfo, numbits, limit, errcode;
      short prefix = (short)0xFFFF;

      BitFile bitFile = new BitFile(output);
      LZWStringTable strings = new LZWStringTable();

      clearcode = 1 << codesize;
      endofinfo = clearcode + 1;
    
      numbits = codesize + 1;
      limit = (1 << numbits) - 1;
   
      strings.ClearTable(codesize);
      bitFile.WriteBits(clearcode, numbits);

      for (int loop = 0; loop < toCompress.length; ++loop){
         c = toCompress[loop];
         if ((index = strings.FindCharString(prefix, c)) != -1){
            prefix = index;
         }
         else
         {
            bitFile.WriteBits(prefix, numbits);
            if (strings.AddCharString(prefix, c) > limit)
            {
               if (++numbits > 12)
               {
                  bitFile.WriteBits(clearcode, numbits - 1);
                  strings.ClearTable(codesize);
                  numbits = codesize + 1;
               }
               limit = (1 << numbits) - 1;
            }
      
            prefix = (short)((short)c & 0xFF);
         }
      }
   
      if (prefix != -1)
      {
         bitFile.WriteBits(prefix, numbits);
      }
   
      bitFile.WriteBits(endofinfo, numbits);
      bitFile.Flush();
   }
}

class ScreenDescriptor{

   public short localScreenWidth_, localScreenHeight_;
   private byte byte_;
   public byte backgroundColorIndex_, pixelAspectRatio_;

   public ScreenDescriptor(short width, short height, int numColors){
   
      localScreenWidth_ = width;
      localScreenHeight_ = height;
      SetGlobalColorTableSize((byte)(BitUtils.BitsNeeded(numColors) - 1));
      SetGlobalColorTableFlag((byte)1);
      SetSortFlag((byte)0);
      SetColorResolution((byte)7);
      backgroundColorIndex_ = 0;
      pixelAspectRatio_ = 0;
   }

   public void Write(OutputStream output) throws IOException{
      BitUtils.writeWord(output, localScreenWidth_);
      BitUtils.writeWord(output, localScreenHeight_);
      output.write(byte_);
      output.write(backgroundColorIndex_);
      output.write(pixelAspectRatio_);
   }

   public void SetGlobalColorTableSize(byte num){
      byte_ |= (num & 7);
   }

   public void SetSortFlag(byte num){
      byte_ |= (num & 1) << 3;
   }

   public void SetColorResolution(byte num){
      byte_ |= (num & 7) << 4;
   }
    
   public void SetGlobalColorTableFlag(byte num){
      byte_ |= (num & 1) << 7;
   }
}

class ImageDescriptor{
   public byte separator_;
   public short leftPosition_, topPosition_, width_, height_;
   private byte byte_;

   public ImageDescriptor(short width, short height, char separator){
      separator_ = (byte)separator;
      leftPosition_ = 0;
      topPosition_ = 0;
      width_ = width;
      height_ = height;
      setLocalColorTableSize((byte)0);
      setReserved((byte)0);
      setSortFlag((byte)0);
      setInterlaceFlag((byte)0);
      setLocalColorTableFlag((byte)0);
   }
    
   public void write(OutputStream output) throws IOException{
      output.write(separator_);
      BitUtils.writeWord(output, leftPosition_);
      BitUtils.writeWord(output, topPosition_);
      BitUtils.writeWord(output, width_);
      BitUtils.writeWord(output, height_);
      output.write(byte_);
   }

   public void setLocalColorTableSize(byte num){
      byte_ |= (num & 7);
   }

   public void setReserved(byte num){
      byte_ |= (num & 3) << 3;
   }

   public void setSortFlag(byte num){
      byte_ |= (num & 1) << 5;
   }
    
   public void setInterlaceFlag(byte num){
      byte_ |= (num & 1) << 6;
   }

   public void setLocalColorTableFlag(byte num){
      byte_ |= (num & 1) << 7;
   }
}

class BitUtils{
   public static byte BitsNeeded(int n){
      byte ret = 1;

      if (n-- == 0){
         return 0;
      }
      while ((n >>= 1) != 0){
         ++ret;
      }
      return ret;
   }    

   public static void writeWord(OutputStream output, short w)
      throws IOException
   {
      output.write(w & 0xFF);
      output.write((w >> 8) & 0xFF);
   }
    
   static void writeString(OutputStream output,
      String string) throws IOException{
      for (int loop = 0; loop < string.length(); ++loop){
         output.write((byte)(string.charAt(loop)));
      }
   }
}
