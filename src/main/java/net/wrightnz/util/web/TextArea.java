/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.wrightnz.util.web;

/**
 * Simple class which renders an HTML form textarea element.
 * 
 * @author t466225
 */
public class TextArea implements java.io.Serializable{
    
    private String name;
    private String value;
    private int rows;
    private int columns;
    
    public TextArea(){
    }
    
    public TextArea(String name, String value, String r, String c){
        this.name = name;
        this.value = value;
        this.rows = Integer.parseInt(r);
        this.columns = Integer.parseInt(c);
    }
    
    public TextArea(String name, String value, int rows, int columns){
        this.name = name;
        this.value = value;
        this.rows = rows;
        this.columns = columns;
    }
        
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
    
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("<textarea name=\"");
        sb.append(getName());
        sb.append("\" cols=\"");
        sb.append(columns);
        sb.append("\" rows=\"");
        sb.append(rows);
        sb.append("\">");
        if(getValue() != null && !"null".equals(value)){
            sb.append(getValue());  
        }
        sb.append("</textarea>");
        return sb.toString();
    }
    
    public static void main(String[] args){
        System.out.println(new TextArea("name", "value", "70", "10"));
        System.out.println(new TextArea("name", null, "70", "10"));
    }

}
