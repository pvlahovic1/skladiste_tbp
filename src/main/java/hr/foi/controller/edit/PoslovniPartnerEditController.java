package hr.foi.controller.edit;

import hr.foi.core.DatabaseDataChangedListener;
import hr.foi.database.DatabaseWorker;
import hr.foi.model.PoslovniPartner;
import hr.foi.utils.MessageDialogUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.regex.Matcher;

import static hr.foi.utils.ApplicationUtils.VALID_EMAIL_ADDRESS_REGEX;
import static hr.foi.utils.ApplicationUtils.isInteger;

public class PoslovniPartnerEditController {

    @FXML
    private TextField txtOib;
    @FXML
    private TextField txtNazivPravneOsobe;
    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtAdresa;
    @FXML
    private TextField txtTelefon;
    @FXML
    private DatePicker dateUnos;

    private DatabaseDataChangedListener databaseDataChangedListener;
    private Stage stage;
    private boolean isEdit;

    public void setDatabaseDataChangedListener(DatabaseDataChangedListener databaseDataChangedListener) {
        this.databaseDataChangedListener = databaseDataChangedListener;
    }

    public void setScene(Stage stage) {
        this.stage = stage;
    }

    public void setEditPoslovniPartner(PoslovniPartner poslovniPartner) {
        isEdit = true;

        txtOib.setText(poslovniPartner.getOib());
        txtOib.setDisable(true);
        txtNazivPravneOsobe.setText(poslovniPartner.getNazivPravneOsobe());
        txtTelefon.setText(poslovniPartner.getTelefon());
        txtAdresa.setText(poslovniPartner.getAdresa());
        txtEmail.setText(poslovniPartner.getEmail());
        dateUnos.setValue(poslovniPartner.getVrijemeUnosa().toLocalDate());
    }

    public void buttonPohraniClicked() {
        if (isFormValid()) {
            PoslovniPartner poslovniPartner = new PoslovniPartner();

            poslovniPartner.setOib(txtOib.getText());
            poslovniPartner.setNazivPravneOsobe(txtNazivPravneOsobe.getText());
            poslovniPartner.setAdresa(txtAdresa.getText());
            poslovniPartner.setEmail(txtEmail.getText());
            poslovniPartner.setTelefon(txtTelefon.getText());
            poslovniPartner.setVrijemeUnosa(dateUnos.getValue().atStartOfDay());

            DatabaseWorker databaseWorker = DatabaseWorker.getInstance();

            try {
                if (isEdit) {
                    databaseWorker.saveExistingPoslovniPartner(poslovniPartner);
                } else {
                    databaseWorker.saveNewPoslovniPartner(poslovniPartner);
                }

                stage.close();
                databaseDataChangedListener.onDataChanged();
            } catch (SQLException e) {
                MessageDialogUtils.showMessage(Alert.AlertType.ERROR, "Greška",
                        "Došlo je do pogreške prilikom pohrane podataka.", "Razlog greške: " + e.getMessage());
            }
        }
    }

    private boolean isFormValid() {
        boolean isValid = true;

        Border errorBorder = new Border(new BorderStroke(Color.RED,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT));

        if (txtOib.getText().isEmpty() || txtOib.getText().length() != 11) {
            isValid = false;
            txtOib.setBorder(errorBorder);
        } else {
            txtOib.setBorder(null);
        }

        if (txtNazivPravneOsobe.getText().isEmpty() || txtNazivPravneOsobe.getText().length() < 3) {
            isValid = false;
            txtNazivPravneOsobe.setBorder(errorBorder);
        } else {
            txtNazivPravneOsobe.setBorder(null);
        }

        if (txtEmail.getText().isEmpty() || !isMailValid(txtEmail.getText())) {
            isValid = false;
            txtEmail.setBorder(errorBorder);
        } else {
            txtEmail.setBorder(null);
        }

        if (txtAdresa.getText().isEmpty() || txtAdresa.getText().length() < 5) {
            isValid = false;
            txtAdresa.setBorder(errorBorder);
        } else {
            txtAdresa.setBorder(null);
        }

        if (txtTelefon.getText().isEmpty() || !isInteger(txtTelefon.getText())) {
            isValid = false;
            txtTelefon.setBorder(errorBorder);
        } else {
            txtTelefon.setBorder(null);
        }

        if (dateUnos.getValue() == null) {
            isValid = false;
            dateUnos.setBorder(errorBorder);
        } else {
            dateUnos.setBorder(null);
        }

        return isValid;
    }

    private boolean isMailValid(String email) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(email);
        return matcher.find();
    }

}
