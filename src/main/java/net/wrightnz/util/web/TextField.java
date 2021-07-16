/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.wrightnz.util.web;

/**
 * Simple class which renders an HTML form input element of type text.
 *
 * @author Richard Wright
 */
public class TextField extends InputWithValue implements java.io.Serializable{

    private Integer maxLength;
    private Integer size;
    private Boolean readonly = false;

    public TextField(){
    }

    public TextField(String name, Object value){
        super(name, value);
    }

    public TextField(String name, Object value, Boolean readonly){
        super(name, value);
        this.readonly = readonly;
        if(value != null){
            this.maxLength = value.toString().length();
        }
    }

    public TextField(String name, Object value, int maxLength){
        this(name, value);
        this.maxLength = maxLength;
    }

    public TextField(String name, Object value, String maxLength, String size){
        this(name, value, new Integer(maxLength), new Integer(size));
    }

    public TextField(String name, Object value, int maxLength, int size){
        super(name, value);
        this.maxLength = maxLength;
        this.size = size;
    }

    public int getMaxLength() {
        return maxLength;
    }

    public void setMaxLength(int maxLength) {
        this.maxLength = maxLength;
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
        sb.append(super.getStart());
        if(readonly.booleanValue()){
            sb.append(" readonly ");
        }
        if(maxLength != null){
            sb.append("maxLength=\"");
            sb.append(getMaxLength());
            sb.append("\" ");
        }
        if(size != null){
            sb.append("size=\"");
            sb.append(getSize());
            sb.append("\" ");
        }

        sb.append(" ");
        sb.append(super.getEnd());
        return sb.toString();
    }

    public static void main(String[] args){
        TextField text = new TextField("name", "value", "10", "10");
        text.setAdditionalAttributes("DISABLED");
        System.out.println(text);
        System.out.println(new TextField("name", null, 32, 20));
        System.out.println(new TextField("name", "value"));
    }

    @Override
    public String getType() {
        return "text";
    }

    /**
     * @return the readonly
     */
    public Boolean getReadonly() {
        return readonly;
    }

    /**
     * @param readonly the readonly to set
     */
    public void setReadonly(Boolean readonly) {
        this.readonly = readonly;
    }

}
