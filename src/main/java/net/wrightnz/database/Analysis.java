package net.wrightnz.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Richard Wright
 */
public class Analysis {

    String sql = "select "
        + "    cem.EVENT_CREATE_DATE, "
        + "    nm.CREATE_DATE, "
        + "    nm.EFFECTIVE_DATE "
        + " from "
        + "    CEM_DATA_WITH_DATES cem, "
        + "    NM_DATA_WITH_DATES nm "
        + " where "
        + "    cem.EXTERNAL_REF = nm.NOTIFICATION_KEY and "
        + "    cem.ACTION_TYPE <> 'B2B' and "
        + "    (nm.EFFECTIVE_DATE <> cem.EVENT_CREATE_DATE or nm.CREATE_DATE <> cem.EVENT_CREATE_DATE)";


    static DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");

    static long TEN_SECONDS = 1000 * 10;
    static long THIRTY_SECONDS = 1000 * 30;
    static long ONE_MINUTES = 1000 * 60;
    static long TWO_MINUTES = 1000 * 60 * 2;
    
    int totalMessages;
    int messagesThatTookLongerThen1Minutes;
    int messagesThatTookLongerThen2Minutes;
    int messagesThatTookLongerThen30Seconds;
    int messagesThatTookLongerThen10Seconds;

    public Analysis(){
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)throws SQLException, ParseException{
        new Analysis().analyse();
        // Date date = dateFormat.parse("02-Aug-2010 13:25:10");
        // System.out.println(date);
    }

    public void analyse()throws SQLException, ParseException{
        Connection conn = new QuickTable("").getConnection();
        try{
            java.sql.ResultSet results = executeSelect(conn, sql);
            while(results.next()){
                String cemCreateString = results.getString(1);
                String nmCreateString = results.getString(2);
                String nmEffectiveString = results.getString(3);
                compare(cemCreateString, nmCreateString, nmEffectiveString);
            }
        }finally{
            conn.close();
        }
        /*
        compare("02-Aug-2020 09:30:10", "02-Aug-2020 09:31:10", "02-Aug-2020 12:10:30");
        compare("02-Aug-2020 12:10:10", "02-Aug-2020 12:10:15", "02-Aug-2020 12:10:30");
        compare("02-Aug-2020 16:10:10", "02-Aug-2020 16:10:12", "02-Aug-2020 16:10:30");
        */
        System.out.println("Total messages cheched: " + totalMessages);
        System.out.println("Messages That Took Longer Then 2 Minutes: " + messagesThatTookLongerThen2Minutes);
        System.out.println("Messages That Took Longer Then 1 Minutes: " + messagesThatTookLongerThen1Minutes);
        System.out.println("Messages That Took Longer Then 30 Seconds: " + messagesThatTookLongerThen30Seconds);
        System.out.println("Messages That Took Longer Then 10 Seconds: " + messagesThatTookLongerThen10Seconds);
    }





    private void compare(String cemCreateString, String nmCreateString, String nmEffectiveString)throws ParseException{
        Date cemCreate = dateFormat.parse(cemCreateString);
        Date nmCreate = dateFormat.parse(nmCreateString);
        Date nmEffective = dateFormat.parse(nmEffectiveString);
        
        totalMessages++;
        if(nmCreate.getTime() - cemCreate.getTime() > TWO_MINUTES || nmEffective.getTime() - cemCreate.getTime() > TWO_MINUTES){
            messagesThatTookLongerThen2Minutes++;
        }
        else if(nmCreate.getTime() - cemCreate.getTime() > ONE_MINUTES || nmEffective.getTime() - cemCreate.getTime() > ONE_MINUTES){
            messagesThatTookLongerThen1Minutes++;
        }
        else if(nmCreate.getTime() - cemCreate.getTime() > THIRTY_SECONDS || nmEffective.getTime() - cemCreate.getTime() > THIRTY_SECONDS){
            messagesThatTookLongerThen30Seconds++;
        }
        else if(nmCreate.getTime() - cemCreate.getTime() > TEN_SECONDS || nmEffective.getTime() - cemCreate.getTime() > TEN_SECONDS){
            messagesThatTookLongerThen10Seconds++;
        }
    }

    private java.sql.ResultSet executeSelect(Connection conn, String query) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement(query);
        return stmt.executeQuery();
    }

}
