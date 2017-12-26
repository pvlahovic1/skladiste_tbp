package hr.foi.controller.edit;

import hr.foi.database.DatabaseWorker;
import hr.foi.model.Dokument;
import hr.foi.model.StavkaDokumenta;
import hr.foi.utils.MessageDialogUtils;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class DokumentDetailController implements Initializable {

    @FXML private TableView<StavkaDokumenta> daokumentDetaljiTableView;
    @FXML private TableColumn<StavkaDokumenta, String> collArtikl;
    @FXML private TableColumn<StavkaDokumenta, String> collKolicina;

    @FXML private Label lblDokumentNaziv;
    @FXML private Label lblTipDokumeta;
    @FXML private Label lblDatumKreiranja;
    @FXML private Label lblZaposlenik;

    private Dokument dokument;
    private Stage stage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        collArtikl.setCellValueFactory(new PropertyValueFactory<>("naziv"));
        collKolicina.setCellValueFactory(new PropertyValueFactory<>("kolicinaTableCollValue"));
    }

    public void setDokument(Dokument dokument) {
        this.dokument = dokument;
        refreshTable();
        refreshDokumentData();
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    private void refreshTable() {
        DatabaseWorker databaseWorker = DatabaseWorker.getInstance();

        try {
            daokumentDetaljiTableView.setItems(FXCollections.observableList(databaseWorker.getStavkeDokumentaByDokumentId(dokument.getId())));
        } catch (SQLException e) {
            MessageDialogUtils.showMessage(Alert.AlertType.ERROR, "Greška",
                    "Došlo je do pogreške prilikom pohrane podataka.", "Razlog greške: " + e.getMessage());
            stage.close();
        }
    }

    private void refreshDokumentData() {
        lblDokumentNaziv.setText("Dokument: " + dokument.getTipDokumenta().getNaziv());
        lblDatumKreiranja.setText("Datum kreiranja: " + dokument.getDatumKreiranja().toLocalDate().toString());
        lblTipDokumeta.setText("Tip dokumenta: " + dokument.getTipDokumenta().getAkcija());
        lblZaposlenik.setText("Zaposlenik: " + dokument.getZaposlenik().getIme() + " " + dokument.getZaposlenik().getPrezime());
    }

}
