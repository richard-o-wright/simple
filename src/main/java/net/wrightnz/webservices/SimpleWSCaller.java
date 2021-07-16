/*
 * SimpleWSCaller.java
 *
 * Created on October 8, 2004, 4:10 PM
 */

package net.wrightnz.webservices;

import java.net.*;
import java.util.*;
import java.io.*;

/**
 *
 * @author  richardw
 */
public class SimpleWSCaller {
    
    static String msg = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
                 "<soapenv:Envelope>" +
                 "<soapenv:Body>" +
                 "<getVersionRequest soapenv:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\">" +
                 "<getVersion>" +
                 "</getVersion>" +
                 "</getVersionRequest>" + 
                 "</soapenv:Body>" +
                 "</soapenv:Envelope>";
    
    /** Creates a new instance of SimpleWSCaller */
    public SimpleWSCaller() {
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try{
            // http://localhost:8080/axis/services/Version?method=getVersion
            URL url = new URL("http://localhost:8080/axis/services/Version");       
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Length", "" + msg.getBytes("UTF-8").length);
            connection.setRequestProperty("SOAPAction", "http://localhost:8080/axis/services/Version");
            //connection.setDoInput(true);    
            connection.setDoOutput(true);
            connection.connect();
            PrintWriter out = new PrintWriter(connection.getOutputStream());
            out.println(msg);
            out.close();
        
            InputStream is = connection.getInputStream();
            BufferedReader in = new BufferedReader(new InputStreamReader(is));          
            String inputLine;
            while ((inputLine = in.readLine()) != null){
                System.out.println(inputLine);
            }
            in.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    
}
