/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.wrightnz.database;

import java.util.ArrayList;
import java.util.List;

import net.wrightnz.data.ValueObjectBase;

/**
 *
 * @author t466225
 */
public class Row extends ValueObjectBase{

    private List<String> values = new ArrayList<String>();

    /**
     * @return the values
     */
    public List<String> getValues() {
        return values;
    }

    /**
     * @param values the values to set
     */
    public void setValues(List<String> values) {
        this.values = values;
    }

    public void addValue(String value) {
        this.values.add(value);
    }

}
