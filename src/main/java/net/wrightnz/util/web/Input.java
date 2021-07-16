/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.wrightnz.util.web;

/**
 * Base class for all form Input elements. 
 * @author T466225
 */
public abstract class Input {
    
    private String name;
    private String additionalAttributes;
    private String id;
    
    /**
     * Get any additional attribute that may be included within this input
     * element such as javascript calls, disable and so on. 
     * @return
     */
    public String getAdditionalAttributes() {
        return additionalAttributes;
    }
    
    /**
     * Add any additional attribute that may be included within the input
     * element such as javascript calls, desable and so on. 
     * @param additionalAttributes
     */
    public void setAdditionalAttributes(String additionalAttributes) {
        this.additionalAttributes = additionalAttributes;
    }
    
    /**
     * This method must be implemented to return the type of this Input Element
     * for example "text", "checkbox", "hidden" etc.
     * 
     * @return the type of this form element.
     */
    public abstract String getType();
    
    
    public String getStart(){
        StringBuilder sb = new StringBuilder();
        sb.append("<input type=\"");
        sb.append(getType());
        sb.append("\" name=\"");
        sb.append(getName());
        sb.append("\" id=\"");
        if(getID() == null){
            sb.append(getName());
        }
        else{
            sb.append(getID());
        }
        sb.append("\" ");
        return sb.toString();
    }
    
    
    public String getEnd(){
        StringBuilder sb = new StringBuilder();
        if(additionalAttributes != null && 
                !"null".equalsIgnoreCase(additionalAttributes)){
            sb.append(additionalAttributes);
            sb.append(" ");
        }
        sb.append("/>");
        return sb.toString();
    }

    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    @Override
    public abstract String toString();

    /**
     * @return the id
     */
    public String getID() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setID(String id) {
        this.id = id;
    }
    
}
