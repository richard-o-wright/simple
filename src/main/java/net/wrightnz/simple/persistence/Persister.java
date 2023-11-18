package net.wrightnz.simple.persistence;

import java.sql.SQLException;

/**
 *
 * @author Richard Wright
 * @param <T>
 */
public interface Persister<T extends Persistable> {

    T save(T thing) throws SQLException;

    void delete(T thing) throws SQLException;

}
