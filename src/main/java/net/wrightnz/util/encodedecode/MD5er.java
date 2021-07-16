package net.wrightnz.util.encodedecode;
/*
 * MD5er.java
 *
 * Created on May 11, 2004, 9:16 AM
 */

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import java.sql.*;

/**
 *
 * @author  richardw
 */
public class MD5er {
    
    /** Creates a new instance of MD5er */
    public MD5er()throws IllegalStateException, SQLException, NoSuchAlgorithmException{
        // DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
        System.out.println(authenticate("adamc", "nACv72jX"));
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception{
        new MD5er();
    }

    /**
     * Authenticates the specified requester & password pair.
     *
     * @param userName  
     * @param password  The password of the requester.
     *
     * @return True if the authentication succeeds; false if not.
     *
     * @throws BESIRequesterException   If we could not read the requester details
     *                                  from the database.
     */
    public boolean authenticate(String userName, String password)throws IllegalStateException, SQLException, NoSuchAlgorithmException{
        
        byte [] hashedPassword = null;  
        MessageDigest digest = MessageDigest.getInstance("MD5");
        hashedPassword = digest.digest(password.getBytes());
        
        // First, get the password.
        String retrievedPassword = null;
        Connection con = DriverManager.getConnection("jdbc:oracle:thin:admanager/doubleclick@orc.team.xtra.co.nz:1524:TXNGD");  
        PreparedStatement stmt = con.prepareStatement("select PASSWORD from NG_USERS where NAME = ?");
        stmt.setString(1, userName);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            retrievedPassword = rs.getString(1);
        } 
        else {
            throw new SQLException("No such User");
        }
        rs.close();
        con.close();
        // This is converting the stored password to digest form
        System.out.println("Retrieved password: " + retrievedPassword);
        System.out.println("Retrieved password lenght: " + retrievedPassword.length());
        byte [] realPassword = convertToDigest(retrievedPassword); 
        return MessageDigest.isEqual(realPassword, hashedPassword);
    }
    
    private static char [] charMap = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    private String printPwd(byte [] mPassword){
        
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

    private byte [] convertToDigest(String mPassword)throws IllegalStateException{
        if (mPassword.length() % 2 != 0) {
            throw new IllegalStateException("Hashed password must be of even length!");
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
    
    /**
     * Determines if the specified requester exists.
     *
     * @param mRequesterId   The id of the requester.
     *
     * @return True if the requester exists; false if not.
     *
     * @throws BESIRequesterException   If we could not talk to the database.
     */
    /*
    public boolean doesRequesterExist(RequesterId mRequesterId)
        throws Exception{
        final String method = "doesRequesterExist()";

        PreparedStatement stmt = null;
        Connection connection = null;
        boolean requesterExists = false;
        
        try {
            connection = DBUtils.getConnection(DATASOURCE);

            stmt = connection.prepareStatement("select * from requesters where user_id = ? and domain = ?");
            stmt.setString(1, mRequesterId.getId());
            stmt.setString(2, mRequesterId.getDomain());

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                requesterExists = true;
            }
        } catch (SQLException se) {
            throw new BESIRequesterException("SQL execution failed: " + se.getMessage());
        } catch (NamingException e) {
            throw new BESIRequesterException("SQL execution failed in: " + e.getMessage());
        } finally {
            DBUtils.closeConnection(connection, stmt);
        }

        Logger.exit(this, method, "Ok");
        return requesterExists;
    }
    */
    /**
     * Obtains requester data from the database.  Note that, as of phase I
     * of CDP0 R&S there is no such data (except for the requester id).
     *
     * @param mRequesterId  The id of the requester whose data we wish to fetch.
     * 
     * @return The data of the requester.
     *
     * @throws BESIRequesterException   If we could not read the data (including
     *                                  if the requester doesn't exist).
     */
    /*
    public RequesterData getRequesterData(RequesterId mRequesterId)
        throws BESIRequesterException
    {
        final String method = "getRequesterData()";
        Logger.enter(this, method);
        Logger.trace(this, method, "Requester is " + mRequesterId);

        PreparedStatement stmt = null;
        Connection connection = null;
        RequesterData data = null;
        
        try {
            connection = DBUtils.getConnection(DATASOURCE);

            stmt = connection.prepareStatement("select * from requesters where user_id = ? and domain = ?");
            stmt.setString(1, mRequesterId.getId());
            stmt.setString(2, mRequesterId.getDomain());

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                // We don't bother looking at what comes back from the database
                // because currently all we care about is the requester id anyway.
                // This will probably change in the future.
                data = new RequesterData(mRequesterId);
            } else {
                throw new BESIRequesterException("No such requester");
            }
        } catch (SQLException se) {
            throw new BESIRequesterException("SQL execution failed: " + se.getMessage());
        } catch (NamingException e) {
            throw new BESIRequesterException("SQL execution failed in: " + e.getMessage());
        } finally {
            DBUtils.closeConnection(connection, stmt);
        }

        Logger.exit(this, method, "Ok");
        return data;
    }
    */
}
