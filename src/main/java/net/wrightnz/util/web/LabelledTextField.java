/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.wrightnz.util.web;

/**
 *
 * @author Richard Wright
 */
public class LabelledTextField extends TextField{

    private String label;

    public LabelledTextField(String label, String name, String value){
        super(name, value);
        this.label = label;
    }

    /**
     * @return the label
     */
    public String getLabel() {
        return label;
    }

    /**
     * @param label the label to set
     */
    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("<label for=\"");
        sb.append(getName());
        sb.append("\">");
        sb.append(label);
        sb.append("</label>");
        sb.append(super.toString());
        return sb.toString();
    }

    public static void main(String[] args){
        LabelledTextField ltf = new LabelledTextField("label", "name", "value");
        System.out.println(ltf);
    }

}
