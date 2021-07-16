/* 
 * CheckBox.java 
 * 
 * Created on 5 April 2006, 14:20 
 * 
 * To change this template, choose Tools | Template Manager 
 * and open the template in the editor. 
 */

package net.wrightnz.util.web;
        
import java.io.Serializable;
import java.util.Map;
import net.wrightnz.util.Util;

/** 
 * Simple class which renders an HTML form input time checkbox and
 * an associated label element.
 * 
 * @author t466225 
 */
public class CheckBox extends Input implements Serializable {

    private String label;
    private boolean checked;

    /** Creates a new instance of CheckBox */
    public CheckBox() {
    }

    /** Creates a new instance of CheckBox */
    public CheckBox(String name, String label, boolean isChecked) {
        this.setName(name);
        this.label = label;
        this.checked = isChecked;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public void setChecked(Map<String, String[]> parameter) {
        assert getName() != null;
        String checkedString = Util.getFirstValue(parameter, getName());
        if ("on".equals(checkedString)) {
            this.setChecked(true);
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("<label for=\"");
        sb.append(getName());
        sb.append("\">");
        sb.append(getLabel());
        sb.append("</label>");
        sb.append(this.getStart());
        if (isChecked()) {
            sb.append("CHECKED ");
        }
        sb.append(this.getEnd());
        return sb.toString();
    }

    @Override
    public String getType() {
        return "checkbox";
    }
    
    /**
     * Main class for Unit testing only.
     * 
     * @param args
     */
    public static void main(String[] args){
        CheckBox checkbox = new CheckBox();
        checkbox.setLabel("Test");
        checkbox.setName("test");
        checkbox.setID("foo");
        checkbox.setAdditionalAttributes("onclick=foo()");
        checkbox.setChecked(true);
        System.out.println(checkbox);
    }
    
}