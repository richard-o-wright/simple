/*
 * WebUtil.java
 *
 * Created on 3 December 2007, 13:17
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package net.wrightnz.util.web;

import java.util.ArrayList;
import java.util.List;

/**
 * A Utility class for any stuff I cannot find a better 
 * place for.
 * 
 * @author t466225
 */
public class WebUtil {
       
    /**
     * Does it's best to format a plain test human readable string into
     * HTML that will display well.
     * 
     * @param string the plain text input.
     * @return the HTML formated version of the input.
     */
    public static String getStringAsHTML(String string){        
        if(string != null){
            List<String> lines = new ArrayList<String>();
            StringBuilder sb = new StringBuilder();
            for(int i = 0; i < string.length(); i++){
                char c = string.charAt(i);
                sb.append(htmlEscape(c));
                if(c == '\n'){
                    lines.add(sb.toString());
                    sb.delete(0, sb.length());
                }
                if(i == string.length() - 1){
                    lines.add(sb.toString());
                }
            }
            return doHTMLFormatting(lines);
        }
        else{
            return "None";
        }
    }
    
    /**
     * HTML escape the specified string (escapes & to &amp; and so on)
     * 
     * @param string the specified string
     * @return the HTML escaped version of the input string
     */
    public static String htmlEscape(String string){        
        if(string != null){
            StringBuilder sb = new StringBuilder();
            for(int i = 0; i < string.length(); i++){
                sb.append(htmlEscape(string.charAt(i)));
            }
            return sb.toString();
        }
        else{
            return "";
        }
    }
    
    /**
     * HTML escape the specified character (escapes & to &amp; and so on)
     * 
     * @param c the specified character
     * @return the HTML escaped version of the input character
     */
    public static String htmlEscape(char c){
        switch (c) {
            case '<':
                return "&lt;";
            case '>':
               return "&gt;"; 
            case '&':
               return "&amp;";               
            case '\"':
               return "&quot;";
            case '\'':
               return "&#039;";
            case'(' :
               return "&#040;";
            case ')' :
               return "&#041;";   
            case '#' :
               return "&#035;";
            case '%' :
                return "&#037;";
            case ';' :
                return "&#059;";
            case '+' :
                return "&#043;";
            case '-' :
                return "&#045;";  
            default:
                return String.valueOf(c);
        }
    }
    
    /**
     * I don't bother to comment private methods sue me:-)
     * 
     * @param lines
     * @return
     */
    private static String doHTMLFormatting(List<String> lines){
        StringBuilder sb = new StringBuilder();
        for(String line : lines){
            line = line.replace("  ", "&nbsp;&nbsp;");
            line = line.trim();
            if(line.length() == 0){
                sb.append("<p/>");
            }
            else if(line.startsWith("****")){
                sb.append("<h4>");
                sb.append(line.substring(4));
                sb.append("</h4>\n");
            }
            else if(line.startsWith("***")){
                sb.append("<h3>"); 
                sb.append(line.substring(3));
                sb.append("</h3>\n");
            }
            else if(line.startsWith("**")){
                sb.append("<h2>");
                sb.append(line.substring(2));
                sb.append("</h2>\n");
            }
            else {
                sb.append(line);
                sb.append("<br/>\n");   
            }
        }  
        return sb.toString();
    }
    
    
    public static void main(String[] args){
        System.out.println(getStringAsHTML("***Heading\n Some  tested like  this\n and another line\n**And Heading"));
    }
    
}
