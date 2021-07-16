/*
 * PPMFileReader.java
 *
 * Created on 01 June 2003, 23:33
 */
package net.wrightnz.awt.image.reader;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.MemoryImageSource;
import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.FileImageInputStream;


/**
 * Start PPM File open
 * @author  Richard Wright
 */
public class PPMReader {

    private static Toolkit tk = Toolkit.getDefaultToolkit();
    private byte[] r;
    private byte[] g;
    private byte[] b;
    private int width, height;

    /** Creates a new instance of PPMFileReader */
    public PPMReader() {
    }

    public Image createImage(File file) throws IOException {
        return createImage(new FileImageInputStream(file));
    }

    public Image createImage(ImageInputStream stream) throws IOException {
        readShorts(stream);
        int[] pix = new int[width * height];
        for (int x = 0; x < pix.length; x++) {
            int red = ((int) r[x]) & 0xFF;
            int green = ((int) g[x]) & 0xFF;
            int blue = ((int) b[x]) & 0xFF;
            pix[x] = 0xff000000 | (red << 16) | (green << 8) | blue;
        }
        return tk.createImage(new MemoryImageSource(width, height, pix, 0, width));
    }

    private void readShorts(ImageInputStream in) throws IOException {
        try {
            getByteImage(in);
        } finally  {
            in.close();
        }
    }

    private void getByteImage(ImageInputStream in) throws IOException {
        readHeader(in);
        r = new byte[width * height];
        g = new byte[width * height];
        b = new byte[width * height];
        byte buf[] = new byte[width * height * 3];
        int offset = 0;
        int count = buf.length;
        int n = in.read(buf, offset, count);

        int j = 0;
        for (int i = 0; i < r.length; i++) {
            r[i] = buf[j++];
            g[i] = buf[j++];
            b[i] = buf[j++];
        }
    }

    private void readHeader(ImageInputStream in) throws IOException {
        char c1, c2;
        c1 = (char) readByte(in);
        c2 = (char) readByte(in);

        if (c1 != 'P') {
            throw new IOException("not a PPM file");
        }
        if (c2 != '6') {
            throw new IOException("not a PPM file");
        }

        width = readInt(in);
        height = readInt(in);
        // System.out.println("ReadPPM: " + width + " x " + height);
        // Read maximum value of each color, and ignore it.
        // In PPM_RAW we know r,g,b use full range (0-255).
        readInt(in);
    }

    private static int readInt(ImageInputStream in) throws IOException {
        char c;
        int i;
        c = readNonwhiteChar(in);
        if ((c < '0') || (c > '9')) {
            throw new IOException("Invalid integer in PPM file.");
        }

        i = 0;
        do {
            i = i * 10 + c - '0';
            c = readChar(in);
        } while ((c >= '0') && (c <= '9'));
        return (i);
    }


    private static int readByte(ImageInputStream in) throws IOException {
        int b = in.read();
        // if end of file
        if (b == -1) {
            throw new EOFException();
        }
        return b;
    }

    private static char readNonwhiteChar(ImageInputStream in) throws IOException {
        char c;
        do {
            c = readChar(in);
        } while ((c == ' ') || (c == '\t') || (c == '\n') || (c == '\r'));
        return c;
    }

    /**
     * Removes comments from the image source file
     */
    private static char readChar(ImageInputStream in) throws IOException {
        char c;
        c = (char) readByte(in);
        if (c == '#') {
            do {
                c = (char) readByte(in);
            } while ((c != '\n') && (c != '\r'));
        }
        return (c);
    }
}
