package net.wrightnz.database;


import com.opencsv.CSVReader;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author Richard Wright
 */
public class QuickTableLoader {

    private static final char QUOTE_CHAR = '\"';
    private static final char SEPARATOR = '|';

    public QuickTableLoader(){
    }

    public static void main(String[] args)throws IOException, SQLException  {
        QuickTableLoader loader = new QuickTableLoader();
        File cem = new File("D:\\Cases07-09-2010.txt");
        loader.load("VANTIVE_CASE_CALL", cem);
        // File nm = new File("D:\\CEM\\nm.csv");
        // loader.load("NM_DATA_WITH_DATES", nm);
    }

    private void load(String tableName, File csvName) throws IOException, SQLException {
        String method = "load(" + tableName + ", " + csvName + ")";
        System.out.println("Start: " + method);
        QuickTable table = new QuickTable(tableName);
        CSVReader reader = new CSVReader(new FileReader(csvName), SEPARATOR, QUOTE_CHAR);
        boolean hasReadFirstLine = false;
        String [] nextLine = new String[10];
        int lineNumber = 0;

        Connection conn = table.getConnection();
        try {
            while ((nextLine = reader.readNext()) != null) {
                lineNumber++;
                if (!hasReadFirstLine) {
                    table.createTable(conn, nextLine);
                } else {
                    table.insertRow(conn, nextLine);
                }
                hasReadFirstLine = true;
            }
            System.out.println("End: " + method);
        } catch(SQLException e){
            StringBuilder sb = new StringBuilder();
            sb.append("[");
            for(int i = 0; i < nextLine.length; i++){
                sb.append(nextLine[i]);
                sb.append(", ");
            }
            sb.append("]");
            System.err.println("Error loading line: " + lineNumber + " : " + sb.toString());
            throw e;
        } finally {
            conn.close();
        }
    }

}
