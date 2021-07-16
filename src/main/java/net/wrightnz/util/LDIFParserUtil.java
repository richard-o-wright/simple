/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.wrightnz.util;

import java.io.IOException;
import net.wrightnz.util.encodedecode.Base64Utils;

/**
 *
 * @author T466225
 */
public class LDIFParserUtil {

    /**
     * Given an LDIF file line starting with a specified attibute Name return
     * the attribute value.
     * @param line
     * @param attribName the attibute Name (e.g. cn: o: giveName:)
     * @return attribute value
     */
    public static String getAttributeValue(String line, String attribName)throws IOException{
        String value = line.substring(attribName.length());
        value = value.trim();
        if(value.startsWith(": ")){
            value = value.substring(2);
            value = Base64Utils.decode(value);
        }
        return value;
    }



    public static void main(String[] args)throws IOException{
        System.out.println(getAttributeValue("bob:: QU1BTkRBIEZJTkxBWVNPTiA=", "bob:"));
    }

}
