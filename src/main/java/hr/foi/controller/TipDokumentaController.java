package hr.foi.controller;

import hr.foi.database.DatabaseWorker;
import hr.foi.model.TipDokumenta;
import hr.foi.utils.MessageDialogUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import org.codehaus.plexus.util.StringUtils;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class TipDokumentaController implements Initializable {

    @FXML private TableView<TipDokumenta> tipDokumentaTableView;
    @FXML private TableColumn<TipDokumenta, String> collNaziv;
    @FXML private TableColumn<TipDokumenta, String> collAkcija;
    @FXML private GridPane editGridPane;
    @FXML private TextField nazivTextView;
    @FXML private RadioButton ulazRobeRadioButton;
    @FXML private RadioButton izlazRobeRadioButton;
    private ToggleGroup radioButtonGroup;

    private boolean isEditing;
    private TipDokumenta editedTipDokumenta;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        radioButtonGroup = new ToggleGroup();
        ulazRobeRadioButton.setToggleGroup(radioButtonGroup);
        ulazRobeRadioButton.setSelected(true);
        izlazRobeRadioButton.setToggleGroup(radioButtonGroup);

        editGridPane.setVisible(false);

        collNaziv.setCellValueFactory(new PropertyValueFactory<TipDokumenta, String>("naziv"));
        collAkcija.setCellValueFactory(new PropertyValueFactory<TipDokumenta, String>("akcija"));

        refreshTable();
    }

    private void refreshTable() {
        DatabaseWorker databaseWorker = DatabaseWorker.getInstance();
        try {
            tipDokumentaTableView.setItems(FXCollections.observableList(databaseWorker.getAllTipDokumenta()));
        } catch (SQLException e) {
            MessageDialogUtils.showMessage(Alert.AlertType.ERROR, "Greška", "Došlo je do greške u radu s bazom podataka.",
                    "Razlog: " + e.getMessage());
        }
    }

    public void onPohraniButtonClicked() {
        if (isFormValid()) {
            DatabaseWorker databaseWorker = DatabaseWorker.getInstance();

            try {
                if (isEditing) {
                    editedTipDokumenta.setNaziv(nazivTextView.getText());
                    editedTipDokumenta.setAkcija(getAkcija());
                    databaseWorker.saveExistingTipDokumenta(editedTipDokumenta);
                } else {
                    TipDokumenta tipDokumenta = new TipDokumenta();
                    tipDokumenta.setNaziv(nazivTextView.getText());
                    tipDokumenta.setAkcija(getAkcija());
                    databaseWorker.saveNewTipDokumenta(tipDokumenta);
                }

                refreshTable();
            } catch (SQLException e) {
                MessageDialogUtils.showMessage(Alert.AlertType.ERROR, "Greška", "Došlo je do greške u radu s bazom podataka.",
                        "Razlog: " + e.getMessage());
            }
        }
        editGridPane.setVisible(false);
    }

    public void onIzmjeniButtonClicked() {
        TipDokumenta tipDokumenta = tipDokumentaTableView.getSelectionModel().getSelectedItem();

        if (tipDokumenta != null) {
            editedTipDokumenta = tipDokumenta;
            isEditing = true;
            nazivTextView.setText(tipDokumenta.getNaziv());

            if (!StringUtils.isEmpty(tipDokumenta.getAkcija()) && tipDokumenta.getAkcija().equals("ULAZ")) {
                ulazRobeRadioButton.setSelected(true);
                izlazRobeRadioButton.setSelected(false);
            } else {
                ulazRobeRadioButton.setSelected(false);
                izlazRobeRadioButton.setSelected(true);
            }

            editGridPane.setVisible(true);
        }
    }

    public void onObrisiButtonClicked() {
        TipDokumenta tipDokumenta = tipDokumentaTableView.getSelectionModel().getSelectedItem();

        if (tipDokumenta != null) {
            DatabaseWorker databaseWorker = DatabaseWorker.getInstance();

            try {
                databaseWorker.deleteTipDokumenta(tipDokumenta);
                refreshTable();
            } catch (SQLException e) {
                MessageDialogUtils.showMessage(Alert.AlertType.ERROR, "Greška", "Došlo je do greške u radu s bazom podataka.",
                        "Razlog: " + e.getMessage());
            }
        }
    }

    public void onNovoButtonClicked() {
        isEditing = false;
        ulazRobeRadioButton.setSelected(true);
        nazivTextView.setText("");
        editGridPane.setVisible(true);
    }

    private String getAkcija() {
        String akcija = "";
        if (ulazRobeRadioButton.isSelected()) {
            akcija = "ULAZ";
        } else {
            akcija = "IZLAZ";
        }

        return akcija;
    }

    private boolean isFormValid() {
        boolean isValid = true;
        Border errorBorder = new Border(new BorderStroke(Color.RED,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT));


        if (nazivTextView.getText().isEmpty() || nazivTextView.getText().length() < 4) {
            nazivTextView.setBorder(errorBorder);
            isValid = false;
        } else {
            nazivTextView.setBorder(null);
        }

        return isValid;
    }
}
