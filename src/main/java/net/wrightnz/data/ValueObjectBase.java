package net.wrightnz.data;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import net.wrightnz.util.web.WebUtil;

/**
 *
 * @author t466225
 */
public class ValueObjectBase implements java.io.Serializable{

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getName());
        sb.append(":[");
        List<Method> getters = getGetters();
        for(Method getter : getters){
            sb.append(getter.getName());
            sb.append(" : ");
            sb.append(callGetter(getter));
            sb.append(", ");
        }
        sb.delete(sb.length() - 2, sb.length());
        sb.append("]\n");
        return sb.toString();
    }

    protected List<Method> getGetters() {
        List<Method> getters = new ArrayList<Method>();
        Class c = getClass();
        Method[] methods = c.getMethods();
        for (Method method : methods) {
            addToGetters(getters, method);
        }
        return getters;
    }

    protected void addToGetters(List<Method> getters, Method method) {
        String methodName = method.getName();
        Class[] params = method.getParameterTypes();
        if (methodName.startsWith("get") && params.length == 0 && !"getClass".equals(methodName)) {
            getters.add(method);
        }
    }

    protected String callGetter(Method method) {
        try {
            Object object = method.invoke(this, (Object[]) null);
            if (object != null) {
                return WebUtil.htmlEscape(object.toString());
            }
            else {
                return "null";
            }
        }
        catch (IllegalAccessException iae) {
            System.err.println(iae.toString());
            return "Error could not access method " + method.getName() + "\n" + iae.toString();
        }
        catch (InvocationTargetException ite) {
            System.err.println(ite.toString());
            return "Error could not invoke method " + method.getName() + "\n" + ite.toString();
        }
    }

}
