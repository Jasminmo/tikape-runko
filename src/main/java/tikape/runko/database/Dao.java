package tikape.runko.database;

import java.sql.*;
import java.util.*;

public interface Dao<T, K> {

    T findOne(K key) throws SQLException;

    List<T> findAll() throws SQLException;

    public List<T> findbyInt(String columnName, Integer key) throws SQLException;

    public T saveOrUpdate(T object) throws SQLException;

    void delete(K key) throws SQLException;
}
