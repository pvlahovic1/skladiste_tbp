package hr.foi.controller;

import hr.foi.controller.edit.NewDokumentCreationController;
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
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class NoviDokumentController implements Initializable, DatabaseDataChangedListener {

    @FXML private TableView<Artikl> potrebniArtikliTableView;
    @FXML private TableColumn<Artikl, String> collPotrebniArtikl;
    @FXML private TableColumn<Artikl, Integer> collPotrebniArtiklKolicinaNaSkladistu;
    @FXML private TableColumn<Artikl, Integer> collPotrebniArtiklMinimalnaKolicina;
    @FXML private TableColumn<Artikl, Double> collPotrebniArtiklCijenaNarucivanja;
    @FXML private TableColumn<Artikl, Double> collPotrebniArtikCijenaSkladistenja;
    @FXML private TableColumn<Artikl, Integer> collPotrebniArtiklEkonomicnaKolicinaNarucivanja;

    @FXML private TableView<Artikl> moguciArtikliTableView;
    @FXML private TableColumn<Artikl, String> collMoguciArtikli;
    @FXML private TableColumn<Artikl, Integer> collMoguciArtikliKolicinaNaSkladistu;
    @FXML private TableColumn<Artikl, Integer> collMoguciArtikliMinimalnaKolicina;
    @FXML private TableColumn<Artikl, Double> collMoguciArtikliCijenaNaruciavanja;
    @FXML private TableColumn<Artikl, Double> collMoguciArtikliCijenaSkladistenja;

    private List<Artikl> artikli;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        potrebniArtikliTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        moguciArtikliTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        collPotrebniArtikl.setCellValueFactory(new PropertyValueFactory<Artikl, String>("naziv"));
        collPotrebniArtiklKolicinaNaSkladistu.setCellValueFactory(new PropertyValueFactory<Artikl, Integer>("kolicinaNaSkladistuTableCollValue"));
        collPotrebniArtiklMinimalnaKolicina.setCellValueFactory(new PropertyValueFactory<Artikl, Integer>("minimalnaKolicinaNaSkladistuTableCollValue"));
        collPotrebniArtiklCijenaNarucivanja.setCellValueFactory(new PropertyValueFactory<Artikl, Double>("troskoviNabave"));
        collPotrebniArtikCijenaSkladistenja.setCellValueFactory(new PropertyValueFactory<Artikl, Double>("troskoviSkladistenja"));
        collPotrebniArtiklEkonomicnaKolicinaNarucivanja.setCellValueFactory(new PropertyValueFactory<Artikl, Integer>("ekonomicnaKolicinaNarucivanja"));

        collMoguciArtikli.setCellValueFactory(new PropertyValueFactory<Artikl, String>("naziv"));
        collMoguciArtikliKolicinaNaSkladistu.setCellValueFactory(new PropertyValueFactory<Artikl, Integer>("kolicinaNaSkladistuTableCollValue"));
        collMoguciArtikliMinimalnaKolicina.setCellValueFactory(new PropertyValueFactory<Artikl, Integer>("minimalnaKolicinaNaSkladistuTableCollValue"));
        collMoguciArtikliCijenaNaruciavanja.setCellValueFactory(new PropertyValueFactory<Artikl, Double>("troskoviNabave"));
        collMoguciArtikliCijenaSkladistenja.setCellValueFactory(new PropertyValueFactory<Artikl, Double>("troskoviSkladistenja"));

        refreshTables();
    }

    private void refreshTables() {
        DatabaseWorker databaseWorker = DatabaseWorker.getInstance();

        try {
            artikli = databaseWorker.getAllArtikl();
            moguciArtikliTableView.setItems(FXCollections.observableList(artikli));
            refreshPotrebniArtikli();
        } catch (SQLException e) {
            MessageDialogUtils.showMessage(Alert.AlertType.ERROR, "Greška",
                    "Došlo je do pogreške prilikom pohrane podataka.", "Razlog greške: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void refreshPotrebniArtikli() {
        List<Artikl> potrebniArtikli = artikli.stream().filter(artikl -> artikl.getKolicinaNaSkladistu() <= artikl.getMinimalneZalihe())
                .collect(Collectors.toList());

        potrebniArtikliTableView.setItems(FXCollections.observableList(potrebniArtikli));
    }

    public void onKreirajPotrebniDokumentButtonClicked() {
        Pane root;
        try {
            List<Artikl> odabraniArtikli = new ArrayList<>();

            if (potrebniArtikliTableView.getSelectionModel().getSelectedItems() != null) {
                odabraniArtikli.addAll(potrebniArtikliTableView.getSelectionModel().getSelectedItems());
            }

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/NewDokumentCreationView.fxml"));
            root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Kreiranje novog dokumenta");
            stage.setScene(new Scene(root, 800, 600));

            ((NewDokumentCreationController)fxmlLoader.getController()).setDatabaseDataChangedListener(this);
            ((NewDokumentCreationController)fxmlLoader.getController()).setStage(stage);
            ((NewDokumentCreationController)fxmlLoader.getController()).setOdabraniArtikli(odabraniArtikli);

            stage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onKreirajMoguciDokumentButtonClicked() {
        Pane root;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/NewDokumentCreationView.fxml"));
            root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Kreiranje novog dokumenta");
            stage.setScene(new Scene(root, 800, 600));

            ((NewDokumentCreationController)fxmlLoader.getController()).setDatabaseDataChangedListener(this);
            ((NewDokumentCreationController)fxmlLoader.getController()).setStage(stage);

            stage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDataChanged() {
        refreshTables();
    }
}
