/*
 * WidgetUtil.java
 *
 * Created on 21 November 2005, 11:38
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package net.wrightnz.util.web;

import java.util.List;

/**
 *
 * @author t466225
 */
public class WidgetUtil {
    
    /** 
     * This classes was probably a bad idea?
     * It's a factory for the various HTML widget classes but I've found it
     * to be largly useless. It may disappear in a latter version of the WebUtil
     * package.
     * 
     * Creates a new instance of WidgetUtil 
     */
    private WidgetUtil() {
    }
    
    /**
     * Create a new CheckBox widget using the specified parameters call 
     * toString() on said CheckBox widget and return the result.
     * 
     * @param name
     * @param label
     * @param isChecked
     * @param additionalAttributes
     * @return
     */
    public static String getCheckBox(String name, String label, boolean isChecked, String additionalAttributes){
        CheckBox checkBox = new CheckBox();
        checkBox.setName(name);
        checkBox.setLabel(label);
        checkBox.setChecked(isChecked);
        checkBox.setAdditionalAttributes(additionalAttributes);
        return checkBox.toString();
    }
    
    /**
     *
     * Create a new Select widget using the specified parameters call 
     * toString() on said Select widget and return the result. 
     *
     * @param name
     * @param items
     * @param selectedValue
     * @return
     */
    public static String getSelect(String name, ValueDescriptions items, String selectedValue){
        Select select = new Select();
        select.setName(name);
        select.setItems(items);
        select.setSelectedValue(selectedValue);
        return select.toString();
    }
    
    /**
     * Create a new Select widget using the specified parameters call 
     * toString() on said Select widget and return the result. 
     * 
     * @param name
     * @param items
     * @param selectedValue
     * @param additionalAttributes
     * @return
     */
    public static String getSelect(String name, ValueDescriptions items, String selectedValue, String additionalAttributes){
        Select select = new Select();
        select.setName(name);
        select.setItems(items);
        select.setSelectedValue(selectedValue);
        select.setAdditionalAttributes(additionalAttributes);
        return select.toString();
    }
    
    /**
     * Generate the required HTML code for a select elemant.
     * 
     * @param name
     * @param values
     * @param selectedValue
     * @return
     */
    public static String getSelect(String name, List<String> values, String selectedValue){
        StringBuilder sb = new StringBuilder();
        sb.append("<select name=\"");
        sb.append(name);
        sb.append("\">\n");
        for(String value : values){
            sb.append("<option value=\"");
            sb.append(value);
            sb.append("\" ");
            if(selectedValue != null && value.equals(selectedValue)){
                sb.append("SELECTED");
            }
            sb.append(">");
            sb.append(value);
            sb.append("</option>\n");            
        }
        sb.append("</select>\n");
        return sb.toString();
    }
    
}
