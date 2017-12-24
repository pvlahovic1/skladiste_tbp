package hr.foi.database;

import hr.foi.model.Mjera;
import hr.foi.model.PoslovniPartner;
import hr.foi.model.TipDokumenta;
import hr.foi.model.Zaposlenik;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DatabaseWorker {

    private static String HOST = "localhost";
    private static String PORT = "5432";
    private static String DATABASE = "tbp_skladiste";
    private static String USERNAME = "postgres";
    private static String PASSSWORD = "postgres";
    private static DatabaseWorker instance = null;

    private DatabaseWorker() {
    }

    public static DatabaseWorker getInstance() {
        if(instance == null) {
            instance = new DatabaseWorker();
        }

        return instance;
    }

    private Connection openConnectionToDatabase() throws SQLException {
        return DriverManager.getConnection("jdbc:postgresql://" + HOST + ":" + PORT + "/" + DATABASE, USERNAME, PASSSWORD);
    }

    private void closeConnectionToDatabase(Connection connection, List<Statement> statements) {
        try {
            for (Statement statement : statements) {
                statement.close();
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Zaposlenik> getAllZaposlenik() throws SQLException {
            List<Zaposlenik> zaposlenici = new ArrayList<>();

            Connection connection = openConnectionToDatabase();
            Statement statement = connection.createStatement();

            ResultSet rs = statement.executeQuery("SELECT * FROM zaposlenik;");

            while ( rs.next() ) {
                int id = rs.getInt("id");
                String oib = rs.getString("oib");
                String ime = rs.getString("ime");
                String prezime = rs.getString("prezime");
                String email = rs.getString("email");
                String adresa = rs.getString("adresa");
                String telefon = rs.getString("telefon");
                Timestamp vrijemeUnosa = rs.getTimestamp("vrijeme_unosa");

                zaposlenici.add(new Zaposlenik(id, oib, ime, prezime, email, adresa, telefon, vrijemeUnosa.toLocalDateTime()));

            }

            closeConnectionToDatabase(connection, Collections.singletonList(statement));

            return zaposlenici;
        }

    public void deleteZaposlenik(Zaposlenik zaposlenik) throws SQLException {
        Connection connection = openConnectionToDatabase();

        String deleteSQL = "DELETE FROM zaposlenik WHERE id = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(deleteSQL);
        preparedStatement.setInt(1, zaposlenik.getId());

        preparedStatement.executeUpdate();

        closeConnectionToDatabase(connection, Collections.singletonList(preparedStatement));
    }

    public void saveNewZaposlenik(Zaposlenik zaposlenik) throws SQLException {
        Connection connection = openConnectionToDatabase();

        String insertSQL = "INSERT INTO zaposlenik (oib, ime, prezime, email, adresa, telefon, vrijeme_unosa) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?);";

        PreparedStatement preparedStatement = connection.prepareStatement(insertSQL);
        preparedStatement.setString(1, zaposlenik.getOib());
        preparedStatement.setString(2, zaposlenik.getIme());
        preparedStatement.setString(3, zaposlenik.getPrezime());
        preparedStatement.setString(4, zaposlenik.getEmai());
        preparedStatement.setString(5, zaposlenik.getAdresa());
        preparedStatement.setString(6, zaposlenik.getTelefon());
        preparedStatement.setTimestamp(7, Timestamp.valueOf(zaposlenik.getVrijemeUnosa()));

        preparedStatement.executeUpdate();


        closeConnectionToDatabase(connection, Collections.singletonList(preparedStatement));
    }

    public void saveExistingZaposelnik(Zaposlenik zaposlenik) throws SQLException {
        Connection connection = openConnectionToDatabase();

        String updateSQL = "UPDATE zaposlenik SET ime = ?, prezime = ?, email = ?, adresa = ?, telefon = ?, vrijeme_unosa = ? " +
                " WHERE oib = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(updateSQL);
        preparedStatement.setString(1, zaposlenik.getIme());
        preparedStatement.setString(2, zaposlenik.getPrezime());
        preparedStatement.setString(3, zaposlenik.getEmai());
        preparedStatement.setString(4, zaposlenik.getAdresa());
        preparedStatement.setString(5, zaposlenik.getTelefon());
        preparedStatement.setTimestamp(6, Timestamp.valueOf(zaposlenik.getVrijemeUnosa()));
        preparedStatement.setString(7, zaposlenik.getOib());

        preparedStatement.executeUpdate();

        closeConnectionToDatabase(connection, Collections.singletonList(preparedStatement));
    }

    public List<PoslovniPartner> getAllPoslovniPartneri() throws SQLException {
        List<PoslovniPartner> poslovniPartneri = new ArrayList<>();

        Connection connection = openConnectionToDatabase();
        Statement statement = connection.createStatement();

        ResultSet rs = statement.executeQuery("SELECT * FROM poslovni_partner;");

        while (rs.next()) {
            int id = rs.getInt("id");
            String oib = rs.getString("oib");
            String nazivPravneOsobe = rs.getString("naziv_pravne_osobe");
            String email = rs.getString("email");
            String adresa = rs.getString("adresa");
            String telefon = rs.getString("telefon");
            Timestamp vrijemeUnosa = rs.getTimestamp("vrijeme_unosa");

            poslovniPartneri.add(new PoslovniPartner(id, oib, nazivPravneOsobe, telefon, email, adresa, vrijemeUnosa.toLocalDateTime()));

        }

        closeConnectionToDatabase(connection, Collections.singletonList(statement));

        return poslovniPartneri;
    }

    public void deletePoslovniPartner(PoslovniPartner poslovniPartner) throws SQLException {
        Connection connection = openConnectionToDatabase();

        String deleteSQL = "DELETE FROM poslovni_partner WHERE id = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(deleteSQL);
        preparedStatement.setInt(1, poslovniPartner.getId());

        preparedStatement.executeUpdate();

        closeConnectionToDatabase(connection, Collections.singletonList(preparedStatement));
    }

    public void saveNewPoslovniPartner(PoslovniPartner poslovniPartner) throws SQLException {
        Connection connection = openConnectionToDatabase();

        String insertSQL = "INSERT INTO poslovni_partner (oib, naziv_pravne_osobe, telefon, email, adresa, vrijeme_unosa) " +
                "VALUES (?, ?, ?, ?, ?, ?);";

        PreparedStatement preparedStatement = connection.prepareStatement(insertSQL);
        preparedStatement.setString(1, poslovniPartner.getOib());
        preparedStatement.setString(2, poslovniPartner.getNazivPravneOsobe());
        preparedStatement.setString(3, poslovniPartner.getTelefon());
        preparedStatement.setString(4, poslovniPartner.getEmail());
        preparedStatement.setString(5, poslovniPartner.getAdresa());
        preparedStatement.setTimestamp(6, Timestamp.valueOf(poslovniPartner.getVrijemeUnosa()));

        preparedStatement.executeUpdate();

        closeConnectionToDatabase(connection, Collections.singletonList(preparedStatement));
    }

    public void saveExistingPoslovniPartner(PoslovniPartner poslovniPartner) throws SQLException {
        Connection connection = openConnectionToDatabase();

        String updateSQL = "UPDATE poslovni_partner SET naziv_pravne_osobe = ?, telefon = ?, email = ?, adresa = ?, vrijeme_unosa = ? " +
                " WHERE oib = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(updateSQL);
        preparedStatement.setString(1, poslovniPartner.getNazivPravneOsobe());
        preparedStatement.setString(2, poslovniPartner.getTelefon());
        preparedStatement.setString(3, poslovniPartner.getEmail());
        preparedStatement.setString(4, poslovniPartner.getAdresa());
        preparedStatement.setTimestamp(5, Timestamp.valueOf(poslovniPartner.getVrijemeUnosa()));
        preparedStatement.setString(6, poslovniPartner.getOib());

        preparedStatement.executeUpdate();

        closeConnectionToDatabase(connection, Collections.singletonList(preparedStatement));
    }

    public void deleteMjera(Mjera mjera) throws SQLException {
        Connection connection = openConnectionToDatabase();

        String deleteSQL = "DELETE FROM mjera WHERE id = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(deleteSQL);
        preparedStatement.setInt(1, mjera.getId());

        preparedStatement.executeUpdate();

        closeConnectionToDatabase(connection, Collections.singletonList(preparedStatement));
    }

    public void saveNewMjera(Mjera mjera) throws SQLException {
        Connection connection = openConnectionToDatabase();

        String insertSQL = "INSERT INTO mjera(naziv, skracenica) VALUES (?, ?);";

        PreparedStatement preparedStatement = connection.prepareStatement(insertSQL);
        preparedStatement.setString(1, mjera.getNaziv());
        preparedStatement.setString(2, mjera.getSkracenica());

        preparedStatement.executeUpdate();

        closeConnectionToDatabase(connection, Collections.singletonList(preparedStatement));
    }

    public void saveExistingMjera(Mjera mjera) throws SQLException {
        Connection connection = openConnectionToDatabase();

        String updateSQL = "UPDATE mjera SET naziv = ?, skracenica = ? WHERE id = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(updateSQL);
        preparedStatement.setString(1, mjera.getNaziv());
        preparedStatement.setString(2, mjera.getSkracenica());
        preparedStatement.setInt(3, mjera.getId());

        preparedStatement.executeUpdate();

        closeConnectionToDatabase(connection, Collections.singletonList(preparedStatement));
    }

    public List<Mjera> getAllMjera() throws SQLException {
        List<Mjera> mjere = new ArrayList<>();

        Connection connection = openConnectionToDatabase();
        Statement statement = connection.createStatement();

        ResultSet rs = statement.executeQuery("SELECT * FROM mjera;");

        while ( rs.next() ) {
            int id = rs.getInt("id");
            String naziv = rs.getString("naziv");
            String skracenica = rs.getString("skracenica");

            mjere.add(new Mjera(id, naziv, skracenica));
        }

        closeConnectionToDatabase(connection, Collections.singletonList(statement));

        return mjere;
    }

    public List<TipDokumenta> getAllTipDokumenta() throws SQLException {
        List<TipDokumenta> tipoviDokumenta = new ArrayList<>();

        Connection connection = openConnectionToDatabase();
        Statement statement = connection.createStatement();

        ResultSet rs = statement.executeQuery("SELECT * FROM tip_dokumenta;");

        while ( rs.next() ) {
            int id = rs.getInt("id");
            String naziv = rs.getString("naziv");
            String akcija = rs.getString("akcija");

            tipoviDokumenta.add(new TipDokumenta(id, naziv, akcija));
        }

        closeConnectionToDatabase(connection, Collections.singletonList(statement));

        return tipoviDokumenta;
    }

    public void deleteTipDokumenta(TipDokumenta tipDokumenta) throws SQLException {
        Connection connection = openConnectionToDatabase();

        String deleteSQL = "DELETE FROM tip_dokumenta WHERE id = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(deleteSQL);
        preparedStatement.setInt(1, tipDokumenta.getId());

        preparedStatement.executeUpdate();

        closeConnectionToDatabase(connection, Collections.singletonList(preparedStatement));
    }

    public void saveNewTipDokumenta(TipDokumenta tipDokumenta) throws SQLException {
        Connection connection = openConnectionToDatabase();

        String insertSQL = "INSERT INTO tip_dokumenta(naziv, akcija) VALUES (?, ?);";

        PreparedStatement preparedStatement = connection.prepareStatement(insertSQL);
        preparedStatement.setString(1, tipDokumenta.getNaziv());
        preparedStatement.setString(2, tipDokumenta.getAkcija());

        preparedStatement.executeUpdate();

        closeConnectionToDatabase(connection, Collections.singletonList(preparedStatement));
    }

    public void saveExistingTipDokumenta(TipDokumenta tipDokumenta) throws SQLException {
        Connection connection = openConnectionToDatabase();

        String updateSQL = "UPDATE tip_dokumenta SET naziv = ?, akcija = ? WHERE id = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(updateSQL);
        preparedStatement.setString(1, tipDokumenta.getNaziv());
        preparedStatement.setString(2, tipDokumenta.getAkcija());
        preparedStatement.setInt(3, tipDokumenta.getId());

        preparedStatement.executeUpdate();

        closeConnectionToDatabase(connection, Collections.singletonList(preparedStatement));
    }

}
