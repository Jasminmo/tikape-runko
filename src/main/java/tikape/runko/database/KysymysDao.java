package tikape.runko.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import tikape.runko.domain.Kysymys;

public class KysymysDao extends AbstractNamedObjectDao<Kysymys> {

    public KysymysDao(Database database) {
        super(database, "kysymys");
    }

    @Override
    public Kysymys createFromRow(ResultSet rs) throws SQLException {
        Integer id = rs.getInt("id");
        String kurssi = rs.getString("kurssi");
        String aihe = rs.getString("aihe");
        String teksti = rs.getString("kysymys_teksti");
        return new Kysymys(id, kurssi, aihe, teksti);
    }

    @Override
    protected Kysymys save(Kysymys kysymys) throws SQLException {

        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO Kysymys"
                + " (kurssi, aihe, kysymys_teksti)"
                + " VALUES (?, ?, ?)");
        stmt.setString(1, kysymys.getKurssi());
        stmt.setString(2, kysymys.getAihe());
        stmt.setString(3, kysymys.getKysymysTeksti());

        stmt.executeUpdate();
        stmt.close();

        stmt = conn.prepareStatement("SELECT * FROM Kysymys"
                + " WHERE kurssi = ? AND aihe = ? AND kysymys_teksti = ?");
        stmt.setString(1, kysymys.getKurssi());
        stmt.setString(2, kysymys.getAihe());
        stmt.setString(3, kysymys.getKysymysTeksti());

        ResultSet rs = stmt.executeQuery();
        rs.next(); 
        Kysymys k = createFromRow(rs);

        stmt.close();
        rs.close();
        conn.close();

        return k;
    }

    @Override
    protected Kysymys update(Kysymys object) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
