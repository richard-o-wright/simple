package net.wrightnz.ivy.menu;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

/**
 * File Menus for OBJ file viewer.
 *
 * @author Richard Wright, richard@wrightnz.net
 * 
 */
public class FileMenu extends JMenu {

    private JMenuItem open;
    private JMenuItem saveAs;

    public FileMenu(App app) {
        super("File");
        open = new JMenuItem("Open ..");
        open.addActionListener(new FileOpenActionListener(app));
        add(open);
        saveAs = new JMenuItem("Save As ..");
        saveAs.addActionListener(new FileSaveActionListener(app));
        add(saveAs);
    }
  
} // End FileMenu Class
