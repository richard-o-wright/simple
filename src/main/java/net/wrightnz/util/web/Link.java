/* 
 * Link.java 
 * 
 * Created on 14 November 2007, 16:21 
 * 
 * To change this template, choose Tools | Template Manager 
 * and open the template in the editor. */
package net.wrightnz.util.web;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/** 
 * Simple class which renders an HTML  anker element with an href attribute
 * and label.
 * 
 * @author t466225
 */
public class Link implements Serializable, Comparable<Link> {

    private String url;
    private String label;
    private String additionalAttribute;
    private boolean active;
    private Map<String, String> parameters;

    /** Creates a new instance of Link */
    public Link(String url) {
        this(url, null, true);
        this.setLabel(this.deriveLabel(url));
    }

    /** Creates a new instance of Link */
    public Link(String url, String label) {
        this(url, label, true);
    }

    /** Creates a new instance of Link */
    public Link(String url, String label, boolean active) {
        this(url, label, active, null);
    }

    /** Creates a new instance of Link */
    public Link(String url, String label, boolean active, Map<String, String> parameters) {
        this.setURL(url);
        this.setLabel(label);
        this.setActive(active);
        this.setParameters(parameters);
    }

    public String getURL() {
        return this.url;
    }

    /**
     * Adds additional attributes to a anker tag that are not otherwise
     * supported by this class.
     *
     * @param str attributes to add.
     */
    public void setAdditionalAttribute(String str) {
        this.additionalAttribute = str;
    }

    public void setURL(String url) {
        this.url = url;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    /**     *      * @return an HTML representation of this link.     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (isActive()) {
            sb.append("<a href=\"");
            if (getURL() != null) {
                sb.append(getURL());
            }
            if (getParameters() != null) {
                try {
                    appendParameters(sb);
                } catch (UnsupportedEncodingException ex) {
                    System.err.println("Problem encoding parameters in nz.co.telecom.idt.webapps.webutil.Link " + ex);
                }
            }
            sb.append("\" ");
            if(additionalAttribute != null){
                sb.append(additionalAttribute);
            }
            sb.append(">");
            sb.append(getLabel());
            sb.append("</a>");
        } else {
            sb.append(getLabel());
        }
        return sb.toString();
    }

    private String deriveLabel(String href) {
        int lastSlash = href.lastIndexOf("/");
        String l = "";
        if (lastSlash >= 0) {
            l = href.substring(lastSlash + 1, href.length());
        } else {
            l = href;
        }
        l = l.replaceAll(".jsp", "");
        StringBuilder newLabel = new StringBuilder();
        for (int i = 0; i < l.length(); i++) {
            if (Character.isUpperCase(l.charAt(i)) && i > 0) {
                newLabel.append(" ");
            }
            newLabel.append(l.charAt(i));
        }
        return newLabel.toString();
    }

    /**     * Main method used to test this class     * @param args     */
    public static void main(String[] args) {
        Link link = new Link("http://www.blah.com/files/BlahBlah.jsp");
        System.out.println(link.toString());
        link.setURL(null);
        Map<String, String> parameters = new HashMap<String, String>();
        parameters.put("name", "value");
        parameters.put("name &2", "value &2");
        parameters.put("name &3", "?&?//&");
        link.setParameters(parameters);
        link.setAdditionalAttribute("onclick=\"javascript.doSomeStuff(this.form)\"");
        System.out.println(link.toString());
    }

    public Map<String, String> getParameters() {
        return parameters;
    }

    public void setParameters(Map<String, String> parameters) {
        this.parameters = parameters;
    }

    private void appendParameters(StringBuilder sb) throws UnsupportedEncodingException {
        Set<String> names = getParameters().keySet();
        int i = 0;
        sb.append("?");
        for (String name : names) {
            sb.append(URLEncoder.encode(name, "UTF-8"));
            sb.append("=");
            String parameter = getParameters().get(name);
            if (parameter != null) {
                String value = URLEncoder.encode(parameter, "UTF-8");
                sb.append(value);

            }
            i++;
            if (i < names.size()) {
                sb.append("&");
            }
        }
    }

    /**
     * Links are compared based on a string comparison of there labels
     *
     * @param that
     * @return 0 if links are equal -1 it this link is less then link o, * 1 if
     * this link is greater then link o;
     */
    @Override
    public int compareTo(Link that) {
        String thisLabel = this.getLabel();
        String thatLabel = that.getLabel();
        return thisLabel.compareTo(thatLabel);
    }
}