package net.wrightnz.proxy;

import java.io.*;
import java.net.*;

/**
 *
 * @author  RichardW
 */
public class Whois  {

   /* Holds the search terms */
   private String serverURL;     // The URL whois server being queried
   private String targetDomain;    // Holds the query domain.


  /**
  * Initialize the host to query about.
  */
   public Whois(String su, String s){
       this.serverURL = su;
       this.targetDomain = s;
   }
     /**
    * PRE:           * IN:           * OUT:        java.lang.String. The whois daemon reply. With \n's.
    * THROWS:    IOexception. Socket could not be opened.
    */
   public String whois() throws UnknownHostException, IOException{
       BufferedReader reader = getWhoisReader();
       StringBuffer sb = new StringBuffer();
       String line = new String();
       /* Read in the response line by line and then return it to the calling program. */
       while((line = reader.readLine())!= null){
           sb.append(line + "\n");
       }
       return sb.toString();
   }
   
   public BufferedReader getWhoisReader() throws UnknownHostException, IOException{
       /* Open a new socket. */
       Socket socket = new Socket(serverURL, 43);
       socket.setSoTimeout(2000);
       /* Attach a BufferedReader to the socket to read in data. */
       BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
       /* Attatch a DataOutputStream to the socket to write data out. */
       DataOutputStream out = new DataOutputStream(socket.getOutputStream());
       /* After the connection, send the target query. */
       out.writeBytes(targetDomain+"\n");
       return in;
   }

} //EC 

