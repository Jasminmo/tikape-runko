package tikape.runko.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import tikape.runko.domain.*;

public class VastausDao implements Dao<Vastaus, Integer> {

    private Database database;
    private Dao<Kysymys, Integer> kysymysDao;

    public VastausDao(Database database, Dao<Kysymys, Integer> kysymysDao) {
        this.database = database;
        this.kysymysDao = kysymysDao;
    }

    @Override
    public Vastaus findOne(Integer key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Vastaus WHERE id = ?");
        stmt.setObject(1, key);

        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            return null;
        }

        Integer id = rs.getInt("id");
        Kysymys kysymys = kysymysDao.findOne(rs.getInt("kysymys_id"));
        String teksti = rs.getString("vastaus_teksti");

        Vastaus vastaus = new Vastaus(id, kysymys, teksti);

        rs.close();
        stmt.close();
        connection.close();

        return vastaus;
    }

    @Override
    public List<Vastaus> findAll() throws SQLException {

        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Vastaus");

        ResultSet rs = stmt.executeQuery();
        List<Vastaus> vastaukset = new ArrayList<>();
        while (rs.next()) {
            Integer id = rs.getInt("id");
            Kysymys kysymys = kysymysDao.findOne(rs.getInt("kysymys_id"));
            String teksti = rs.getString("vastaus_teksti");
            Vastaus vastaus = new Vastaus(id, kysymys, teksti);

            vastaukset.add(vastaus);
        }

        rs.close();
        stmt.close();
        connection.close();

        return vastaukset;
    }

    public List<Vastaus> findByKysymys(Kysymys kysymys) throws SQLException {

        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Vastaus where kysymys_id is ?");
        stmt.setObject(1, kysymys.getId());

        ResultSet rs = stmt.executeQuery();
        List<Vastaus> vastaukset = new ArrayList<>();
        while (rs.next()) {
            Integer id = rs.getInt("id");
            String teksti = rs.getString("vastaus_teksti");
            Vastaus vastaus = new Vastaus(id, kysymys, teksti);

            vastaukset.add(vastaus);
        }

        rs.close();
        stmt.close();
        connection.close();

        return vastaukset;
    }

    @Override
    public void delete(Integer key) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
