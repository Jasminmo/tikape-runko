package tikape.runko.database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Database {

    private String databaseAddress;

    public Database(String databaseAddress) throws ClassNotFoundException {
        this.databaseAddress = databaseAddress;
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(databaseAddress);
    }

    public void init() {
        List<String> lauseet = sqliteLauseet();

        // "try with resources" sulkee resurssin automaattisesti lopuksi
        try (Connection conn = getConnection()) {
            Statement st = conn.createStatement();

            // suoritetaan komennot
            for (String lause : lauseet) {
                System.out.println("Running command >> " + lause);
                st.executeUpdate(lause);
            }

        } catch (Throwable t) {
            // jos tietokantataulu on jo olemassa, ei komentoja suoriteta
            System.out.println("Error >> " + t.getMessage());
        }
    }

    private List<String> sqliteLauseet() {
        ArrayList<String> lista = new ArrayList<>();

        // tietokantataulujen luomiseen tarvittavat komennot suoritusjärjestyksessä
        lista.add("DROP TABLE IF EXISTS Kysymys;");

        lista.add("CREATE TABLE Kysymys ("
                + "    id             sequence PRIMARY KEY,"
                + "    kurssi         varchar(128),"
                + "    aihe           varchar(128),"
                + "    kysymys_teksti varchar(1024)"
                + ");");

        lista.add("DROP TABLE IF EXISTS Vastaus;");

        lista.add("CREATE TABLE Vastaus ("
                + "    id             sequence PRIMARY KEY,"
                + "    kysymys_id     integer,"
                + "    vastaus_teksti varchar(1024),"
                + "    oikein         boolean,"
                + "    FOREING KEY kysymys_id REFERENCES Kysymys (id)"
                + ");");

        for (int i = 0; i < 10; i++) {
            lista.add("INSERT INTO Kysymys (id, kurssi, aihe, kysymys_teksti) VALUES (" + i +", 'kurssi_" + i + "', 'aihe_" + i + "', 'kysymysteksti_" + i + "');");
            for (int j = 0; j < 3; j++) {
                lista.add("INSERT INTO Vastaus (kysymys_id, vastaus_teksti, oikein) VALUES (" + i + ", 'vastaus_" + j + "', 0);");
            }
        }

        return lista;
    }
}
