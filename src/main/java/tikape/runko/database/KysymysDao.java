package tikape.runko.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import tikape.runko.domain.Kysymys;

public class KysymysDao implements Dao<Kysymys, Integer> {

    private Database database;

    public KysymysDao(Database database) {
        this.database = database;
    }

    @Override
    public Kysymys findOne(Integer key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Kysymys WHERE id = ?");
        stmt.setObject(1, key);

        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            return null;
        }

        Integer id = rs.getInt("id");
        String kurssi = rs.getString("kurssi");
        String aihe = rs.getString("aihe");
        String teksti = rs.getString("kysymys_teksti");

        Kysymys kysymys = new Kysymys(id, kurssi, aihe, teksti);

        rs.close();
        stmt.close();
        connection.close();

        return kysymys;
    }

    @Override
    public List<Kysymys> findAll() throws SQLException {

        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Kysymys");

        ResultSet rs = stmt.executeQuery();
        List<Kysymys> kysymykset = new ArrayList<>();
        while (rs.next()) {
            Integer id = rs.getInt("id");
            String kurssi = rs.getString("kurssi");
            String aihe = rs.getString("aihe");
            String teksti = rs.getString("kysymys_teksti");

            Kysymys kysymys = new Kysymys(id, kurssi, aihe, teksti);
            kysymykset.add(kysymys);
        }

        rs.close();
        stmt.close();
        connection.close();

        return kysymykset;
    }

    @Override
    public void delete(Integer key) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
