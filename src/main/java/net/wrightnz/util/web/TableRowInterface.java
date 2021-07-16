/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.wrightnz.util.web;

import java.lang.reflect.Method;

/**
 *
 * @author t466225
 * 
 * This interface can be extended by Value Objects wishing to have more
 * control over how they are displayed by the nz.org.richardw.util.web.Table.
 */
public interface TableRowInterface {
    
    /**
     * Returns the list of methods published by this TableRowInterface. A
     * published method is one that can be called to display it's value 
     * in a nz.org.richardw.util.web.Table. 
     * 
     * Publish methods are required to return their value as instance of
     * nz.org.richardw.util.web.Link.
     * 
     * @return String[] of method names published by this TableRowInterface 
     *                  implementation.
     */
    String[] getPublishedMethodNames();
    
    /**
     * 
     * @param method
     * @return try if the method is published.
     */
    boolean isPublished(Method method);
    
}
