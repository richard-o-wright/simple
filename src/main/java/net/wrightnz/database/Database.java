/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.wrightnz.database;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import net.wrightnz.util.xml.XMLPrinter;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

/**
 *
 * @author Richard Wright richard@wrightnz.net
 */
public class Database {

    public static final String DB_DOMAIN = "wrightnz.net";

    private ResultSet resultSet = new ResultSet();

    /**
     * Execute a query against the remote database.
     * 
     * @param table the table to query
     * @param where the where cause to filter the response by. If no where clause
     *        is required specify null for this parameter.
     * @return containing the rows in the response
     * @throws MalformedURLException
     * @throws IOException
     * @throws JDOMException
     */
    public ResultSet executeQuery(String table, String where) throws MalformedURLException, IOException, JDOMException{
        URLConnection connection = buildURL(table, where).openConnection();
        InputStream in = connection.getInputStream();
        try {
            return parseResponse(in);
        } finally {
            in.close();
        }
    }

    private URL buildURL(String table, String where) throws MalformedURLException{
        StringBuilder sb = new StringBuilder();
        sb.append("http://");
        sb.append(DB_DOMAIN);
        sb.append("/Query.php?table=");
        sb.append(table);
        if (where != null) {
            sb.append("&where=");
            sb.append(where);
        }
        URL url = new URL(sb.toString());
        return url;
    }


    /**
     * Main method for unit testing only
     * 
     * @param args
     * @throws Exception
     */
    public static void main(String[] args)throws Exception{
       ResultSet rs = new Database().executeQuery("CONTENT_ITEMS", null);
       System.out.println(rs);
    }


    private ResultSet parseResponse(InputStream in) throws JDOMException, IOException{
        SAXBuilder builder = new SAXBuilder();
        Document doc = builder.build(in);
        Element root = doc.getRootElement();
        expandElement(root);
        new XMLPrinter().expandElement(root, 0);
        return resultSet;
    }


    private void expandElement(Element element){
        List<Element> elements = element.getChildren();
        for(Element e : elements){
            if("query".equals(e.getName())){
                resultSet.setQuery(e.getValue());
            }
            if("row".equals(e.getName())){
                Row row = new Row();
                List<Element> children = e.getChildren();
                for(Element c : children){
                    if("field".equals(c.getName())){
                        String value = c.getValue();
                        row.addValue(value);
                    }
                }
                resultSet.addRow(row);
            }
            if(e != null){
                expandElement(e);
            }
        }
    }

}
