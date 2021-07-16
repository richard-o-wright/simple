/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.wrightnz.util.web;

/**
 *
 * @author T466225
 */
public class SubmitButton extends InputWithValue {
    
    public SubmitButton(String name, String value){
        this.setName(name);
        this.setValue(value);
    }

    @Override
    public String getType() {
        return "submit";
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getStart());
        sb.append(getEnd());
        return sb.toString();
    }
    
    public static void main(String[] args){
        System.out.println(new SubmitButton("name", "value"));
        System.out.println(new SubmitButton("name", "NULL"));
    }

}
