package tikape.runko.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import tikape.runko.domain.*;

public class VastausDao extends AbstractNamedObjectDao<Vastaus> {

    private Dao<Kysymys, Integer> kysymysDao;

    public VastausDao(Database database, Dao<Kysymys, Integer> kysymysDao) {
        super(database, "vastaus");
        this.kysymysDao = kysymysDao;
    }

    public List<Vastaus> findByKysymys(Kysymys kysymys) throws SQLException {
        return findbyInt("kysymys_id", kysymys.getId());
    }

    @Override
    public Vastaus createFromRow(ResultSet rs) throws SQLException {
        Integer id = rs.getInt("id");
        Kysymys kysymys = kysymysDao.findOne(rs.getInt("kysymys_id"));
        String teksti = rs.getString("vastaus_teksti");
        Boolean oikein = rs.getBoolean("oikein");
        return new Vastaus(id, kysymys, teksti, oikein);
    }

    @Override
    protected Vastaus save(Vastaus vastaus) throws SQLException {

        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO Vastaus"
                + " (kysymys_id, vastaus_teksti, oikein)"
                + " VALUES (?, ?, ?)");
        stmt.setInt(1, vastaus.getKysymys().getId());
        stmt.setString(2, vastaus.getVastausTeksti());
        stmt.setBoolean(3, vastaus.getOikein());

        stmt.executeUpdate();
        stmt.close();

        stmt = conn.prepareStatement("SELECT * FROM Vastaus"
                + " WHERE kysymys_id = ? AND vastaus_teksti = ?");
        stmt.setInt(1, vastaus.getKysymys().getId());
        stmt.setString(2, vastaus.getVastausTeksti());

        ResultSet rs = stmt.executeQuery();
        rs.next(); 
        Vastaus v = createFromRow(rs);

        stmt.close();
        rs.close();
        conn.close();

        return v;
    }

    @Override
    protected Vastaus update(Vastaus object) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void deleteByKysymys(Kysymys k) throws SQLException {
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM " + tableName + " WHERE kysymys_id = ?");
        stmt.setInt(1, k.getId());
        stmt.executeUpdate();
        stmt.close();
        conn.close();
    }

}
