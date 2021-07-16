/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.wrightnz.util;

import java.util.List;
import java.util.Map;

/**
 * A holding class for general Utility stuff I haven't found a better
 * place for yet.
 * @author T466225
 */
public class Util {

    /**
     * Used for HTTP Request parameter Map handling returns the first value.
     * in the String[] which is the value stored in the specified map at the
     * specified key.
     * 
     * @param map
     * @param key
     * @return
     */
    public static String getFirstValue(Map<String, String[]>map, String key){
        String[] values = map.get(key);
        if(values != null && values.length > 0){
            return values[0];
        }
        else{
            return null;
        }
    }


    public static void printList(List<?> list){
        System.out.print("List [");
        for(Object obj : list){
            System.out.print(obj.toString());
            System.out.print(",");
        }
        System.out.print("]");
        System.out.println();
    }
    
}
