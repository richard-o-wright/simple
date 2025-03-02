package net.wrightnz.simple.persistence.rdb;

import java.sql.ResultSet;
import java.sql.SQLException;
import net.wrightnz.simple.persistence.Key;
import net.wrightnz.simple.persistence.Persistable;

/**
 *
 * @author Richard Wright
 */
public abstract class RdbPersistable implements Persistable {

    private Key key;

    public RdbPersistable() {
    }

    public RdbPersistable(ResultSet res) throws SQLException {
        this.key = new Key(res.getString("id"));
    }

    @Override
    public Key getKey() {
        return key;
    }

    @Override
    public void setKey(Key key) {
        this.key = key;
    }

    public abstract String getColumns();

    public abstract String getJoinSet();

}
