/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.wrightnz.util.web;

import java.lang.reflect.Method;

/**
 *
 * @author T466225
 */
public abstract class TableRowBase implements TableRowInterface{
    
    public abstract String[] getPublishedMethodNames();

    public boolean isPublished(Method method) {
        String methodName = method.getName();
        String[] publishedMethods = getPublishedMethodNames();
        for(int i = 0; i < publishedMethods.length; i++){
            if(methodName.equals(publishedMethods[i])){
                return true;
            }
        }
        return false;
    }
    
}
