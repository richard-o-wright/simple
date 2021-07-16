/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.wrightnz.io;

import java.awt.Component;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Arrays;
import javax.swing.JOptionPane;

/**
 *
 * @author Richard Wright
 */
public class FileServices {

    public static final int KILO_BYTE = 1024;
    public static final int MEGA_BYTE = 1024 * 1024;
    public static final int GIGA_BYTE = 1024 * 1024 * 1024;

    /**
     * This method handles file deletion for GUI programs including displaying a
     * confirmation dialogue box before deleting.
     * @param component
     * @param file to delete
     * @return true of file is deleted otherwise false.
     */
     public static boolean delete(Component component, File file) {
        int option = JOptionPane.showConfirmDialog(component,
            "Perminently Delete " + file.getName() + "?",
            "Are You Sure?", JOptionPane.YES_NO_OPTION);
        if (option == JOptionPane.YES_OPTION) {
            return file.delete();
        }
        else{
            return false;
        }
    }

    public static void delete(Component component, File[] files) throws IOException {
        int option = JOptionPane.showConfirmDialog(component,
            "Perminently Delete " + files.length + " files?",
            "Are You Sure?", JOptionPane.YES_NO_OPTION);
        if (option == JOptionPane.YES_OPTION) {
            delete(files);
        }
    }

    public static void delete(File[] files)throws IOException {
        Arrays.sort(files);
        for (int i = 0; i < files.length; i++) {
            File file = files[i];
            boolean deleteOK = true;
            if (file.isFile()) {
                deleteOK = file.delete();
            } else if (file.isDirectory()) {
                delete(file.listFiles());
                deleteOK = file.delete();
            }
            if (!deleteOK) {
                throw new IOException("Could not delete file: " + file.getName());
            }
        }
    }

    public static boolean move(Component component, File from, File to, boolean confirm) {
        if (confirm) {
            int option = JOptionPane.showConfirmDialog(component,
                "Move\n\t" + from.getName() + "\nto\n\t" + to.getParent(),
                "Please Confirm", JOptionPane.YES_NO_OPTION);
            if (option == JOptionPane.YES_OPTION) {
                if (!from.renameTo(to)) {
                    JOptionPane.showMessageDialog(component,
                        "Move from \n\t" + from.getName() + "\nto\n\t" + to.getParent() + " has failed.",
                        "Move Failed", JOptionPane.ERROR_MESSAGE);
                    return false;
                } else {
                    return true;
                }
            } else {
                return false;
            }
        } else {
            return from.renameTo(to);
        }
    }

    public static String getHumanReadableFileSize(File file) {
        StringBuilder sb = new StringBuilder();
        if (!file.isDirectory()) {
            long size = file.length();
            String units = "bytes";
            if (size > KILO_BYTE && size < MEGA_BYTE) {
                size = size / KILO_BYTE;
                units = "K" + units;
            } else if (size > MEGA_BYTE && size < GIGA_BYTE ) {
                size = size / MEGA_BYTE;
                units = "M" + units;
            } else if (size > GIGA_BYTE) {
                size = size / GIGA_BYTE;
                units = "G" + units;
            }
            DecimalFormat format = new DecimalFormat("####.#");
            sb.append(format.format(size));
            sb.append(" ");
            sb.append(units);
        }
        return sb.toString();
    }


}
