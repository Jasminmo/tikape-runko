package tikape.runko.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import tikape.runko.domain.AbstractNamedObject;

public abstract class AbstractNamedObjectDao<T extends AbstractNamedObject> implements Dao<T, Integer> {

    protected Database database;
    protected String tableName;

    public AbstractNamedObjectDao(Database database, String tableName) {
        this.database = database;
        this.tableName = tableName;
    }

    public abstract T createFromRow(ResultSet resultSet) throws SQLException;

    @Override
    public List<T> findAll() throws SQLException {
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Kysymys");

        ResultSet rs = stmt.executeQuery();
        List<T> objects = new ArrayList<>();
        while (rs.next()) {
            objects.add(createFromRow(rs));
        }

        rs.close();
        stmt.close();
        conn.close();

        return objects;
    }

    @Override
    public T findOne(Integer key) throws SQLException {
        return findbyInt("id", key).get(0);
    }

    @Override
    public List<T> findbyInt(String columnName, Integer value) throws SQLException {
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM " + tableName + " WHERE " + columnName + " = ?");
        stmt.setObject(1, value);

        ResultSet rs = stmt.executeQuery();
        List<T> objects = new ArrayList<>();
        while (rs.next()) {
            objects.add(createFromRow(rs));
        }

        rs.close();
        stmt.close();
        conn.close();

        return objects;
    }

    @Override
    public void delete(Integer key) throws SQLException {
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM " + tableName + " WHERE id = ?");
        stmt.setInt(1, key);
        stmt.executeUpdate();
        stmt.close();
        conn.close();
    }

    @Override
    public T saveOrUpdate(T object) throws SQLException {
        if (object.getId() == null) {
            return save(object);
        } else {
            return update(object);
        }
    }

    protected abstract T update(T object) throws SQLException;

    protected abstract T save(T object) throws SQLException;

}
