package net.wrightnz.database;

import java.sql.SQLException;

/**
 *
 * @author Richard Wright
 */
public class DropTables {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SQLException{
        new QuickTable("CEM_DATA_WITH_DATES").dropTable();
        new QuickTable("NM_DATA_WITH_DATES").dropTable();
    }


}
