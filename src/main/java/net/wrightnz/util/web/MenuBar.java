/*
 * MenuBar.java
 *
 * Created on 14 November 2007, 11:02
 *
 */
package net.wrightnz.util.web;

import java.util.List;
import java.util.ArrayList;

/**
 * HTML widget to display an menu nar of links on the web page.
 * 
 * @author t466225
 */
public class MenuBar implements java.io.Serializable{

    private static List<Link> links = new ArrayList<Link>();   
    private String linkSeparator = " | ";
    
    /** 
     * Creates a new instance of MenuBar the specified list of links
     */
    public MenuBar(List<Link> links) {
        MenuBar.links = links;
    }

    /**
     * 
     * @param label the display name of the link to make selected.
     */
    public synchronized void setSelectedByLabel(String label){
        for (Link link : links){
            if(link.getLabel().equalsIgnoreCase(label)){
                link.setActive(false);
            }
            else{
               link.setActive(true);
            }
        }      
    }

    
    /**
     * 
     * @param href the href of the link to make selected
     */
    public synchronized void setSelectedByHREF(String href){ 
        for (Link link : links){
            if(link.getURL().equalsIgnoreCase(href)){
                link.setActive(false);
            }
            else{
               link.setActive(true);
            }
        } 
    }
   
    public synchronized void addLink(String href, String label){
        links.add(new Link(href, label, true));
    }    

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(linkSeparator);
        int i = 0;
        for (Link link : links){
            sb.append(link.toString());
            i++;
            if(i != links.size()){
                sb.append(linkSeparator);
            }
        } 
        return sb.toString();
    }

    public String getLinkSeparator() {
        return linkSeparator;
    }

    public void setLinkSeparator(String linkSeparator) {
        this.linkSeparator = linkSeparator;
    }

}

