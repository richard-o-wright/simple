/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.wrightnz.util.web;

/**
 * This is a base class for all form input elements that have both a name and
 * a value e.g. button, submit, hidden, text etc.
 *
 * @author T466225
 */
public abstract class InputWithValue extends Input{

    private Object value;

    public InputWithValue(){
    }

   public InputWithValue(String name, Object value){
       this.setName(name);
       this.value = value;
   }

    @Override
   public String getStart(){
        StringBuilder sb = new StringBuilder();
        sb.append(super.getStart());
        if(getValue() != null && !"null".equalsIgnoreCase(getValue().toString())){
            sb.append(" value=\"");
            sb.append(getValue());
            sb.append("\" ");
        }
        return sb.toString();
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

}
