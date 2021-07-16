package net.wrightnz.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author Richard Wright
 *
 * Quick and dirty class for create and load an Oracle database table for ad-hoc data analysis.
 * The create table has do constraints (not even a primary key) and all columns are or type
 * varchar2(255).
 *
 */
public class QuickTable {

    public static final String DB_URL = "jdbc:oracle:thin:@localhost:1521:XE";
    public static final String DB_USER = "xicms";
    public static final String DB_PASSWORD = "xicmspass";

    private String tableName;

    public QuickTable(String tableName)throws SQLException{
        DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
        this.tableName = tableName;
    }

    public void createTable(Connection conn, String[] columns)throws SQLException{
        String sql = generateCreateStatement(columns);
        System.out.println(sql);
        executeStatement(conn, sql);
    }

    public void insertRow(String[] values)throws SQLException{
        Connection conn = getConnection();
        try{
            insertRow(conn, values);
        }
        finally{
            conn.close();
        }
    }

    public void insertRow(Connection conn, String[] values)throws SQLException{
        String sql = this.generateInsertSQL(values);
        PreparedStatement stmt = generateInsertStatement(conn, sql, values);
        try {
            stmt.executeUpdate();
        }
        catch(SQLException e){
            System.err.println(sql);
            throw e;
        } finally {
            stmt.close();
        }
    }

    public void dropTable()throws SQLException{
        Connection conn = getConnection();
        try{
            dropTable(conn);
        }
        finally{
            conn.close();
        }
    }

    public void dropTable(Connection conn)throws SQLException{
        String sql = "drop table " + tableName;
        executeStatement(conn, sql);
    }

    public static void main(String[] args)throws SQLException{
        String[] columms = {"ID", "NAME", "DESCRIPTION"};
        String[] values1 = {"1", "Test Name", null};
        String[] values2 = {"2", "Test, 'Name' a more difficult <'String'> \"like\" this", null};
        String[] values3 = {"3", null, "Blah Blah"};
        QuickTable table = new QuickTable("TEST_QUICK_TABLE");
        Connection conn = table.getConnection();
        System.out.println(table.generateCreateStatement(columms));
        table.createTable(conn, columms);
        table.insertRow(values1);
        table.insertRow(values2);
        table.insertRow(values3);
        table.dropTable(conn);
        conn.close();
    }

    private String generateCreateStatement(String[] columns){
        StringBuilder sql = new StringBuilder();
        sql.append("create table ");
        sql.append(tableName);
        sql.append(" (\n");
        for(int i = 0; i < columns.length; i++){
            sql.append("    ");
            sql.append(columns[i]);
            //if(columns[i].contains("DATE") || columns[i].contains("date")){
            //   sql.append(" timestamp");
            //}
            // else{
            sql.append(" varchar2(4000)");
            //}
            if(i < columns.length - 1){
                sql.append(",\n");
            }
             else{
                   sql.append("\n)\n");
             }
        }
        return sql.toString();
    }


    private String generateInsertSQL(String[] values){
        StringBuilder sql = new StringBuilder();
        sql.append("insert into ");
        sql.append(tableName);
        sql.append(" values (");
        for (int i = 0; i < values.length; i++) {
            sql.append("? ");
            if (i < values.length - 1) {
                sql.append(",");
            } else {
                sql.append(")\n");
            }
        }
        return sql.toString();
    }

    private PreparedStatement generateInsertStatement(Connection conn, String sql, String[] values)throws SQLException {
        PreparedStatement stmt = conn.prepareStatement(sql);
        for (int i = 0; i < values.length; i++) {
            String value = values[i];
            if(value != null && value.length() > 4000){
                value = value.substring(0, 3999);
            }
            stmt.setString(i + 1, value);
        }
        return stmt;
    }

    public Connection getConnection()throws SQLException{
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }

    private boolean executeStatement(Connection conn, String sql) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement(sql);
        try{
            return stmt.execute();
        }
        finally{
            stmt.close();
        }
    }
    
}
