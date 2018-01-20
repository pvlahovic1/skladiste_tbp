package hr.foi.controller;

import hr.foi.controller.edit.PoslovniPartnerEditController;
import hr.foi.core.DatabaseDataChangedListener;
import hr.foi.database.DatabaseWorker;
import hr.foi.model.PoslovniPartner;
import hr.foi.utils.MessageDialogUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import java.util.ResourceBundle;

public class PoslovniPartneriController implements Initializable, DatabaseDataChangedListener {

    @FXML
    private TableView<PoslovniPartner> poslovniPartneriTable;
    @FXML private TableColumn<PoslovniPartner, String> collOib;
    @FXML private TableColumn<PoslovniPartner, String> collNazivPravneOsobe;
    @FXML private TableColumn<PoslovniPartner, String> collTelefon;
    @FXML private TableColumn<PoslovniPartner, String> collEmail;
    @FXML private TableColumn<PoslovniPartner, String> collAdresa;
    @FXML private TableColumn<PoslovniPartner, String> collVrijemeUnosa;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        collOib.setCellValueFactory(new PropertyValueFactory<PoslovniPartner, String>("oib"));
        collNazivPravneOsobe.setCellValueFactory(new PropertyValueFactory<PoslovniPartner, String>("nazivPravneOsobe"));
        collEmail.setCellValueFactory(new PropertyValueFactory<PoslovniPartner, String>("email"));
        collAdresa.setCellValueFactory(new PropertyValueFactory<PoslovniPartner, String>("adresa"));
        collTelefon.setCellValueFactory(new PropertyValueFactory<PoslovniPartner, String>("telefon"));
        collVrijemeUnosa.setCellValueFactory(new PropertyValueFactory<PoslovniPartner, String>("vrijemeUnosa"));

        refreshTable();
    }

    private void refreshTable() {
        DatabaseWorker database = DatabaseWorker.getInstance();

        try {
            ObservableList<PoslovniPartner> ol = FXCollections.observableArrayList(database.getAllPoslovniPartneri());
            poslovniPartneriTable.setItems(ol);
        } catch (SQLException e) {
            MessageDialogUtils.showMessage(Alert.AlertType.ERROR, "Greška",
                    "Došlo je do pogreške prilikom pohrane podataka.", "Razlog greške: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void dodajButtonClicked() {
        Pane root;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/PoslovniPartnerEditView.fxml"));
            root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Dodavanje novog poslovnog partnera");
            stage.setScene(new Scene(root, 400, 550));

            ((PoslovniPartnerEditController)fxmlLoader.getController()).setDatabaseDataChangedListener(this);
            ((PoslovniPartnerEditController)fxmlLoader.getController()).setScene(stage);

            stage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void izmjeniButtonClicked() {
        PoslovniPartner poslovniPartner = poslovniPartneriTable.getSelectionModel().getSelectedItem();

        if (poslovniPartner != null) {
            Pane root;
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/PoslovniPartnerEditView.fxml"));
                root = fxmlLoader.load();
                Stage stage = new Stage();
                stage.setTitle("Izmjena podataka: " + poslovniPartner.getNazivPravneOsobe());
                stage.setScene(new Scene(root, 400, 550));

                ((PoslovniPartnerEditController)fxmlLoader.getController()).setDatabaseDataChangedListener(this);
                ((PoslovniPartnerEditController)fxmlLoader.getController()).setScene(stage);
                ((PoslovniPartnerEditController)fxmlLoader.getController()).setEditPoslovniPartner(poslovniPartner);

                stage.show();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void obrisiButtonClicked() {
        PoslovniPartner poslovniPartner = poslovniPartneriTable.getSelectionModel().getSelectedItem();

        if (poslovniPartner != null) {
            DatabaseWorker databaseWorker = DatabaseWorker.getInstance();

            try {
                databaseWorker.deletePoslovniPartner(poslovniPartner);
                refreshTable();
            } catch (SQLException e) {
                MessageDialogUtils.showMessage(Alert.AlertType.ERROR, "Greška", null, "Došlo je do greške u radu s bazom podataka.");
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onDataChanged() {
        refreshTable();
    }
}
