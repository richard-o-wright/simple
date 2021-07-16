/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.wrightnz.ivy.menu;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.io.File;
import javax.swing.JFileChooser;

/**
 *
 * @author T466225
 */
public class FileSaveActionListener extends FileActionListenerBase{

    public FileSaveActionListener(App app){
        super(app);
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        JFileChooser chooser = new JFileChooser(getLastDirectory());
        int returnVal = chooser.showSaveDialog((Component)getApp());
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File selectedFile = chooser.getSelectedFile();
            getApp().save(selectedFile);
            setLastDirectory(selectedFile);
        }
    }

}
