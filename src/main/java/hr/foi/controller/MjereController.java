package hr.foi.controller;

import hr.foi.database.DatabaseWorker;
import hr.foi.model.Mjera;
import hr.foi.utils.MessageDialogUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;


public class MjereController implements Initializable {

    @FXML private GridPane editGridPane;
    @FXML private TableView<Mjera> mjereTableView;
    @FXML private TableColumn<Mjera, String> collMjere;
    @FXML private TableColumn<Mjera, String> collSkracenica;
    @FXML private TextField mjeraTextField;
    @FXML private TextField skracenicaTextField;

    private boolean isEditing;
    private Mjera editingMjera;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        editGridPane.setVisible(false);
        collMjere.setCellValueFactory(new PropertyValueFactory<Mjera, String>("naziv"));
        collSkracenica.setCellValueFactory(new PropertyValueFactory<Mjera, String>("skracenica"));

        refreshTable();
    }

    public void onPohraniButtonClicked() {
        DatabaseWorker databaseWorker = DatabaseWorker.getInstance();

        if (isFormValid()) {
            try {
                if (isEditing) {
                    editingMjera.setNaziv(mjeraTextField.getText());
                    editingMjera.setSkracenica(skracenicaTextField.getText());

                    databaseWorker.saveExistingMjera(editingMjera);
                } else {
                    Mjera mjera = new Mjera();
                    mjera.setNaziv(mjeraTextField.getText());
                    mjera.setSkracenica(skracenicaTextField.getText());

                    databaseWorker.saveNewMjera(mjera);
                }

                editGridPane.setVisible(false);
                refreshTable();
            } catch (SQLException e) {
                MessageDialogUtils.showMessage(Alert.AlertType.ERROR, "Greška",
                        "Došlo je do pogreške prilikom pohrane podataka.", "Razlog greške: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    public void onIzmjeniButtonClicked() {
        Mjera mjera = mjereTableView.getSelectionModel().getSelectedItem();

        if (mjera != null) {
            editingMjera = mjera;
            mjeraTextField.setText(mjera.getNaziv());
            skracenicaTextField.setText(mjera.getSkracenica());

            editGridPane.setVisible(true);
            isEditing = true;
        }
    }

    public void onObrisiButtonClicked() {
        Mjera mjera = mjereTableView.getSelectionModel().getSelectedItem();

        if (mjera != null) {
            DatabaseWorker databaseWorker = DatabaseWorker.getInstance();
            try {
                databaseWorker.deleteMjera(mjera);
            } catch (SQLException e) {
                MessageDialogUtils.showMessage(Alert.AlertType.ERROR, "Greška",
                        "Došlo je do pogreške prilikom pohrane podataka.", "Razlog greške: " + e.getMessage());
                e.printStackTrace();
            }
        }

        refreshTable();
    }

    public void onNovoButtonClicked() {
        isEditing = false;
        mjeraTextField.setText("");
        skracenicaTextField.setText("");

        editGridPane.setVisible(true);
    }

    private void refreshTable() {
        DatabaseWorker databaseWorker = DatabaseWorker.getInstance();
        try {
            ObservableList<Mjera> ol = FXCollections.observableArrayList(databaseWorker.getAllMjera());
            mjereTableView.setItems(ol);
        } catch (SQLException e) {
            MessageDialogUtils.showMessage(Alert.AlertType.ERROR, "Greška",
                    "Došlo je do pogreške prilikom pohrane podataka.", "Razlog greške: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private boolean isFormValid() {
        boolean isValid = true;

        Border errorBorder = new Border(new BorderStroke(Color.RED,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT));

        if (mjeraTextField.getText().isEmpty() || mjeraTextField.getText().length() < 2) {
            isValid = false;
            mjeraTextField.setBorder(errorBorder);
        } else {
            mjeraTextField.setBorder(null);
        }

        if (skracenicaTextField.getText().isEmpty() || skracenicaTextField.getText().length() < 1) {
            isValid = false;
            skracenicaTextField.setBorder(errorBorder);
        } else {
            skracenicaTextField.setBorder(null);
        }

        return isValid;
    }
}
