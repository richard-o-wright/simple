/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.wrightnz.awt.dnd;

import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.util.StringTokenizer;

/**
 *
 * @author T466225
 */
public class IVYFileUtil {

    public static Color fileToColour(File file) {
        if (file.exists()) {
            String filename = file.getName();
            StringTokenizer st = new StringTokenizer(filename, ".");
            int r = Integer.parseInt(st.nextToken());
            int g = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int a = Integer.parseInt(st.nextToken());
            return new Color(r, g, b, a);
        } else {
            return new Color(0, 0, 0, 255);
        }
    }

    public static String colourToFileName(Color colour){
        int r = colour.getRed();
        int g = colour.getGreen();
        int b = colour.getBlue();
        int a = colour.getAlpha();
        String colourFileName = String.valueOf(r) + "."
            + String.valueOf(g) + "."
            + String.valueOf(b) + "."
            + String.valueOf(a) + ".colour";
        return colourFileName;
    }


     public static Font fileToFont(File file) {
        if (file.exists()) {
            String filename = file.getName();
            StringTokenizer st = new StringTokenizer(filename, ".");
            String name = st.nextToken();
            int style = Integer.parseInt(st.nextToken());
            int size = Integer.parseInt(st.nextToken());
            return new Font(name, style, size);
        } else {
            return new Font("Dialog", 0, 12);
        }
    }

    public static String fontToFileName(Font font) {
        String name = font.getName();
        int style = font.getStyle();
        int size = font.getSize();
        String fontFileName = name + "."
            + String.valueOf(style) + "."
            + String.valueOf(size) + ".font";
        return fontFileName;
    }

}
