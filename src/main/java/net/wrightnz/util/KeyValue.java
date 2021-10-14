package net.wrightnz.util;

import net.wrightnz.data.ValueObjectBase;

/**
 *
 * @author  Richard Wright
 */
public class KeyValue<T> extends ValueObjectBase implements java.io.Serializable{
    
    private String key;
    private T value;
    
    /**
     * Create a new TagAndValue
     * @param tag
     * @param value
     */
    public KeyValue(String tag, T value) {
        this.key = tag;
        this.value = value;
    }
    
    public String getTag() {
        return key;
    }
    
    public T getValue() {
        return value;
    }
    
    public void setTag(String tag) {
        this.key = tag;
    }
    
    public void setValue(T value) {
        this.value = value;
    }
        
}