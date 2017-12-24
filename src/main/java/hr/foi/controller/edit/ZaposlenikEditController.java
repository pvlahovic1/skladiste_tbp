package hr.foi.controller.edit;

import hr.foi.core.DatabaseDataChangedListener;
import hr.foi.database.DatabaseWorker;
import hr.foi.model.Zaposlenik;
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

public class ZaposlenikEditController {

    @FXML
    private TextField txtOib;
    @FXML
    private TextField txtIme;
    @FXML
    private TextField txtPrezime;
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

    public void setEditZaposelnik(Zaposlenik zaposelnik) {
        isEdit = true;

        txtOib.setText(zaposelnik.getOib());
        txtOib.setDisable(true);
        txtIme.setText(zaposelnik.getIme());
        txtPrezime.setText(zaposelnik.getPrezime());
        txtTelefon.setText(zaposelnik.getTelefon());
        txtAdresa.setText(zaposelnik.getAdresa());
        txtEmail.setText(zaposelnik.getEmai());
        dateUnos.setValue(zaposelnik.getVrijemeUnosa().toLocalDate());
    }

    public void buttonPohraniClicked() {
        if (isFormValid()) {
            Zaposlenik zaposlenik = new Zaposlenik();

            zaposlenik.setOib(txtOib.getText());
            zaposlenik.setIme(txtIme.getText());
            zaposlenik.setPrezime(txtPrezime.getText());
            zaposlenik.setAdresa(txtAdresa.getText());
            zaposlenik.setEmai(txtEmail.getText());
            zaposlenik.setTelefon(txtTelefon.getText());
            zaposlenik.setVrijemeUnosa(dateUnos.getValue().atStartOfDay());

            DatabaseWorker databaseWorker = DatabaseWorker.getInstance();

            try {
                if (isEdit) {
                    databaseWorker.saveExistingZaposelnik(zaposlenik);
                } else {
                    databaseWorker.saveNewZaposlenik(zaposlenik);
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

        if (txtIme.getText().isEmpty() || txtIme.getText().length() < 3) {
            isValid = false;
            txtIme.setBorder(errorBorder);
        } else {
            txtIme.setBorder(null);
        }

        if (txtPrezime.getText().isEmpty() || txtPrezime.getText().length() < 3) {
            isValid = false;
            txtPrezime.setBorder(errorBorder);
        } else {
            txtPrezime.setBorder(null);
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

        if (txtTelefon.getText().isEmpty() || !isNumber(txtTelefon.getText())) {
            isValid = false;
            txtTelefon.setBorder(errorBorder);
        } else {
            txtTelefon.setBorder(null);
        }

        if (txtPrezime.getText().isEmpty() || txtPrezime.getText().length() < 3) {
            isValid = false;
            txtPrezime.setBorder(errorBorder);
        } else {
            txtPrezime.setBorder(null);
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

    private boolean isNumber(String number) {
        return number.matches("\\d+");
    }

}
