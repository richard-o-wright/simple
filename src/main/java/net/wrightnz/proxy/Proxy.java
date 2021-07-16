/*
 * Proxy.java
 *
 * Created on February 5, 2003, 3:59 PM
 */

package net.wrightnz.proxy;

import java.net.*;
import java.io.*;

/**
 *
 * @author  RichardW
 */
public class Proxy {

    /** Creates a new instance of Proxy
     * @throws java.lang.Exception */
    public Proxy() throws Exception {
        ServerSocket ss = new ServerSocket(43);
        Socket socket = ss.accept();
        InputStream in = socket.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        DataOutputStream out = new DataOutputStream(socket.getOutputStream());
        String line;
        while((line = br.readLine()) != null){
            Whois whois = new Whois("whois.srs.net.nz", line);
            String response = whois.whois();
            out.writeBytes(response + "\n");
        }
    }

    /**
     * @param args the command line arguments
     * @throws java.lang.Exception
     */
    public static void main(String[] args) throws Exception {
        Proxy proxy = new Proxy();
    }

}
