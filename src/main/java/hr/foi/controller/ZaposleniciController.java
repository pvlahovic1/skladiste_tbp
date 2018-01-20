package hr.foi.controller;

import hr.foi.controller.edit.ZaposlenikEditController;
import hr.foi.core.DatabaseDataChangedListener;
import hr.foi.database.DatabaseWorker;
import hr.foi.model.Zaposlenik;
import hr.foi.utils.MessageDialogUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
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


public class ZaposleniciController implements Initializable, DatabaseDataChangedListener {

    @FXML private TableView<Zaposlenik> zposleniciTable;
    @FXML private TableColumn<Zaposlenik, String> collOib;
    @FXML private TableColumn<Zaposlenik, String> collIme;
    @FXML private TableColumn<Zaposlenik, String> collPrezime;
    @FXML private TableColumn<Zaposlenik, String> collEmail;
    @FXML private TableColumn<Zaposlenik, String> collAdresa;
    @FXML private TableColumn<Zaposlenik, String> collTelefon;
    @FXML private TableColumn<Zaposlenik, String> collVrijemeUnosa;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        collOib.setCellValueFactory(new PropertyValueFactory<Zaposlenik, String>("oib"));
        collIme.setCellValueFactory(new PropertyValueFactory<Zaposlenik, String>("ime"));
        collPrezime.setCellValueFactory(new PropertyValueFactory<Zaposlenik, String>("prezime"));
        collEmail.setCellValueFactory(new PropertyValueFactory<Zaposlenik, String>("emai"));
        collAdresa.setCellValueFactory(new PropertyValueFactory<Zaposlenik, String>("adresa"));
        collTelefon.setCellValueFactory(new PropertyValueFactory<Zaposlenik, String>("telefon"));
        collVrijemeUnosa.setCellValueFactory(new PropertyValueFactory<Zaposlenik, String>("vrijemeUnosa"));

        refreshTable();
    }

    public void dodajButtonClicked() {
        Pane root;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ZaposlenikEditView.fxml"));
            root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Dodavanje novog zaposlenik");
            stage.setScene(new Scene(root, 400, 550));

            ((ZaposlenikEditController)fxmlLoader.getController()).setDatabaseDataChangedListener(this);
            ((ZaposlenikEditController)fxmlLoader.getController()).setScene(stage);

            stage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void izmjeniButtonClicked() {
        Zaposlenik zaposlenik = zposleniciTable.getSelectionModel().getSelectedItem();

        if (zaposlenik != null) {
            Pane root;
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ZaposlenikEditView.fxml"));
                root = fxmlLoader.load();
                Stage stage = new Stage();
                stage.setTitle("Izmjena podataka:" + zaposlenik.getIme() + " " + zaposlenik.getPrezime());
                stage.setScene(new Scene(root, 400, 550));

                ((ZaposlenikEditController)fxmlLoader.getController()).setDatabaseDataChangedListener(this);
                ((ZaposlenikEditController)fxmlLoader.getController()).setScene(stage);
                ((ZaposlenikEditController)fxmlLoader.getController()).setEditZaposelnik(zaposlenik);

                stage.show();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void obrisiButtonClicked() {
        Zaposlenik zaposlenik = zposleniciTable.getSelectionModel().getSelectedItem();

        if (zaposlenik != null) {
            DatabaseWorker databaseWorker = DatabaseWorker.getInstance();

            try {
                databaseWorker.deleteZaposlenik(zaposlenik);
                refreshTable();
            } catch (SQLException e) {
                MessageDialogUtils.showMessage(Alert.AlertType.ERROR, "Greška", null, "Došlo je do greške u radu s bazom podataka.");
                e.printStackTrace();
            }
        }
    }

    private void refreshTable() {
        DatabaseWorker database = DatabaseWorker.getInstance();

        try {
            ObservableList<Zaposlenik> ol = FXCollections.observableArrayList(database.getAllZaposlenik());
            zposleniciTable.setItems(ol);
        } catch (SQLException e) {
            MessageDialogUtils.showMessage(Alert.AlertType.ERROR, "Greška",
                    "Došlo je do pogreške prilikom pohrane podataka.", "Razlog greške: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void onDataChanged() {
        refreshTable();
    }

}
