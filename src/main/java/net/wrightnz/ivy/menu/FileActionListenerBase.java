/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.wrightnz.ivy.menu;

import java.awt.event.ActionListener;
import java.io.File;
import java.util.prefs.Preferences;

/**
 *
 * @author T466225
 */
public abstract class FileActionListenerBase implements ActionListener {

    public static Preferences preferences = Preferences.userNodeForPackage(FileActionListenerBase.class);
    
    private App app;
    private File currentPath;
    private String lastDirectortKey = ".lastdirectory";

    public FileActionListenerBase(App app){
        this.app = app;
        this.lastDirectortKey = app.getClass().getName() + lastDirectortKey;
    }

    protected File getLastDirectory(){
        String lastDirectory = preferences.get(lastDirectortKey, System.getProperty("user.home"));
        return new File(lastDirectory);
    }

    protected void setLastDirectory(File selectedFile){
        currentPath = selectedFile.getParentFile();
        preferences.put(lastDirectortKey, currentPath.getAbsolutePath());
    }

    /**
     * @return the app
     */
    public App getApp() {
        return app;
    }

    /**
     * @param app the app to set
     */
    public void setApp(App app) {
        this.app = app;
    }
    
}
