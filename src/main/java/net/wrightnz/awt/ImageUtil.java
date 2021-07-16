/*
 * ImageUtil.java
 *
 * Created on September 6, 2004, 6:33 PM
 */

package net.wrightnz.awt;

import java.awt.Color;
import java.awt.Component;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Rectangle;
import java.awt.image.PixelGrabber;

/**
 * Utility class for Graphics related static methods.
 * 
 * @author  richardw
 */
public class ImageUtil {
    
    public static void waitForLoadedImage(Image image, Component component){
        int id = 0;
        MediaTracker media = new MediaTracker(component);
        media.addImage(image, id);
        try {
            media.waitForID(id);
        }
        catch(InterruptedException e){
            System.err.println("Interrupted while loading image");
        }
    }

    public static int[] getPixels(Image image, int width, int height) {
        int[] pixels = new int[width * height];
        if (image != null) {
            PixelGrabber grabber = new PixelGrabber(image, 0, 0, width, height, pixels, 0, width);
            try {
                grabber.grabPixels();
            } catch (InterruptedException ex) {
                System.err.println("Interrupted while Grabing Pixels");
            }
        }
        return pixels;
    }

    public static int[][] getPixels2D(Image image, int width, int height) {
        int[] pix1D = getPixels(image, width, height);
        int[][] pix2D = new int[width][height];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                pix2D[x][y] = pix1D[y * width + x];
            }
        }
        return pix2D;
    }


    public static boolean isOKToFill(int x, int y, int w, int h, int[][] pix, Color c, int s, int[][] mask, Rectangle selection) {
        if (selection.contains(x, y) && isOKToFill(x, y, w, h, pix, c, s, mask)) {
            return true;
        } else {
            return false;
        }
    }


    /**
     *
     * @param x
     * @param y
     * @param pix
     * @param c
     * @param s
     * @param mask
     * @return true if x and y identifies a pixel in the raster pix which is similar
     *         to colour as color c (where simularity threshold is set with s) and
     *         the point x,y does not have a value of 255 in the mask array mask.
     *         A value of 255 in the mask area indecaing that this point has already
     *         been identified as on what is OK to fill.
     */
    public static boolean isOKToFill(int x, int y, int w, int h, int[][] pix, Color c, int s, int[][] mask) {
        if (isPointInBounds(x, y, w, h)
            && TB.sameColour(pix[x][y], c.getRGB(), s)
            && mask[x][y] != 255) {
            return true;
        } else {
            return false;
        }
    }


    /**
     * @param x
     * @param y
     * @param w
     * @param h
     * @return true if x,y is within the area specified by width w and height h
     */
    public static boolean isPointInBounds(int x, int y, int w, int h) {
        return new Rectangle(w, h).contains(x, y);
    }

}
