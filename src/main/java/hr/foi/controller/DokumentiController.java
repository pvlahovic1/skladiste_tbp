package hr.foi.controller;

import hr.foi.controller.edit.DokumentDetailController;
import hr.foi.controller.edit.PovijestController;
import hr.foi.database.DatabaseWorker;
import hr.foi.model.Dokument;
import hr.foi.utils.MessageDialogUtils;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class DokumentiController implements Initializable {

    @FXML private TableView<Dokument> dokumentTableView;
    @FXML private TableColumn<Dokument, String> collDokument;
    @FXML private TableColumn<Dokument, LocalDate> collDatumKreiranja;
    @FXML private TableColumn<Dokument, String> collZaposlenik;
    @FXML private TableColumn<Dokument, String> collPoslovniPartner;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        collDokument.setCellValueFactory(new PropertyValueFactory<Dokument, String>("tipDokumenta"));
        collDatumKreiranja.setCellValueFactory(new PropertyValueFactory<Dokument, LocalDate>("datumKreiranja"));
        collZaposlenik.setCellValueFactory(new PropertyValueFactory<Dokument, String>("zaposlenik"));
        collPoslovniPartner.setCellValueFactory(new PropertyValueFactory<Dokument, String>("poslovniPartner"));
        refreshTable();
    }

    public void onDetaljiClicked() {
        Dokument dokument = dokumentTableView.getSelectionModel().getSelectedItem();

        if (dokument != null) {
            Pane root;
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/DokumentDetailView.fxml"));
                root = fxmlLoader.load();
                Stage stage = new Stage();
                stage.setTitle("Prikaz detalja dokumenta: " + dokument.getTipDokumenta().getNaziv()
                        + dokument.getDatumKreiranja().toLocalDate().toString());
                stage.setScene(new Scene(root, 800, 600));

                ((DokumentDetailController)fxmlLoader.getController()).setDokument(dokument);
                ((DokumentDetailController)fxmlLoader.getController()).setStage(stage);

                stage.show();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void refreshTable() {
        DatabaseWorker databaseWorker = DatabaseWorker.getInstance();

        try {
            dokumentTableView.setItems(FXCollections.observableList(databaseWorker.getAllDokument()));
        } catch (SQLException e) {
            MessageDialogUtils.showMessage(Alert.AlertType.ERROR, "Greška",
                    "Došlo je do pogreške prilikom pohrane podataka.", "Razlog greške: " + e.getMessage());
        }
    }
}
