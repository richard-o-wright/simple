/*
 * Select.java
 *
 * Created on 5 December 2005, 14:49
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package net.wrightnz.util.web;

import java.util.List;

/**
 * Simple class which renders an HTML form select element.
 *
 * @author t466225
 */
public class Select implements java.io.Serializable{

    private String name;
    private ValueDescriptions items;
    private String selectedValue;
    private int size = 0;
    private String additionalAttributes;

    /** Creates a new instance of Select */
    public Select() {
    }

    /** Creates a new instance of Select
     * @param name
     * @param items
     * @param selectedValue */
    public Select(String name, ValueDescriptions items, String selectedValue) {
        this.name = name;
        this.items = items;
        this.selectedValue = selectedValue;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setItems(ValueDescriptions items) {
        this.items = items;
    }

    public String getSelectedValue() {
        return selectedValue;
    }

    public void setSelectedValue(String selectedValue) {
        this.selectedValue = selectedValue;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }


    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("<select name=\"");
        sb.append(getName());
        if(getSize() != 0){
            sb.append("\" size=\"");
            sb.append(getSize());
        }
        sb.append("\"");
        if(getAdditionalAttributes() != null){
            sb.append(" ");
            sb.append(getAdditionalAttributes());
        }
        sb.append(">\n");
        if(items != null){
            List<String> values = items.keys();
            if(getSelectedValue() == null){
                for(String v : values){
                    setSelectedValue(v);
                    break;
                }
            }
            for(String value : values){
                sb.append("<option value=\"");
                sb.append(value);
                sb.append("\"");
                if(value.equals(getSelectedValue())){
                    sb.append(" SELECTED ");
                }
                sb.append(">");
                sb.append(items.get(value));
                sb.append("</option>\n");
            }
        }
        sb.append("</select>");
        return sb.toString();
    }

    public String getAdditionalAttributes() {
        return additionalAttributes;
    }

    public void setAdditionalAttributes(String additionalAttributes) {
        this.additionalAttributes = additionalAttributes;
    }

}
