/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.wrightnz.awt.dnd;

import java.awt.Color;
import java.awt.Font;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import net.wrightnz.data.ValueObjectBase;
import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

/**
 *
 * @author Richard Wright
 */
public class FontAndColour extends ValueObjectBase{

    private Font font = new Font("Dialog",0, 12);
    private Color foreground = new Color(0, 0, 0, 255);
    private Color background = new Color(255, 255, 255, 255);

    private String fontName = "Dialog";
    private int fontStyle = 0;
    private int fontSize = 12;

    private String colourUse;
    private int foregroundRed;
    private int foregroundGreen;
    private int foregroundBlue;
    private int foregroundAlpha;

    private int backgroundRed;
    private int backgroundGreen;
    private int backgroundBlue;
    private int backgroundAlpha;


    public FontAndColour(){
    }

    public FontAndColour(String xml) throws UnsupportedEncodingException, JDOMException, IOException{
        ByteArrayInputStream inputStream  = new ByteArrayInputStream(xml.getBytes("UTF-8"));
        SAXBuilder builder = new SAXBuilder();
        Document doc = builder.build(inputStream);
        Element root = doc.getRootElement();
        expandElement(root);
        this.font = new Font(fontName, fontStyle, fontSize);
        this.foreground = new Color(foregroundRed, foregroundGreen, foregroundBlue, foregroundAlpha);
        this.background = new Color(backgroundRed, backgroundGreen, backgroundBlue, backgroundAlpha);
    }

    public FontAndColour(Font font, Color foreground, Color background) {
        this.font = font;
        this.foreground = foreground;
        this.background = background;
    }

    public String toXML(){
        StringBuilder sb = new StringBuilder();
        sb.append("<fontandcolour>\n");
        sb.append("    <font name=\"");
        sb.append(getFont().getName());
        sb.append("\" style=\"");
        sb.append(getFont().getStyle());
        sb.append("\" size=\"");
        sb.append(getFont().getSize());
        sb.append("\"/>\n");

        sb.append("    <colour use=\"foreground\" red=\"");
        sb.append(getForeground().getRed());
        sb.append("\" green=\"");
        sb.append(getForeground().getGreen());
        sb.append("\" blue=\"");
        sb.append(getForeground().getBlue());
        sb.append("\" alpha=\"");
        sb.append(getForeground().getAlpha());
        sb.append("\"/>\n");

        sb.append("    <colour use=\"background\" red=\"");
        sb.append(getBackground().getRed());
        sb.append("\" green=\"");
        sb.append(getBackground().getGreen());
        sb.append("\" blue=\"");
        sb.append(getBackground().getBlue());
        sb.append("\" alpha=\"");
        sb.append(getBackground().getAlpha());
        sb.append("\"/>\n");
        sb.append("</fontandcolour>\n");
        return sb.toString();
    }

    /**
     * @return the font
     */
    public Font getFont() {
        return font;
    }

    /**
     * @param font the font to set
     */
    public void setFont(Font font) {
        this.font = font;
    }

    /**
     * @return the foreground
     */
    public Color getForeground() {
        return foreground;
    }

    /**
     * @param foreground the foreground to set
     */
    public void setForeground(Color foreground) {
        this.foreground = foreground;
    }

    /**
     * @return the background
     */
    public Color getBackground() {
        return background;
    }

    /**
     * @param background the background to set
     */
    public void setBackground(Color background) {
        this.background = background;
    }

    public void expandElement(Element element){
        List<Element> elements = element.getChildren();
        for(Element e : elements){
            if("font".equals(e.getName())){
                List<Attribute> attributes = e.getAttributes();
                if(attributes != null && attributes.size() > 0){
                    for(Attribute attribute : attributes){
                        if("name".equals(attribute.getName())){
                            this.fontName = attribute.getValue();
                        }
                        else if ("style".equals(attribute.getName())){
                            this.fontStyle = Integer.parseInt(attribute.getValue());
                        }
                        else if ("size".equals(attribute.getName())){
                            this.fontSize = Integer.parseInt(attribute.getValue());
                        }
                    }
                }
            }
            else if("colour".equals(e.getName())){
                List<Attribute> attributes = e.getAttributes();
                if(attributes != null && attributes.size() > 0){
                    for(Attribute attribute : attributes){
                        if ("use".equals(attribute.getName())){
                            colourUse = attribute.getValue();
                        }
                        else if ("red".equals(attribute.getName())){
                            if("foreground".equals(colourUse)){
                                foregroundRed = Integer.parseInt(attribute.getValue());
                            }else{
                                backgroundRed = Integer.parseInt(attribute.getValue());
                            }
                        }
                        else if ("green".equals(attribute.getName())){
                            if("foreground".equals(colourUse)){
                                foregroundGreen = Integer.parseInt(attribute.getValue());
                            }else{
                                backgroundGreen = Integer.parseInt(attribute.getValue());
                            }
                        }
                        else if ("blue".equals(attribute.getName())){
                            if("foreground".equals(colourUse)){
                                foregroundBlue = Integer.parseInt(attribute.getValue());
                            }else{
                                backgroundBlue = Integer.parseInt(attribute.getValue());
                            }
                        }
                        else if ("alpha".equals(attribute.getName())){
                            if("foreground".equals(colourUse)){
                                foregroundAlpha = Integer.parseInt(attribute.getValue());
                            }else{
                                backgroundAlpha = Integer.parseInt(attribute.getValue());
                            }
                        }
                    }
                }
            }
            if(e != null){
                expandElement(e);
            }
        }
    }

    public static void main(String[] args)throws Exception{
        String xml = "<fontandcolour><font name=\"Roman\" style=\"2\" size=\"24\"/>" +
            "<colour use=\"foreground\" red=\"12\" green=\"200\" blue=\"120\" alpha=\"255\"/>" +
            "<colour use=\"background\" red=\"200\" green=\"200\" blue=\"200\" alpha=\"255\"/>" +
            "</fontandcolour>";

        FontAndColour fontAndColour = new FontAndColour(xml);
        System.out.println(fontAndColour.toString());
        System.out.println(fontAndColour.toXML());

    }

}
