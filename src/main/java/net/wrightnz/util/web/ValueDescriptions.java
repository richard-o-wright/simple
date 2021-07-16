/*
 * ValueDescriptions.java
 *
 * Created on 14 December 2005, 14:44
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package net.wrightnz.util.web;

import java.util.ArrayList;
import java.util.List;

/**
 * The class is used by the Select Widget to carry the values 
 * and description needed to build the option list used in the
 * select. Might have been better to call it SelectOptionList
 * and make it private but it occures to me that it might have 
 * other uses so have kept the name generic and made it public.
 * 
 * @author t466225
 */
public class ValueDescriptions {
    
    List<ObjectPair> list = new ArrayList<ObjectPair>();
    
    /** 
     * Creates a new instance of ValueDescriptions 
     */
    public ValueDescriptions() {
    }
    
    
    public void put(String value, String description){
        list.add(new ObjectPair(value, description));
    }
    
    public String get(String value){
        for(ObjectPair item : list){
            String itemKey = (String)item.getObject1();
            if(itemKey.equals(value)){
                return (String)item.getObject2();
            }
        }
        return null;
    }
    
    public List<String> keys(){
        List<String> keys = new ArrayList<String>();
        for(ObjectPair item : list){
            keys.add((String)item.getObject1());
        }
        return keys;
    }
    
    public int size(){
        return list.size();
    }
    
    public void remove(String key){
        for(ObjectPair item : list){
            String itemKey = (String)item.getObject1();
            if(itemKey.equals(key)){
                list.remove(item);
            }
        }
    }
    
}

class ObjectPair{
    
    private Object obj1;
    private Object obj2;
    private boolean selected;
    
    public ObjectPair(Object obj1, Object obj2){
        this(obj1, obj2, false);
    }
    
    public ObjectPair(Object obj1, Object obj2, boolean selected){
        this.setObject1(obj1);
        this.setObject2(obj2);
        this.setSelected(selected);
    }

    public Object getObject1() {
        return obj1;
    }

    public void setObject1(Object obj) {
        this.obj1 = obj;
    }

    public Object getObject2() {
        return obj2;
    }

    public void setObject2(Object obj) {
        this.obj2 = obj;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
