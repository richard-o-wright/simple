/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.wrightnz.database;

import java.util.ArrayList;
import java.util.List;
import net.wrightnz.data.ValueObjectBase;

/**
 *
 * @author t466225
 */
public class ResultSet extends ValueObjectBase{
    private String query = "";
    private List<Row> results = new ArrayList<Row>();

    /**
     * @return the query
     */
    public String getQuery() {
        return query;
    }

    /**
     * @param query the query to set
     */
    public void setQuery(String query) {
        this.query = query;
    }

    /**
     * @return the results
     */
    public List<Row> getResults() {
        return results;
    }

    /**
     * @param results the results to set
     */
    public void setResults(List<Row> results) {
        this.results = results;
    }

    public void addRow(Row row) {
        this.results.add(row);
    }

}
