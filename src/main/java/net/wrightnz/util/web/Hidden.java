/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.wrightnz.util.web;

/**
 *
 * @author T466225
 */
public class Hidden extends InputWithValue{
    
    public Hidden(){
    }
    
    public Hidden(String name, String value){
        super(name, value);
    }

    @Override
    public String getType() {
        return "hidden";
    }
    
    public static void main(String[] args){
        Hidden hidden = new Hidden("test", "2000");
        hidden.setAdditionalAttributes("onclick=foo()");
        System.out.println(hidden);
        hidden.setValue(null);
        System.out.println(hidden);
        hidden.setValue("null");
        hidden.setAdditionalAttributes(null);
        System.out.println(hidden);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.getStart());
        sb.append(this.getEnd());
        return sb.toString();
    }

}
