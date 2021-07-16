/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.wrightnz.awt.dnd;

import java.awt.Color;
import java.awt.Font;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import javax.swing.JComponent;
import javax.swing.TransferHandler;
import javax.swing.plaf.basic.BasicTextUI;

/**
 *
 * @author Richard Wright
 */
public class StringTransferHandler extends TransferHandler {

    public static final String FILES_PREFIX = "files::";
    public static final String FILES_LIST_DELIMITER = ";";
    public static final String COLOUR_PREFIX = "colour::";
    public static final String COLOUR_LIST_DELIMITER = ";";
    public static final String FONT_PREFIX = "font::";
    public static final String FONT_LIST_DELIMITER = ";";
    public static final String XML_PREFIX = "<fontandcolour>";
    private int action;
    private TransferHandler transferHandler;

    public StringTransferHandler(int action) {
        this.action = action;
    }

    public StringTransferHandler(int action, TransferHandler transferHandler) {
        this.action = action;
        this.transferHandler = transferHandler;
    }

    @Override
    public boolean canImport(TransferHandler.TransferSupport support) {
        // KISS we only support drops (not clipboard paste)
        if (!support.isDrop()) {
            return false;
        }
        // We only import Strings in this base class though this can be overriden
        // However in the interests of interoperablity and simplicity if a String
        // will do use it.
        if (!support.isDataFlavorSupported(DataFlavor.stringFlavor)) {
            return false;
        }
        boolean actionSupported = (action & support.getSourceDropActions()) == action;
        if (actionSupported) {
            support.setDropAction(action);
            return true;
        }
        return false;
    }

    @Override
    public boolean importData(TransferHandler.TransferSupport support) {
        // If we can't handle the import, say so
        if (!canImport(support)) {
            return false;
        }
        // Fetch the data and bail if this fails
        String data;
        try {
            data = (String) support.getTransferable().getTransferData(DataFlavor.stringFlavor);
        } catch (UnsupportedFlavorException e) {
            return false;
        } catch (IOException e) {
            return false;
        }

        if (support.getComponent() instanceof IVYTransferInterface) {
            IVYTransferInterface component = (IVYTransferInterface) support.getComponent();
            if (data.startsWith(COLOUR_PREFIX)) {
                List<Color> colours = new ArrayList<Color>();
                colours.add(dataToColour(data));
                component.handleImportData(colours);
            } else {
                if (data.startsWith(FILES_PREFIX)) {
                    List<File> files = dataToFiles(data);
                    component.handleImportData(files);
                } else {
                    if (data.startsWith(FONT_PREFIX)) {
                        List<Font> fonts = new ArrayList<Font>();
                        fonts.add(dataToFont(data));
                        component.handleImportData(fonts);
                    } else {
                        if (data.startsWith(XML_PREFIX)) {
                            List<FontAndColour> fontAndColour = new ArrayList<FontAndColour>();
                            try {
                                fontAndColour.add(new FontAndColour(data));
                                component.handleImportData(fontAndColour);
                            } catch (Exception e) {
                                e.printStackTrace();
                                return false;
                            }
                        } else {
                            if (transferHandler != null) {
                                transferHandler.importData(support);
                            }
                        }
                    }
                }
            }
        }
        /* // Fetch the drop location
        JList.DropLocation dl = (JList.DropLocation) support.getDropLocation();
        int i = dl.getIndex();        
        DefaultListModel model = (DefaultListModel) list.getModel();
        model.insertElementAt(data, i);
        Rectangle rect = list.getCellBounds(i, i);
        list.scrollRectToVisible(rect);
        list.setSelectedIndex(i);
        list.requestFocusInWindow();
         */
        return true;
    }

    @Override
    public int getSourceActions(JComponent comp) {
        return COPY_OR_MOVE;
    }

    @Override
    public Transferable createTransferable(JComponent comp) {
        if (comp instanceof IVYTransferInterface) {
            IVYTransferInterface from = (IVYTransferInterface) comp;
            return from.createTransferable();
        } else {
           // if (transferHandler != null) {
           //     return BasicTextUI.createUI(comp).;
           // } else {
                return new StringSelection("Unsupport Component");
          //  }
        }
    }

    @Override
    public void exportDone(JComponent source, Transferable trans, int action) {
        if (source instanceof IVYTransferInterface) {
            IVYTransferInterface component = (IVYTransferInterface) source;
            if (action == MOVE) {
                component.handleExportDone();
            }
        } else {
            if (transferHandler != null) {
                // transferHandler.exportDone(source, trans, action);
            }
        }
    }

    public static String filesToString(Object[] files) {
        StringBuilder sb = new StringBuilder();
        sb.append(FILES_PREFIX);
        for (int i = 0; i < files.length; i++) {
            sb.append(((File) files[i]).getAbsolutePath());
            if (i < files.length - 1) {
                sb.append(FILES_LIST_DELIMITER);
            }
        }
        return sb.toString();
    }

    public static String colourToString(Color colour) {
        StringBuilder sb = new StringBuilder();
        sb.append(COLOUR_PREFIX);
        sb.append(colour.getRed());
        sb.append(COLOUR_LIST_DELIMITER);
        sb.append(colour.getGreen());
        sb.append(COLOUR_LIST_DELIMITER);
        sb.append(colour.getBlue());
        sb.append(COLOUR_LIST_DELIMITER);
        sb.append(colour.getAlpha());
        return sb.toString();
    }

    public static String fontToString(Font font) {
        StringBuilder sb = new StringBuilder();
        sb.append(FONT_PREFIX);
        sb.append(font.getName());
        sb.append(FONT_LIST_DELIMITER);
        sb.append(font.getStyle());
        sb.append(FONT_LIST_DELIMITER);
        sb.append(font.getSize());
        return sb.toString();
    }

    private List<File> dataToFiles(String data) {
        List<File> files = new ArrayList<File>();
        String fileString = data.substring(FILES_PREFIX.length(), data.length());
        StringTokenizer fileList = new StringTokenizer(fileString, FILES_LIST_DELIMITER);
        while (fileList.hasMoreTokens()) {
            files.add(new File(fileList.nextToken()));
        }
        return files;
    }

    private Color dataToColour(String data) {
        String colourString = data.substring(COLOUR_PREFIX.length(), data.length());
        StringTokenizer colourList = new StringTokenizer(colourString, COLOUR_LIST_DELIMITER);
        int r = Integer.parseInt(colourList.nextToken());
        int g = Integer.parseInt(colourList.nextToken());
        int b = Integer.parseInt(colourList.nextToken());
        int a = Integer.parseInt(colourList.nextToken());
        return new Color(r, g, b, a);
    }

    private Font dataToFont(String data) {
        String fontString = data.substring(FONT_PREFIX.length(), data.length());
        StringTokenizer fontData = new StringTokenizer(fontString, FONT_LIST_DELIMITER);
        String name = fontData.nextToken();
        int style = Integer.parseInt(fontData.nextToken());
        int size = Integer.parseInt(fontData.nextToken());
        return new Font(name, style, size);
    }

    public static void main(String[] args) {
        StringTransferHandler sth = new StringTransferHandler(0);
        String data = StringTransferHandler.FILES_PREFIX + "/usr/local/public_ file./How To;D:\\Program Files\\My Program.exe";
        net.wrightnz.util.Util.printList(sth.dataToFiles(data));
        File[] files = {
            new File("/usr/local/public_ file./How To"),
            new File("D:\\Program Files\\My Program.exe"),
            new File("D:\\Program Files\\My Program.txt")
        };
        System.out.println(filesToString(files));
        System.out.println(colourToString(new Color(210, 0, 12, 255)));
        System.out.println(fontToString(new Font("Arial", 0, 13)));
    }
}

