package hr.foi.database;

import hr.foi.model.*;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class DatabaseWorker {

    private static String HOST = "localhost";
    private static String PORT = "5432";
    private static String DATABASE = "skladiste_pavao_vlahovic";
    private static String USERNAME = "pvlahovic";
    private static String PASSSWORD = "pvlahovic";
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

    public List<Artikl> getAllArtikl() throws SQLException {
        List<Artikl> artikli = new ArrayList<>();

        String selectSQL = "SELECT artikl.*, mjera.naziv as mjeraNaziv," +
                " mjera.skracenica AS mjeraSkracenica FROM artikl JOIN mjera ON artikl.id_mjere = mjera.id;";

        Connection connection = openConnectionToDatabase();
        Statement statement = connection.createStatement();

        ResultSet rs = statement.executeQuery(selectSQL);

        while ( rs.next() ) {
            int id = rs.getInt("id");
            String naziv = rs.getString("naziv");
            int kolicinaNaSkladistu = rs.getInt("kolicina_na_skladistu");
            int minimalneZalihe = rs.getInt("minimalne_zalihe");
            double jedinicnaCijena = rs.getDouble("jedinicna_cijena");
            int godisnjaPotraznja = rs.getInt("godisnja_potraznja");
            double troskoviSkladistenja = rs.getDouble("troskovi_skladistenja");
            double troskoviNabave = rs.getDouble("troskovi_nabave");
            int idMjere = rs.getInt("id_mjere");
            String mjeraNaziv = rs.getString("mjeraNaziv");
            String mjeraSkracenica = rs.getString("mjeraSkracenica");

            Mjera mjera = new Mjera(idMjere, mjeraNaziv, mjeraSkracenica);

            artikli.add(new Artikl(id, naziv, kolicinaNaSkladistu, minimalneZalihe, jedinicnaCijena, godisnjaPotraznja, troskoviSkladistenja, troskoviNabave, idMjere, mjera));
        }

        closeConnectionToDatabase(connection, Collections.singletonList(statement));

        return artikli;
    }

    public void deleteArtikl(Artikl artikl) throws SQLException {
        Connection connection = openConnectionToDatabase();

        String deleteSQL = "DELETE FROM artikl WHERE id = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(deleteSQL);
        preparedStatement.setInt(1, artikl.getId());

        preparedStatement.executeUpdate();

        closeConnectionToDatabase(connection, Collections.singletonList(preparedStatement));
    }

    public void saveNewArtikl(Artikl artikl) throws SQLException {
        Connection connection = openConnectionToDatabase();

        String insertSQL = "INSERT INTO artikl(naziv, kolicina_na_skladistu, minimalne_zalihe, jedinicna_cijena, " +
                "godisnja_potraznja, troskovi_skladistenja, troskovi_nabave, id_mjere) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?);";

        PreparedStatement preparedStatement = connection.prepareStatement(insertSQL);
        preparedStatement.setString(1, artikl.getNaziv());
        preparedStatement.setInt(2, artikl.getKolicinaNaSkladistu());
        preparedStatement.setInt(3, artikl.getMinimalneZalihe());
        preparedStatement.setDouble(4, artikl.getJedinicnaCijena());
        preparedStatement.setInt(5, artikl.getGodisnjaPotraznja());
        preparedStatement.setDouble(6, artikl.getTroskoviSkladistenja());
        preparedStatement.setDouble(7, artikl.getTroskoviNabave());
        preparedStatement.setInt(8, artikl.getIdMjere());

        preparedStatement.executeUpdate();

        closeConnectionToDatabase(connection, Collections.singletonList(preparedStatement));
    }

    public void saveExistingArtikl(Artikl artikl) throws SQLException {
        Connection connection = openConnectionToDatabase();

        String updateSQL = "UPDATE artikl SET naziv = ?, kolicina_na_skladistu = ?, minimalne_zalihe = ?, " +
                "jedinicna_cijena = ?, godisnja_potraznja = ?, troskovi_skladistenja = ?, troskovi_nabave = ?, " +
                "id_mjere = ? WHERE id = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(updateSQL);
        preparedStatement.setString(1, artikl.getNaziv());
        preparedStatement.setInt(2, artikl.getKolicinaNaSkladistu());
        preparedStatement.setInt(3, artikl.getMinimalneZalihe());
        preparedStatement.setDouble(4, artikl.getJedinicnaCijena());
        preparedStatement.setInt(5, artikl.getGodisnjaPotraznja());
        preparedStatement.setDouble(6, artikl.getTroskoviSkladistenja());
        preparedStatement.setDouble(7, artikl.getTroskoviNabave());
        preparedStatement.setInt(8, artikl.getIdMjere());
        preparedStatement.setInt(9, artikl.getId());

        preparedStatement.executeUpdate();

        closeConnectionToDatabase(connection, Collections.singletonList(preparedStatement));
    }

    public List<Povijest> getPovijestByArtiklId(int artiklId) throws SQLException {
        List<Povijest> povijesti = new ArrayList<>();

        String selectSQL = "SELECT povijest.*, artikl.naziv FROM povijest JOIN artikl ON povijest.id_artikla = artikl.id " +
                "WHERE artikl.id = ? ORDER BY vremenska_oznaka ASC;";

        Connection connection = openConnectionToDatabase();

        PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
        preparedStatement.setInt(1, artiklId);

        ResultSet rs = preparedStatement.executeQuery();

        while ( rs.next() ) {
            int id = rs.getInt("id");
            Timestamp vremenskaOznaka = rs.getTimestamp("vremenska_oznaka");
            int kolicina = rs.getInt("kolicina");
            int idArtikla = rs.getInt("Id_artikla");
            String naziv = rs.getString("naziv");

            povijesti.add(new Povijest(id, vremenskaOznaka.toLocalDateTime().toLocalDate(), kolicina, idArtikla, naziv));
        }

        closeConnectionToDatabase(connection, Collections.singletonList(preparedStatement));

        return povijesti;
    }

    public List<Dokument> getAllDokument() throws SQLException {
        List<Dokument> dokumenti = new ArrayList<>();

        String selectSQL = "SELECT dokument.id AS dokumentId,  dokument.datum_kreiranja as dokumentDatumKreiranja," +
                " zaposlenik.id AS zaposlenikId, zaposlenik.oib AS zaposlenikOIB, zaposlenik.ime AS zaposlenikIme," +
                " zaposlenik.prezime AS zaposlenikPrezime, zaposlenik.email AS zaposlenikEmail," +
                " zaposlenik.adresa AS zaposlenikAdresa,zaposlenik.telefon AS zaposlenikTelefon," +
                " zaposlenik.vrijeme_unosa AS zaposlenikVrijemeUnosa, poslovni_partner.id AS poslovniPartnerId," +
                " poslovni_partner.oib AS poslovniPartnerOib, poslovni_partner.naziv_pravne_osobe AS poslovniPartnerNazivPravneOsobe," +
                " poslovni_partner.telefon AS poslovniPartnerTelefon, poslovni_partner.email AS poslovniPartnerEmail," +
                " poslovni_partner.adresa AS poslovniPartnerAdresa, poslovni_partner.vrijeme_unosa AS posloniPartnerVrijemeUnosa," +
                " tip_dokumenta.id AS tipDokumentaId, tip_dokumenta.naziv AS tipDokumentaNaziv, tip_dokumenta.akcija AS tipDokumentaAkcija" +
                " FROM dokument JOIN zaposlenik ON dokument.id_zaposlenika = zaposlenik.id" +
                " JOIN poslovni_partner ON dokument.id_poslovnog_partnera = poslovni_partner.id" +
                " JOIN tip_dokumenta ON dokument.id_tip_dokumenta = tip_dokumenta.id;";

        Connection connection = openConnectionToDatabase();
        Statement statement = connection.createStatement();

        ResultSet rs = statement.executeQuery(selectSQL);

        while ( rs.next() ) {
            int dokumentId = rs.getInt("dokumentId");
            Timestamp dokumentDatumKreiranja = rs.getTimestamp("dokumentDatumKreiranja");
            int zaposlenikId = rs.getInt("zaposlenikId");
            String zaposlenikOIB = rs.getString("zaposlenikOIB");
            String zaposlenikIme = rs.getString("zaposlenikIme");
            String zaposlenikPrezime = rs.getString("zaposlenikPrezime");
            String zaposlenikEmail = rs.getString("zaposlenikEmail");
            String zaposlenikAdresa = rs.getString("zaposlenikAdresa");
            String zaposlenikTelefon = rs.getString("zaposlenikTelefon");
            Timestamp zaposlenikVrijemeUnosa = rs.getTimestamp("zaposlenikVrijemeUnosa");

            int poslovniPartnerId = rs.getInt("poslovniPartnerId");
            String poslovniPartnerOib = rs.getString("poslovniPartnerOib");
            String poslovniPartnerNazivPravneOsobe = rs.getString("poslovniPartnerNazivPravneOsobe");
            String poslovniPartnerTelefon = rs.getString("poslovniPartnerTelefon");
            String poslovniPartnerEmail = rs.getString("poslovniPartnerEmail");
            String poslovniPartnerAdresa = rs.getString("poslovniPartnerAdresa");
            Timestamp posloniPartnerVrijemeUnosa = rs.getTimestamp("posloniPartnerVrijemeUnosa");

            int tipDokumentaId = rs.getInt("tipDokumentaId");
            String tipDokumentaNaziv = rs.getString("tipDokumentaNaziv");
            String tipDokumentaAkcija = rs.getString("tipDokumentaAkcija");

            Zaposlenik zaposlenik = new Zaposlenik(zaposlenikId, zaposlenikOIB, zaposlenikIme, zaposlenikPrezime,
                    zaposlenikEmail, zaposlenikAdresa, zaposlenikTelefon, zaposlenikVrijemeUnosa.toLocalDateTime());

            PoslovniPartner poslovniPartner = new PoslovniPartner(poslovniPartnerId, poslovniPartnerOib,
                    poslovniPartnerNazivPravneOsobe, poslovniPartnerTelefon, poslovniPartnerEmail, poslovniPartnerAdresa,
                    posloniPartnerVrijemeUnosa.toLocalDateTime());

            TipDokumenta tipDokumenta = new TipDokumenta(tipDokumentaId, tipDokumentaNaziv, tipDokumentaAkcija);

            dokumenti.add(new Dokument(dokumentId, dokumentDatumKreiranja.toLocalDateTime(), tipDokumenta, zaposlenik, poslovniPartner));
        }

        closeConnectionToDatabase(connection, Collections.singletonList(statement));

        return dokumenti;
    }

    public List<StavkaDokumenta> getStavkeDokumentaByDokumentId(int dokumentId) throws SQLException {
        List<StavkaDokumenta> stavkeDokumenta = new ArrayList<>();

        String selectSQL = "SELECT artikl.naziv, stavke_dokumenta.kolicina, mjera.skracenica " +
                "FROM artikl JOIN mjera ON artikl.id_mjere = mjera.id " +
                "JOIN stavke_dokumenta ON artikl.id = stavke_dokumenta.id_artikla " +
                "WHERE stavke_dokumenta.id_dokumenta = ?";

        Connection connection = openConnectionToDatabase();

        PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
        preparedStatement.setInt(1, dokumentId);

        ResultSet rs = preparedStatement.executeQuery();

        while ( rs.next() ) {
            String naziv = rs.getString("naziv");
            int kolicina = rs.getInt("kolicina");
            String skracenica = rs.getString("skracenica");

            stavkeDokumenta.add(new StavkaDokumenta(naziv, kolicina, skracenica));

        }

        closeConnectionToDatabase(connection, Collections.singletonList(preparedStatement));

        return stavkeDokumenta;

    }

    public void saveNewDokument(Dokument dokument, List<StavkaDokumenta> stavkeDokumenta) throws SQLException {
        Connection connection = openConnectionToDatabase();
        connection.setAutoCommit(false);

        List<Statement> preparedStatementList = new ArrayList<>();

        String insertSQL = "INSERT INTO dokument(datum_kreiranja, id_tip_dokumenta, id_zaposlenika, id_poslovnog_partnera) VALUES (?, ?, ?, ?);";

        PreparedStatement preparedStatement = connection.prepareStatement(insertSQL);
        preparedStatementList.add(preparedStatement);
        preparedStatement.setTimestamp(1, Timestamp.valueOf(dokument.getDatumKreiranja()));
        preparedStatement.setInt(2, dokument.getIdTipDokument());
        preparedStatement.setInt(3, dokument.getIdZaposlenika());
        preparedStatement.setInt(4, dokument.getIdPoslovnigPartnera());

        preparedStatement.executeUpdate();


        PreparedStatement preparedStatementDokumentId = connection.prepareStatement("SELECT dokument.id FROM" +
                " dokument WHERE datum_kreiranja = ? AND id_tip_dokumenta = ? " +
                "AND id_zaposlenika = ? AND id_poslovnog_partnera = ?");
        preparedStatementList.add(preparedStatementDokumentId);

        preparedStatementDokumentId.setTimestamp(1, Timestamp.valueOf(dokument.getDatumKreiranja()));
        preparedStatementDokumentId.setInt(2, dokument.getIdTipDokument());
        preparedStatementDokumentId.setInt(3, dokument.getIdZaposlenika());
        preparedStatementDokumentId.setInt(4, dokument.getIdPoslovnigPartnera());


        ResultSet rs = preparedStatementDokumentId.executeQuery();

        int idDokumenta = 0;

        while ( rs.next() ) {
            idDokumenta = rs.getInt("id");
        }

        for (StavkaDokumenta stavkaDokumenta : stavkeDokumenta) {
            stavkaDokumenta.setIdDokumenta(idDokumenta);

            insertSQL = "INSERT INTO stavke_dokumenta(id_artikla, id_dokumenta, kolicina) VALUES (?, ?, ?);";

            PreparedStatement preparedStatementstavke = connection.prepareStatement(insertSQL);
            preparedStatementList.add(preparedStatementstavke);
            preparedStatementstavke.setInt(1, stavkaDokumenta.getIdArtikla());
            preparedStatementstavke.setInt(2, stavkaDokumenta.getIdDokumenta());
            preparedStatementstavke.setInt(3, stavkaDokumenta.getKolicina());

            preparedStatementstavke.executeUpdate();
        }

        connection.commit();

        closeConnectionToDatabase(connection, Collections.singletonList(preparedStatement));
    }

    private Dokument getDokumentByDatumKreiranjaIdTipDokumentaIdZaposlenikaIdPoslovnogPartnera(LocalDateTime datumKreiranja,
                                            int idTipDokument, int idZaposlenika, int idPoslovnigPartnera) throws SQLException {

        List<Dokument> dokumenti = getAllDokument().stream().filter(d -> d.getDatumKreiranja().equals(datumKreiranja)
                && d.getIdZaposlenika() == idZaposlenika
                && d.getIdTipDokument() == idTipDokument
                && d.getIdPoslovnigPartnera() == idPoslovnigPartnera).collect(Collectors.toList());

        if (dokumenti != null && dokumenti.size() == 1) {
            return dokumenti.get(0);
        }

        return null;
    }

}
