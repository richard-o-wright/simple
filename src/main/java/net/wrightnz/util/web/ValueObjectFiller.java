/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.wrightnz.util.web;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Class which calles the various setter on a specified value object setting them
 * based on the values found in a specified http request.
 * @author t466225
 */
public class ValueObjectFiller {
    
    private Object object;
    private Map<String, String[]> parameters;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public ValueObjectFiller(Object obj, Map<String, String[]> parameters){
        this.object = obj;
        this.parameters = parameters;
    }

    /**
     * Populates The value object specified on the constructer with the values
     * from the Map also specified on the constructer.
     * 
     * @throws java.lang.IllegalAccessException if there is a problem calling one of the required getters
     * @throws java.lang.reflect.InvocationTargetException if there is a problem calling one of the required getters
     * @throws java.text.ParseException if a getter parameter type is date and the correct SimpleDateFormat is not set.
     */
    public void populate()throws IllegalAccessException, InvocationTargetException, ParseException{
        List<Method> setters = getSetters();
        Set<String> names = parameters.keySet();
         for(String name : names){
            String setterName = parameterNameToMethodName(name);
            for(Method method : setters){
                String methodName = method.getName();
                System.out.println("Method name: " + methodName + ", Setter name: " + setterName);
                if(methodName.equals(setterName)){
                    String[] values = parameters.get(name);
                    callMethod(method, values[0]);
                }
            }
        }
    }

    private List<Method> getSetters() {
        List<Method> setters = new ArrayList<Method>();
        Class c = object.getClass();
        Method[] methods = c.getMethods();
        for (int i = 0; i < methods.length; i++) {
            Method method = methods[i];
            String methodName = method.getName();
            if (methodName.startsWith("set")) {
                setters.add(method);
            }
        }
        return setters;
    }

    private void callMethod(Method method, String parameter) throws IllegalAccessException, InvocationTargetException, ParseException {
        Object[] param = new Object[1];
        Class[] types = method.getParameterTypes();
        if(types.length > 1){
            throw new RuntimeException("Only setters with a single parameter are supported");
        }
        if(types[0].equals(Float.class)){
            Float f = Float.parseFloat(parameter);
            param[0] = f;
        }
        else if(types[0].equals(Double.class)){
            Double d = Double.parseDouble(parameter);
            param[0] = d;
        }
        else if(types[0].equals(Integer.class)){
            Integer i = Integer.parseInt(parameter);
            param[0] = i;
        }
        else if(types[0].equals(Long.class)){
            Long l = Long.parseLong(parameter);
            param[0] = l;
        }
        else if(types[0].equals(String.class)){
            param[0] = parameter;
        }
        else if(types[0].equals(Date.class)){
            param[0] = dateFormat.parse(parameter);
        }
        else {
            throw new RuntimeException("Unsupported setter parameter type (String, Integer, Long, Float and java.util.Date are supported)");
        }
        method.invoke(object, param);
    }

    private String parameterNameToMethodName(String name) {
        StringBuilder sb = new StringBuilder();
        String firstChar = name.substring(0, 1);
        sb.append("set");
        sb.append(firstChar.toUpperCase());
        sb.append(name.substring(1, name.length()));
        return sb.toString();
    }

    public static void main(String[] args)throws Exception{
        Map<String, String[]> params = new HashMap<String, String[]>();
        String[] v1 = {"value"};
        params.put("name", v1);
        String[] v2 = {"1"};
        params.put("longNumber", v2);
        String[] v3 = {"10"};
        params.put("integer", v3);
        String[] v4 = {"40.234"};
        params.put("floatNumber", v4);
        String[] v5 = {"2009-05-10 12:40:31"};
        params.put("date", v5);
        String[] v6 = {"1234.675"};
        params.put("doubleNumber", v6);
        TestObject test = new TestObject();
        ValueObjectFiller filler = new ValueObjectFiller(test, params);
        System.out.println(filler.parameterNameToMethodName("name"));
        filler.populate();
        System.out.println(test.getName());
        System.out.println(test.getInteger());
        System.out.println(test.getLongNumber());
        System.out.println(test.getFloatNumber());
        System.out.println(test.getDoubleNumber());
        System.out.println(test.getDate());
    }

    /**
     * @return the dateFormat
     */
    public SimpleDateFormat getDateFormat() {
        return dateFormat;
    }

    /**
     * @param dateFormat the dateFormat to set
     */
    public void setDateFormat(SimpleDateFormat dateFormat) {
        this.dateFormat = dateFormat;
    }

}
