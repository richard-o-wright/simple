/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.wrightnz.database.testing;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Richard Wright
 */
public class ICMSOrderIdChecker {
    
    public static final String DB_URL = "jdbc:jtds:sqlserver://HP2289.telecom.tcnz.net:1433/SLAPAR";
    public static final String DB_USER = "SLAPAR_USER";
    public static final String DB_PASSWORD = "winter99";
    
    public ICMSOrderIdChecker(){        
    }
    
    public static void main(String[] args) throws Exception {
        DriverManager.registerDriver((Driver)Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance());
        Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        try {
            ICMSOrderIdChecker checker = new ICMSOrderIdChecker();
            checker.integerTest(conn);
            List<String> toLongIDs = checker.getToLongIDs(conn);
            for (String id : toLongIDs) {
                System.out.println(id);
            }
            checker.removeToLongIDs(conn, toLongIDs);
        } finally {
            conn.close();
        }
    }
    

    public void integerTest(Connection conn ) throws SQLException {   
        String sql = "select cast(ICMS_ORDER_ID as int) icms from ntck.feature_instance where ICMS_ORDER_ID is not null";
        PreparedStatement stmt = conn.prepareStatement(sql);
        ResultSet res = stmt.executeQuery();
        while (true) {
            try {
                if (!res.next()) {
                    break;
                }
                Integer i = res.getInt(1);
            } catch (SQLException e) {
                System.out.println(e.toString());
            }
        }
    }
    
    public List<String> getToLongIDs(Connection conn )throws SQLException{
        List<String> badIDs = new ArrayList<String>();
        String sql = "select ICMS_ORDER_ID from ntck.feature_instance where len(ICMS_ORDER_ID) > 10";
        PreparedStatement stmt = conn.prepareStatement(sql);
        ResultSet res = stmt.executeQuery();  
        while(res.next()){
            badIDs.add(res.getString(1));
        }
        return badIDs;
    }
    
    
    private void removeToLongIDs(Connection conn, List<String> toLongIDs) throws SQLException {
        for (String toLongID : toLongIDs) {
            String sql = "update ntck.feature_instance set ICMS_ORDER_ID = ? where ICMS_ORDER_ID = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, "");
            stmt.setString(2, toLongID);
            stmt.execute();
        }
    }

}
