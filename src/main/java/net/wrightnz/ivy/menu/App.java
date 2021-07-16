/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.wrightnz.ivy.menu;

import java.awt.Component;
import java.io.File;

/**
 *
 * @author T466225
 */
public interface App {

    String getAppName();

    void load(File file);

    void save(File file);

    Component getPrimaryView();
    
}
