/*
 * UnMime.java
 *
 * Created on March 2, 2004, 9:19 AM
 */

package net.wrightnz.util.encodedecode;

// import com.sun.mail.util.BASE64EncoderStream;
// import com.sun.mail.util.BASE64DecoderStream;

//import java.security.MessageDigest;
//import java.security.NoSuchAlgorithmException;
//import java.net.URLEncoder;

/**
 *
 * @author  richardw
 */
public class EncodingTester {
    
    /** Creates a new instance of UnMime */
    public EncodingTester() {
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)throws Exception {
        /*
        String[] strings = {"Any old shit like this", "Blah", "1234", " ", "yet another ^&^&*() String", " lysaght"};
        
        for(int i = 0; i < strings.length; i++){
            byte[] b = strings[i].getBytes();
            byte[] eb = BASE64EncoderStream.encode(b);
            System.out.println(new String(eb));
        }
        
        String encoded = "SGkgUmljaGFyZCE=";
        byte[] s = BASE64DecoderStream.decode("IGx5c2FnaHQ=".getBytes());
        System.out.println(new String(s));
               
        MessageDigest digest = MessageDigest.getInstance("MD5");
        
        byte[] uid1 = digest.digest("x.richardw".getBytes());
        byte[] uid2 = digest.digest("x.richardw".getBytes());
        
        System.out.println("MD5s match is " + MessageDigest.isEqual(uid1, uid2));
        printPwd(uid1);
        printPwd(uid2);
        
        String doggyURL = "http://blah/ajdha?bahdah=gashdg&sadahd=saghdgas";
        System.out.println(URLEncoder.encode(doggyURL));
        */
    }
    
    private static void printPwd(byte [] mPassword) {
        
        StringBuffer pwd = new StringBuffer();
        
        for (int i = 0; i < mPassword.length; i++) {
            int theByte = mPassword[i];
            if (theByte < 0) {
                theByte += 256;
            }
            pwd.append(charMap[theByte / 16]);
            pwd.append(charMap[theByte % 16]);
        }
        System.out.println(pwd.toString());
    }
    
    private static char [] charMap = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    
    
    private static byte [] convertToDigest(String mPassword) throws Exception{
            
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
