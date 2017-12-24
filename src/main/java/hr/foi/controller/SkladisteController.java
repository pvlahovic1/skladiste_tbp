package hr.foi.controller;

import hr.foi.controller.edit.ArtiklEditController;
import hr.foi.controller.edit.ZaposlenikEditController;
import hr.foi.core.DatabaseDataChangedListener;
import hr.foi.database.DatabaseWorker;
import hr.foi.model.Artikl;
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
import java.util.ResourceBundle;

public class SkladisteController implements Initializable, DatabaseDataChangedListener {

    @FXML private TableView<Artikl> artikliTableView;
    @FXML private TableColumn<Artikl, String> collNaziv;
    @FXML private TableColumn<Artikl, String> collKolicinaNaSkladistu;
    @FXML private TableColumn<Artikl, Integer> collMinimalneZalihe;
    @FXML private TableColumn<Artikl, Double> collJedinicnaCijena;
    @FXML private TableColumn<Artikl, Integer> collGodisnjaPotraznja;
    @FXML private TableColumn<Artikl, Double> collTroskoviNabave;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        collNaziv.setCellValueFactory(new PropertyValueFactory<Artikl, String>("naziv"));
        collKolicinaNaSkladistu.setCellValueFactory(new PropertyValueFactory<Artikl, String>("kolicinaNaSkladistuTableCollValue"));
        collMinimalneZalihe.setCellValueFactory(new PropertyValueFactory<Artikl, Integer>("minimalneZalihe"));
        collJedinicnaCijena.setCellValueFactory(new PropertyValueFactory<Artikl, Double>("jedinicnaCijena"));
        collGodisnjaPotraznja.setCellValueFactory(new PropertyValueFactory<Artikl, Integer>("godisnjaPotraznja"));
        collTroskoviNabave.setCellValueFactory(new PropertyValueFactory<Artikl, Double>("troskoviNabave"));

        refreshTable();
    }

    public void onIzmjeniButtonClicked() {

    }

    public void onNovoButtonClicked() {
        Pane root;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ArtiklEditView.fxml"));
            root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Dodavanje novog artikla");
            stage.setScene(new Scene(root, 400, 550));

            ((ArtiklEditController)fxmlLoader.getController()).setDatabaseDataChangedListener(this);
            ((ArtiklEditController)fxmlLoader.getController()).setStage(stage);

            stage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onObrisiButtonClicked() {
        Artikl artikl = artikliTableView.getSelectionModel().getSelectedItem();

        if (artikl != null) {
            DatabaseWorker databaseWorker = DatabaseWorker.getInstance();

            try {
                databaseWorker.deleteArtikl(artikl);
                refreshTable();
            } catch (SQLException e) {
                MessageDialogUtils.showMessage(Alert.AlertType.ERROR, "Greška", "Došlo je do greške u radu s bazom podataka.",
                        "Razlog: " + e.getMessage());
            }
        }
    }

    private void refreshTable() {
        DatabaseWorker databaseWorker = DatabaseWorker.getInstance();
        try {
            artikliTableView.setItems(FXCollections.observableArrayList(databaseWorker.getAllArtikl()));
        } catch (SQLException e) {
            MessageDialogUtils.showMessage(Alert.AlertType.ERROR, "Greška", "Došlo je do greške u radu s bazom podataka.",
                    "Razlog: " + e.getMessage());
        }

    }

    @Override
    public void onDataChanged() {
        refreshTable();
    }
}
