/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.wrightnz.util.xml;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.Set;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

/**
 * Class for doing XSLT transformations.
 * 
 * @author T466225
 */
public class XSLTransformer {
    
    private String xmlDocument;
    private InputStream styleSheetStream;

    /**
     * 
     * @param xmlDocument
     * @param styleSheetStream read the styleSheet from.
     */
    public XSLTransformer(String xmlDocument, InputStream styleSheetStream){
        this.xmlDocument = xmlDocument;
        this.styleSheetStream = styleSheetStream;
    }

    public String transform(Map<String, String[]> params) throws TransformerConfigurationException, UnsupportedEncodingException, TransformerException{        
        StreamSource xslStream = new StreamSource(styleSheetStream);
        //Create Transformer
        Transformer transformer = TransformerFactory.newInstance().newTransformer(xslStream);
        addParameters(transformer, params);
        ByteArrayInputStream bais = new ByteArrayInputStream(xmlDocument.getBytes("UTF-8"));
        Source xmlStream = new StreamSource(bais);
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        StreamResult scrResult = new StreamResult(pw);
        // Do the transform
        transformer.transform(xmlStream, scrResult);
        return sw.toString();
    }

    private void addParameters(Transformer transformer, Map<String, String[]>  params){
        if(params != null){
            Set<String> keys = params.keySet();
            for(String key : keys){
                String[] values = params.get(key);
                for(int i = 0; i < values.length; i++){
                    transformer.setParameter(key, values[i]);
                }
            }
        }
    }

    public static void main(String[] args)throws Exception{ 
        String xml = "<resultset><result>" +
      "<DESCRIPTION>Test job for 2009</DESCRIPTION>" +
      "<WBSE>12345/635736</WBSE>" +
      "<WORK_DATE>2009-06-08</WORK_DATE>" +
      "<HOURS>3</HOURS></result></resultset>";
        XSLTransformer service = new XSLTransformer(xml, XSLTransformer.class.getResourceAsStream("ReportResult.xsl"));
        System.out.println(service.transform(null));
    }

}
