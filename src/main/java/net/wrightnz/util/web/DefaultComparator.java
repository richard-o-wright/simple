/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.wrightnz.util.web;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Comparator;

/**
 * Class: DefaultComparator can be used to compare any to objects based on what
 * is returned by a specified method which is present in both objects and whose
 * name is specified as a String on te Constructor.
 * 
 * The comparison rules are as follows:
 * If what is returned by calling the specified method (the one past in on
 * the constructor) itself implements comparable compare the objects based on
 * their own comparison rules. Otherwise call toString() on both objects and
 * compare the two String.
 * 
 * @author t466225
 */
public class DefaultComparator implements Comparator<Object> {

    private String getter;
    
    /**
     * 
     * @param getter the method to call when comparing the two objects.
     *        The method must return something and have no parameters.
     */
    public DefaultComparator(String getter) {
        this.getter = getter;
        System.out.println("Order using DefaultComparator(" + getter + ")");
    }

    @Override
    public int compare(Object o1, Object o2){
        if(o1 == null && o2 == null){
            return 0;
        }
        else if(o1 == null){
            return -1;
        }
        else if(o2 == null){
            return 1;
            
        }
        else{
            Method method = getGetter(o1);
            if(method != null){ 
                try {
                    Object value1 = method.invoke(o1, (Object[])null);
                    Object value2 = method.invoke(o2, (Object[])null);
                    if(value1 instanceof Comparable && value2 instanceof Comparable){
                        Comparable c1 = (Comparable)value1;
                        Comparable c2 = (Comparable)value2; 
                        return c1.compareTo(c2); 
                    }
                    else{
                     /* OK the returned types do not implement Comparable so 
                      well do a simple String compare on whatever we get back from
                      their toString() methods*/
                        String v1 = value1.toString();
                        String v2 = value2.toString();
                        return v1.compareTo(v2);
                    }
                }
                catch (IllegalAccessException ex) {
                    System.err.println("DefaultComparator could not compare getter return values: " + ex);
                    return 0;
                }
                catch (IllegalArgumentException ex) {
                    System.err.println("DefaultComparator could not compare getter return values: " + ex);
                    return 0;
                } 
                catch (InvocationTargetException ex) {
                    System.err.println("DefaultComparator could not compare getter return values: " + ex);
                    return 0;
                }
            }
            else{
                return 0;
            }
        }
    }
        
    protected Method getGetter(Object obj){
        Class c = obj.getClass();
        Method[] methods = c.getMethods();
        for(int i = 0; i < methods.length; i++){
            Method method = methods[i];
            Class[] params = method.getParameterTypes();
            String methodName = method.getName();
            if(methodName.equals(getter) && params.length == 0){
                return method;
            }
        }
        return null;
    }

}
