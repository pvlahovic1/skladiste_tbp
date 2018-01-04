package hr.foi.controller.edit;

import hr.foi.core.DatabaseDataChangedListener;
import hr.foi.database.DatabaseWorker;
import hr.foi.model.*;
import hr.foi.utils.MessageDialogUtils;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import static hr.foi.utils.ApplicationUtils.isIntegerAndBiggerThen;

public class NewDokumentCreationController implements Initializable {

    @FXML private ComboBox<TipDokumenta> tipDokumentaComboBox;
    @FXML private ComboBox<Zaposlenik> zaposlenikComboBox;
    @FXML private ComboBox<Artikl> artiklComboBox;
    @FXML private ComboBox<PoslovniPartner> poslovniPartnerComboBox;
    @FXML private Label lblDatum;
    @FXML private TextField kolicinaTextField;
    @FXML private TableView<StavkaDokumenta> artikliTableView;
    @FXML private TableColumn<StavkaDokumenta, String> collArtiklNaziv;
    @FXML private TableColumn<StavkaDokumenta, Integer> collArtiklKolicina;

    private List<Artikl> artikli;
    private DatabaseDataChangedListener databaseDataChangedListener;
    private Stage stage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        collArtiklNaziv.setCellValueFactory(new PropertyValueFactory<StavkaDokumenta, String>("naziv"));
        collArtiklKolicina.setCellValueFactory(new PropertyValueFactory<StavkaDokumenta, Integer>("kolicina"));

        refreshTipoveDokumenata();
        refreshZaposlenici();
        refreshArtikli();
        refreshPoslovniPartner();
        lblDatum.setText(LocalDateTime.now().toString());
    }

    private void refreshPoslovniPartner() {
        DatabaseWorker databaseWorker = DatabaseWorker.getInstance();

        try {
            List<PoslovniPartner> poslovniPartneri = databaseWorker.getAllPoslovniPartneri();

            if (poslovniPartneri.isEmpty()) {
                MessageDialogUtils.showMessage(Alert.AlertType.INFORMATION, "Preporuka", null,
                        "Prije dodavanja novog dokumenta morate dodati poslovne partnere");
                stage.close();
            } else {
                poslovniPartnerComboBox.getItems().addAll(poslovniPartneri);
                poslovniPartnerComboBox.getSelectionModel().select(0);
            }
        } catch (SQLException e) {
            MessageDialogUtils.showMessage(Alert.AlertType.ERROR, "Greška", "Došlo je do greške u radu s bazom podataka.",
                    "Razlog: " + e.getMessage());
        }
    }

    private void refreshArtikli() {
        DatabaseWorker databaseWorker = DatabaseWorker.getInstance();

        try {
            artikli = databaseWorker.getAllArtikl();

            if (artikli.isEmpty()) {
                MessageDialogUtils.showMessage(Alert.AlertType.INFORMATION, "Preporuka", null,
                        "Prije dodavanja novog dokumenta morate dodati artikle");
                stage.close();
            } else {
                artiklComboBox.getItems().addAll(new ArrayList<>(artikli));
            }
        } catch (SQLException e) {
            MessageDialogUtils.showMessage(Alert.AlertType.ERROR, "Greška", "Došlo je do greške u radu s bazom podataka.",
                    "Razlog: " + e.getMessage());
        }
    }

    private void refreshZaposlenici() {
        DatabaseWorker databaseWorker = DatabaseWorker.getInstance();

        try {
            List<Zaposlenik> zaposlenici = databaseWorker.getAllZaposlenik();

            if (!zaposlenici.isEmpty()) {
                zaposlenikComboBox.setItems(FXCollections.observableList(zaposlenici));
                zaposlenikComboBox.getSelectionModel().select(0);
            } else {
                MessageDialogUtils.showMessage(Alert.AlertType.INFORMATION, "Preporuka", null,
                        "Prije dodavanja dokumenta morate dodati zaposlenike.");
                stage.close();
            }
        } catch (SQLException e) {
            MessageDialogUtils.showMessage(Alert.AlertType.ERROR, "Greška", "Došlo je do greške u radu s bazom podataka.",
                    "Razlog: " + e.getMessage());
        }
    }

    private void refreshTipoveDokumenata() {
        DatabaseWorker databaseWorker = DatabaseWorker.getInstance();

        try {
            List<TipDokumenta> tipoviDokumenta = databaseWorker.getAllTipDokumenta();

            if (!tipoviDokumenta.isEmpty()) {
                tipDokumentaComboBox.setItems(FXCollections.observableList(tipoviDokumenta));
                tipDokumentaComboBox.getSelectionModel().select(0);
            } else {
                MessageDialogUtils.showMessage(Alert.AlertType.INFORMATION, "Preporuka", null,
                        "Prije dodavanja dokumenta morate dodati tipove dokumenata.");
                stage.close();
            }
        } catch (SQLException e) {
            MessageDialogUtils.showMessage(Alert.AlertType.ERROR, "Greška", "Došlo je do greške u radu s bazom podataka.",
                    "Razlog: " + e.getMessage());
        }
    }

    public void setOdabraniArtikli(List<Artikl> odabraniArtikli) {
        if (artiklComboBox.getItems() != null && !artiklComboBox.getItems().isEmpty()) {
            List<Integer> ids = odabraniArtikli.stream().map(Artikl::getId).collect(Collectors.toList());

            artiklComboBox.getItems()
                    .removeAll(artiklComboBox.getItems()
                            .stream()
                            .filter(artikl -> ids.contains(artikl.getId()))
                            .collect(Collectors.toList()));
        }

        List<StavkaDokumenta> stavkeDokumenta = odabraniArtikli.stream()
                .map(a -> new StavkaDokumenta(a.getId(), a.getNaziv(), a.getEkonomicnaKolicinaNarucivanja(), a.getMjera().getSkracenica()))
                .collect(Collectors.toList());

        artikliTableView.setItems(FXCollections.observableList(stavkeDokumenta));
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public DatabaseDataChangedListener getDatabaseDataChangedListener() {
        return databaseDataChangedListener;
    }

    public void setDatabaseDataChangedListener(DatabaseDataChangedListener databaseDataChangedListener) {
        this.databaseDataChangedListener = databaseDataChangedListener;
    }

    public void onDodajButtonClicked() {
        Artikl artikl = artiklComboBox.getValue();
        if (artikl != null) {

            if (isIntegerAndBiggerThen(kolicinaTextField.getText(), 0)) {
                StavkaDokumenta stavkaDokumenta = new StavkaDokumenta(artikl.getId(), artikl.getNaziv(),
                        Integer.valueOf(kolicinaTextField.getText()), artikl.getMjera().getSkracenica());

                kolicinaTextField.setText("");
                artiklComboBox.getItems().remove(artikl);
                artikliTableView.getItems().add(stavkaDokumenta);
            } else {
                MessageDialogUtils.showMessage(Alert.AlertType.INFORMATION, "", null, "Količina mora biti pozitivna vrijednost.");
            }
        } else {
            MessageDialogUtils.showMessage(Alert.AlertType.INFORMATION, "", null, "Artikl mora biti odabran.");
        }
    }

    public void onObrisiButtonClicked() {
        StavkaDokumenta stavkaDokumenta = artikliTableView.getSelectionModel().getSelectedItem();

        if (stavkaDokumenta != null) {
            artikliTableView.getItems().remove(stavkaDokumenta);
            artiklComboBox.getItems().addAll(artikli.stream().filter(artikl -> artikl.getId() == stavkaDokumenta.getIdArtikla()).collect(Collectors.toList()));
        }
    }

    public void onKreirajDokumentClicked() {
        if (artikliTableView.getItems() != null && !artikliTableView.getItems().isEmpty()) {
            Dokument dokument = new Dokument(LocalDateTime.now(), tipDokumentaComboBox.getValue().getId(),
                    zaposlenikComboBox.getValue().getId(), poslovniPartnerComboBox.getValue().getId());

            DatabaseWorker databaseWorker = DatabaseWorker.getInstance();
            try {
                databaseWorker.saveNewDokument(dokument, artikliTableView.getItems());
                databaseDataChangedListener.onDataChanged();
                stage.close();
            } catch (SQLException e) {
                MessageDialogUtils.showMessage(Alert.AlertType.ERROR, "Greška",
                        "Došlo je do pogreške prilikom pohrane podataka.", "Razlog greške: " + e.getMessage());
                e.printStackTrace();
            }

        }
    }

}
