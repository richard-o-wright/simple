/* 
 * Table.java 
 * 
 * Created on 8 June 2006, 13:51 
 * 
 * To change this template, choose Tools | Template Manager 
 * and open the template in the editor. 
 */
package net.wrightnz.util.web;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import net.wrightnz.util.Util;

/** 
 * Table Widget for creating html tables from an arbitrary List of Value 
 * objects. 
 *  
 * @author t466225
 */
public class Table implements java.io.Serializable {

    private List<?> rows = new ArrayList();
    private List<String> doNotDisplay = new ArrayList<String>();
    private String tableStart = "<table>";
    private String trStart = "<tr>";
    private String tdStart = "<td>";
    private String thStart = "<th>";

    /** Creates a new instance of Table */
    public Table() {
        doNotDisplay.add("getClass");
    }

    public List getRows() {
        return rows;
    }

    public void setRows(List<?> rows) {
        this.rows = rows;
    }

    public void setTableStart(String tableStart) {
        this.tableStart = tableStart;
    }

    public void setTrStart(String trStart) {
        this.trStart = trStart;
    }

    public void setTdStart(String tdStart) {
        this.tdStart = tdStart;
    }

    public void setThStart(String thStart) {
        this.thStart = thStart;
    }

    public void addToDoNotDisplay(String getterName) {
        this.doNotDisplay.add(getterName);
    }

    public void sort(Map<String, String[]> params) {
        if (params != null) {
            String orderBy = Util.getFirstValue(params, "orderBy");
            if (orderBy != null) {
                String className = "nz.org.richardw.util.web." + orderBy + "FieldComparator";
                Comparator<Object> comparator;
                try {
                    Class c = Class.forName(className);
                    comparator = (Comparator<Object>) c.newInstance();
                    Collections.sort(rows, comparator);
                }
                catch (Exception e) {
                    System.err.println("Could not load dynamic FieldComparator: " + e);
                    Comparator<Object> comp = new DefaultComparator(orderBy);
                    Collections.sort(rows, comp);
                }
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (getRows() != null && getRows().size() > 0) {
            Object row = getRows().get(0);
            List<Method> getters = getGetters(row);
            sb.append(tableStart);
            sb.append("\n");
            sb.append("  ");
            sb.append(trStart);
            sb.append("\n");
            for (Method getter : getters) {
                sb.append("    ");
                sb.append(thStart);
                sb.append("<a href=\"?orderBy=");
                sb.append(getter.getName());
                sb.append("\">");
                sb.append(headerFromMethod(getter));
                sb.append("</a>");
                sb.append("</th>\n");
            }
            sb.append("  </tr>\n");
            for (Object cRow : rows) {
                sb.append("  ");
                sb.append(trStart);
                sb.append("\n");
                for (Method getter : getters) {
                    sb.append("    ");
                    sb.append(tdStart);
                    sb.append(callGetter(cRow, getter));
                    sb.append("</td>\n");
                }
                sb.append("  </tr>\n");
            }
            sb.append("</table>\n");
        }
        return sb.toString();
    }

    protected String callGetter(Object obj, Method method) {
        try {
            Object object = method.invoke(obj, (Object[]) null);
            if (object instanceof net.wrightnz.util.web.Link) {
                return object.toString();
            } 
            else if (object != null) {
                return WebUtil.htmlEscape(object.toString());
            } 
            else {
                return "null";
            }
        } catch (IllegalAccessException iae) {
            System.err.println(iae.toString());
            return "Error could not access method " + method.getName() + "\n" + iae.toString();
        } catch (InvocationTargetException ite) {
            System.err.println(ite.toString());
            return "Error could not invoke method " + method.getName() + "\n" + ite.toString();
        }
    }

    /**
     *
     * @param row
     * @return
     */
    protected List<Method> getGetters(Object row) {
        List<Method> getters = new ArrayList<Method>();
        Class c = row.getClass();
        Method[] methods = c.getMethods();
        for (int i = 0; i < methods.length; i++) {
            Method method = methods[i];
            addToGetters(row, getters, method);
        }
        return getters;
    }

    private void addToGetters(Object row, List<Method> getters, Method method) {
        String methodName = method.getName();
        Class[] params = method.getParameterTypes();
        if (row instanceof TableRowInterface) {
            TableRowInterface tableRow = (TableRowInterface) row;
            if (tableRow.isPublished(method) && params.length == 0) {
                if (!doNotDisplay.contains(method.getName())) {
                    getters.add(method);
                }
            }
        } else {
            if (methodName.startsWith("get") && params.length == 0) {
                if (!doNotDisplay.contains(method.getName())) {
                    getters.add(method);
                }
            }
        }
    }

    /**     *      *      * @param method     * @return Hopefully a human readable heading based on the getter     *         method.     */
    protected String headerFromMethod(Method method) {
        String name = method.getName();
        int endGet = 0;
        if (name.startsWith("getLink")) {
            endGet = (name.indexOf("getLink")) + 7;
        } else {
            endGet = (name.indexOf("get")) + 3;
        }
        String methodWithoutGet = name.substring(endGet, name.length());
        return deriveHeading(methodWithoutGet);
    }

    private String deriveHeading(String getter) {
        StringBuilder heading = new StringBuilder();
        for (int i = 0; i < getter.length(); i++) {
            if (Character.isUpperCase(getter.charAt(i)) && i > 0) {
                heading.append(" ");
            }
            heading.append(getter.charAt(i));
        }
        return WebUtil.htmlEscape(heading.toString());
    }

    public static void main(String[] args) {
        Table table = new Table();
        List<String> rows = new ArrayList<String>();
        String s = "Test";
        rows.add(s);
        table.setRows(rows);
        System.out.println(table);
    }
}