/*
 * MD5It.java
 *
 * Created on June 25, 2003, 1:22 PM
 */

package net.wrightnz.util.encodedecode;

import java.security.MessageDigest;


/**
 *
 * @author  RichardW
 */
public class MD5It {
    
    private static char [] charMap = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    
    /** Creates a new instance of MD5It */
    public MD5It(){
    }
        
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)throws Exception {
        String password;
        if(args.length == 1){
            password = args[0];
        }
        else {
           /*
            "jungledrumpass1"
            "catalystpass2"
            */
            password = "jungledrumpass1";
        }
        MessageDigest digest = MessageDigest.getInstance("MD5");
        byte[] hashedPassword = digest.digest(password.getBytes());
        System.out.println(printPwd(hashedPassword));
                 
        byte[] realPassword = convertToDigest(printPwd(hashedPassword)); 
        boolean returnValue = MessageDigest.isEqual(realPassword, hashedPassword);
        System.out.println(returnValue);
    }
    
    private static String printPwd(byte [] mPassword) {
        
        StringBuffer pwd = new StringBuffer();
        
        for (int i = 0; i < mPassword.length; i++) {
            int theByte = mPassword[i];
            if (theByte < 0) {
                theByte += 256;
            }
            pwd.append(charMap[theByte / 16]);
            pwd.append(charMap[theByte % 16]);
        }
        
        return pwd.toString();
    }
    
    private static byte [] convertToDigest(String mPassword) throws Exception {
        if (mPassword.length() % 2 != 0) {
            throw new Exception("Hashed password must be of even length!");
        }
        
        byte [] bytes = new byte[mPassword.length() / 2];
        for (int i = 0; i < mPassword.length(); i += 2) {
            char ch = mPassword.charAt(i);
            if ((ch >= '0') && (ch <= '9')) {
                bytes[i / 2] = (byte)(16 * (ch - '0'));
            } else {
                bytes[i / 2] = (byte)(16 * (ch - 'a' + 10));
            }
            ch = mPassword.charAt(i + 1);
            if ((ch >= '0') && (ch <= '9')) {
                bytes[i / 2] += (ch - '0');
            } else {
                bytes[i / 2] += (ch - 'a' + 10);
            }
        }
        return bytes;
    }
    
}
