package net.wrightnz.awt.image.write;

import java.io.*;
import java.awt.image.*;

/**
 * WritePPM is a class that takes an Image and saves it to
 * a PPM format file.
 * Victor Silva (victor@bridgeport.edu).
 * Modified by D.L. (lyon@DocJava.com)
 * Modified still more by R. Wright (rwri002@voyager.co.nz)
 */
public class PPMWriter{

	public PPMWriter(){
	
	}
   
   public void saveIt(int[] pix, int w, int h, String fn) throws IOException{	
		ColorModel model = ColorModel.getRGBdefault();
   		
		byte[] r = new byte[pix.length];
		byte[] g = new byte[pix.length];
		byte[] b = new byte[pix.length];
		for (int i = 0; i < pix.length; i++){
			r[i] = (byte)model.getRed(pix[i]);
			g[i] = (byte)model.getGreen(pix[i]);
			b[i] = (byte)model.getBlue(pix[i]);
		}
			     
         BufferedOutputStream os = new BufferedOutputStream( new FileOutputStream(fn));
         writeHeader(os, w, h);
         writeImage(os, r, g, b);
         // System.out.println("PPM Image writen");
         os.flush();
         os.close();
   }

	private void writeHeader(OutputStream out, int width, int height) throws IOException{
	   writeString( out, "P6\n" );
	   writeString( out, width + " " + height + "\n" );
	   // Write the maximum value of each color.
	   // i.e. r,g,b all vary between 0 and 255.
	   writeString( out, "255\n" );
	   // System.out.println("Header OK");
	}

	private void writeString(OutputStream out, String str) throws IOException{
		int len = str.length();
		byte[] buf = new byte[len];
		buf = str.getBytes();
		out.write(buf);
	}

	private void writeImage(OutputStream out, byte r[], byte g[], byte b[]) throws IOException{
		int j=0;
		byte[] ppmPixels = new byte[r.length * 3];
		for (int i = 0; i < r.length; i++ ) {
			ppmPixels[j++] = r[i];
			ppmPixels[j++] = g[i];
			ppmPixels[j++] = b[i];
		}
		// Write ppm file all at once.
	   out.write(ppmPixels);
	}
}
