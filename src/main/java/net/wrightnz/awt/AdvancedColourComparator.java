/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.wrightnz.awt;

import java.awt.Color;
import java.util.Comparator;

/**
 *
 * @author richard
 */
public class AdvancedColourComparator implements Comparator<Color> {

    @Override
    public int compare(Color c1, Color c2) {
        // Calculate the absolute brightness of each colour
        int bright1 = c1.getRed() + c1.getGreen() + c1.getBlue();
        int bright2 = c2.getRed() + c2.getGreen() + c2.getBlue();
        // Find largest channel
        int dif1 = Math.max(Math.max(c1.getRed(), c1.getGreen()), Math.max(c1.getGreen(), c1.getBlue()));
        int dif2 = Math.max(Math.max(c2.getRed(), c2.getGreen()), Math.max(c2.getGreen(), c2.getBlue()));
        // Calculate a number which will be the same for any colour regardless of brightness
        int crom1 = (dif1 - c1.getRed()) * 4 + (dif1 - c1.getGreen()) * 2 + (dif1 - c1.getBlue());
        int crom2 = (dif2 - c2.getRed()) * 4 + (dif2 - c2.getGreen()) * 2 + (dif2 - c2.getBlue());
        // System.out.println("Croms      :" + crom1 + " : " + crom2);
        if (crom1 == crom2) {
            return compareBrightness(bright1, bright2);
        } else if (crom1 > crom2) {
            return 1;
        } else {
            return -1;
        }
    }

    private int compareBrightness(int bright1, int bright2) {
        if (bright1 > bright2) {
            return 1;
        } else if (bright1 < bright2) {
            return -1;
        } else {
            return 0;
        }
    }
}
