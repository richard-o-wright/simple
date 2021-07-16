package net.wrightnz.awt;

import java.awt.*;
import java.awt.image.*;
import java.util.*;
import java.util.List;

/** 
 * Static Tools for IVY Paint image program, or
 * any other 2D Graphics app for that matter.
 *
 * @author Richard Wright.
 */
public class TB {

    private static ColorModel model = ColorModel.getRGBdefault();
    private static Toolkit tk = Toolkit.getDefaultToolkit();
    private static Comparator<Color> comparator = new AdvancedColourComparator();

    /**
     * Rescale val UP by scale i.e. result = val * scale)
     */
    public static int scaleUp(int val, double scale) {
        return (int) (val * scale);
    }

    /**
     * Rescale val DOWN by scale i.e. result = val / scale)
     */
    public static int scaleDown(int val, double scale) {
        return (int) (val / scale);
    }

    /**
     * Rescale DOWN rectangle r by scale
     */
    public static Rectangle rescaleRect(Rectangle r, double scale) {
        int rx = (int) (r.x / scale);
        int ry = (int) (r.y / scale);
        int rw = (int) (r.width / scale);
        int rh = (int) (r.height / scale);
        return new Rectangle(rx, ry, rw, rh);
    }

    /**
     * Keeps int in range 0 to 255.
     */
    public static int colourLimiter(int val) {
        if (val < 0) {
            val = 0;
        } else if (val > 255) {
            val = 255;
        }
        return val;
    }

    /**
     * Check if to colours are within dif of each other.
     */
    public static boolean sameColour(int col1, int col2, int dif) {
        int rDif = Math.abs(model.getRed(col1) - model.getRed(col2));
        int gDif = Math.abs(model.getGreen(col1) - model.getGreen(col2));
        int bDif = Math.abs(model.getBlue(col1) - model.getBlue(col2));
        if (rDif <= dif && gDif <= dif && bDif <= dif) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * See if difference between two colour values is big
     * enough (that is greater then 5)
     * and if so exagerate it, otherwise blacken.
     * Used for edge detector.
     */
    public static int[] edgeFind(int col1, int col2) {
        int[] colours = new int[2];
        int dif = Math.abs(col1 - col2);

        if (col1 > col2 && dif > 5) {
            colours[0] = colourLimiter(col1 + 150);
            colours[1] = colourLimiter(col2 - 150);
        } else if (col1 < col2 && dif > 5) {
            colours[0] = colourLimiter(col1 - 150);
            colours[1] = colourLimiter(col2 + 150);
        } else {
            colours[0] = 0;
            colours[1] = 0;
        }
        return colours;
    }

    /**
     * See if difference between to colour values is big enough
     * and if so exagerate it.
     */
    public static int[] edgeEnhance(int col1, int col2) {
        int[] colours = new int[2];
        int dif = Math.abs(col1 - col2);

        if (col1 > col2 && dif > 10) {
            colours[0] = colourLimiter(col1 + 50);
            colours[1] = col2;
        } else if (col1 < col2 && dif > 10) {
            colours[0] = col1;
            colours[1] = colourLimiter(col2 + 50);
        } else {
            colours[0] = col1;
            colours[1] = col2;
        }
        return colours;
    }

    /**
     * Convert 2D array to 1D array
     */
    public static int[] two2One(int[][] array2D) {
        int width = array2D.length;
        int height = array2D[0].length;
        int[] array1D = new int[width * height];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                array1D[y * width + x] = array2D[x][y];
            }
        }
        return array1D;
    }

    /**
     * Return a Stack which contains all the unique colours
     * in the image.
     */
    public static Stack<Color> makePalette(int[] pixels) {
        Stack<Color> colours = new Stack<Color>();
        List<Color> allPixels = new ArrayList<Color>();
        for (int i = 0; i < pixels.length; i++) {
            allPixels.add(new Color(pixels[i]));
        }
        Collections.sort(allPixels, comparator);
        // Remove deplicated colors
        for (Color pixel : allPixels) {
            if (colours.empty()) {
                colours.push(pixel);
            } else if (!pixel.equals(colours.peek())) {
                colours.push(pixel);
            }
        }
        return colours;
    }

    public static int[] colourSort(List<Color> list) {
        int[] elements = new int[list.size()];
        Collections.sort(list, comparator);
        int c = 0;
        for(Color color : list){
            elements[c] = color.getRGB();
            c++;
        }
        return elements;
    }

    /**
     */
    public static int redGreenSwap(int pixel) {
        int red = model.getRed(pixel);
        int green = model.getGreen(pixel);
        int blue = model.getBlue(pixel);
        int alpha = model.getAlpha(pixel);
        return (alpha << 24) | (green << 16) | (red << 8) | blue;
    }

    /**
     */
    public static int greenBlueSwap(int pixel) {
        int red = model.getRed(pixel);
        int green = model.getGreen(pixel);
        int blue = model.getBlue(pixel);
        int alpha = model.getAlpha(pixel);
        return (alpha << 24) | (red << 16) | (blue << 8) | green;
    }

    /**
     */
    public static int redBlueSwap(int pixel) {
        int red = model.getRed(pixel);
        int green = model.getGreen(pixel);
        int blue = model.getBlue(pixel);
        int alpha = model.getAlpha(pixel);
        return (alpha << 24) | (blue << 16) | (green << 8) | red;
    }

    public static int addColours(int col1, int col2) {
        int r = colourLimiter(model.getRed(col1) + model.getRed(col2));
        int g = colourLimiter(model.getGreen(col1) + model.getGreen(col2));
        int b = colourLimiter(model.getBlue(col1) + model.getBlue(col2));
        return (255 << 24) | (r << 16) | (g << 8) | b;
    }

    public static int average(int col1, int col2) {
        int r = (model.getRed(col1) + model.getRed(col2)) / 2;
        int g = (model.getGreen(col1) + model.getGreen(col2)) / 2;
        int b = (model.getBlue(col1) + model.getBlue(col2)) / 2;
        return (255 << 24) | (r << 16) | (g << 8) | b;
    }

    public static int blend(int factor, int col1, int col2) {
        int r1 = model.getRed(col1);
        int r2 = model.getRed(col2);
        int red = (r1 + (r2 * factor)) / (factor + 1);
        int g1 = model.getGreen(col1);
        int g2 = model.getGreen(col2);
        int green = (g1 + (g2 * factor)) / (factor + 1);
        int b1 = model.getBlue(col1);
        int b2 = model.getBlue(col2);
        int blue = (b1 + (b2 * factor)) / (factor + 1);
        return (255 << 24) | (red << 16) | (green << 8) | blue;
    }

    /**
     * Return a Vector which contains a subset of colours in the
     * image.
     */
    public static Stack<Color> makePalette(int[] pix, int numberOfColours) {
        return makePalette(pix);
    }

    public static Image sortPix(int[] pix, int w, int h) {
        colourSort(pix);
        return tk.createImage(new MemoryImageSource(w, h, pix, 0, w));
    }

    public static void colourSort(int[] pix) {
        List<Color> pixels = new ArrayList<Color>(pix.length);
        for (int i = 0; i < pix.length; i++) {
            pixels.add(new Color(pix[i]));
        }
        Collections.sort(pixels, comparator);
        Iterator it = pixels.iterator();
        int c = 0;
        while (it.hasNext()) {
            pix[c] = ((Color) it.next()).getRGB();
            c++;
        }
    }

    /**
     * Sort range from m to n in 1D array pix using QUICK sort
     * 
     */
    public static int[] qSort(int[] pix, int m, int n) {
        int i, j, pivot;
        if (n <= m + 1) {
            return pix;
        }
        if (n - m == 2) {
            if (get(pix, n - 1) < get(pix, m)) {
                exchange(pix, m, n - 1);
            }
            return pix;
        }
        i = m + 1;
        j = n - 1;
        if (get(pix, i) > get(pix, m)) {
            exchange(pix, i, m);
        }
        if (get(pix, j) < get(pix, m)) {
            exchange(pix, m, j);
        }
        if (get(pix, i) > get(pix, m)) {
            exchange(pix, i, m);
        }
        pivot = get(pix, m);
        while (true) {
            j--;
            while (pivot < get(pix, j)) {
                j--;
            }
            i++;
            while (pivot > get(pix, i)) {
                i++;
            }
            if (j <= i) {
                break;
            }
            exchange(pix, i, j);
        }
        exchange(pix, m, j);
        if (j - m < n - j) {
            qSort(pix, m, j);
            qSort(pix, j + 1, n);
        } else {
            qSort(pix, j + 1, n);
            qSort(pix, m, j);
        }
        return pix;
    }

    /**
     * Supporting methods for quick sort.
     */
    private static void exchange(int[] a, int m, int n) {
        int tmp = a[m];
        a[m] = a[n];
        a[n] = tmp;
    }

    private static int get(int[] a, int i) {
        return a[i];
    }
    
}// End of class ToolBox
