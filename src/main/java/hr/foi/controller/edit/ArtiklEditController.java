package hr.foi.controller.edit;

import hr.foi.core.DatabaseDataChangedListener;
import hr.foi.database.DatabaseWorker;
import hr.foi.model.Artikl;
import hr.foi.model.Mjera;
import hr.foi.utils.MessageDialogUtils;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import static hr.foi.utils.ApplicationUtils.isIntegerAndBiggerThen;
import static hr.foi.utils.ApplicationUtils.isNumberAndBiggerThen;

public class ArtiklEditController implements Initializable {

    @FXML private TextField nazivTextArea;
    @FXML private TextField kolicinaNaSkladistuTextArea;
    @FXML private TextField minimalneZaliheTextArea;
    @FXML private TextField jedinicnaCijenaTextArea;
    @FXML private TextField godisnjaPotraznjaTextArea;
    @FXML private TextField troskoviSkladistenjaTextArea;
    @FXML private TextField troskoviNabaveTextArea;
    @FXML private ComboBox<Mjera> mjeraComboBox;
    private Stage stage;
    private boolean isEditing;
    private Artikl editingArtikl;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        DatabaseWorker databaseWorker = DatabaseWorker.getInstance();

        try {
            List<Mjera> mjere = databaseWorker.getAllMjera();

            if (!mjere.isEmpty()) {
                mjeraComboBox.setItems(FXCollections.observableList(mjere));
                mjeraComboBox.getSelectionModel().select(0);
            } else {
                MessageDialogUtils.showMessage(Alert.AlertType.INFORMATION, "Preporuka", null,
                        "Prije dodavanja novog artikla morate dodati moguče mjere.");
                stage.close();
            }
        } catch (SQLException e) {
            MessageDialogUtils.showMessage(Alert.AlertType.ERROR, "Greška", "Došlo je do greške u radu s bazom podataka.",
                    "Razlog: " + e.getMessage());
        }
    }

    public void setEditingArtikl(Artikl editingArtikl) {
        isEditing = true;
        this.editingArtikl = editingArtikl;

        nazivTextArea.setText(editingArtikl.getNaziv());
        kolicinaNaSkladistuTextArea.setText(String.valueOf(editingArtikl.getKolicinaNaSkladistu()));
        minimalneZaliheTextArea.setText(String.valueOf(editingArtikl.getMinimalneZalihe()));
        jedinicnaCijenaTextArea.setText(String.valueOf(editingArtikl.getJedinicnaCijena()));
        godisnjaPotraznjaTextArea.setText(String.valueOf(editingArtikl.getGodisnjaPotraznja()));
        troskoviSkladistenjaTextArea.setText(String.valueOf(editingArtikl.getTroskoviSkladistenja()));
        troskoviNabaveTextArea.setText(String.valueOf(editingArtikl.getTroskoviNabave()));

        List<Mjera> mjere = mjeraComboBox.getItems().stream()
                .filter(e -> e.getId() == editingArtikl.getIdMjere()).collect(Collectors.toList());

        if (!mjere.isEmpty()) {
            mjeraComboBox.getSelectionModel().select(mjere.get(0));
        }
    }

    private DatabaseDataChangedListener databaseDataChangedListener;

    public DatabaseDataChangedListener getDatabaseDataChangedListener() {
        return databaseDataChangedListener;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setDatabaseDataChangedListener(DatabaseDataChangedListener databaseDataChangedListener) {
        this.databaseDataChangedListener = databaseDataChangedListener;
    }

    public void onPohraniButtonClicked() {
        if (isFormValid()) {
            DatabaseWorker databaseWorker = DatabaseWorker.getInstance();

            try {
                if (isEditing) {
                    editingArtikl.setNaziv(nazivTextArea.getText());
                    editingArtikl.setKolicinaNaSkladistu(Integer.valueOf(kolicinaNaSkladistuTextArea.getText()));
                    editingArtikl.setMinimalneZalihe(Integer.valueOf(minimalneZaliheTextArea.getText()));
                    editingArtikl.setJedinicnaCijena(Double.valueOf(jedinicnaCijenaTextArea.getText()));
                    editingArtikl.setTroskoviSkladistenja(Double.valueOf(troskoviSkladistenjaTextArea.getText()));
                    editingArtikl.setTroskoviNabave(Double.valueOf(troskoviNabaveTextArea.getText()));
                    editingArtikl.setGodisnjaPotraznja(Integer.valueOf(godisnjaPotraznjaTextArea.getText()));
                    editingArtikl.setIdMjere(mjeraComboBox.getValue().getId());

                    databaseWorker.saveExistingArtikl(editingArtikl);
                } else {
                    Artikl artikl = new Artikl();
                    artikl.setNaziv(nazivTextArea.getText());
                    artikl.setKolicinaNaSkladistu(Integer.valueOf(kolicinaNaSkladistuTextArea.getText()));
                    artikl.setMinimalneZalihe(Integer.valueOf(minimalneZaliheTextArea.getText()));
                    artikl.setJedinicnaCijena(Double.valueOf(jedinicnaCijenaTextArea.getText()));
                    artikl.setTroskoviSkladistenja(Double.valueOf(troskoviSkladistenjaTextArea.getText()));
                    artikl.setTroskoviNabave(Double.valueOf(troskoviNabaveTextArea.getText()));
                    artikl.setGodisnjaPotraznja(Integer.valueOf(godisnjaPotraznjaTextArea.getText()));
                    artikl.setIdMjere(mjeraComboBox.getValue().getId());

                    databaseWorker.saveNewArtikl(artikl);
                }

                databaseDataChangedListener.onDataChanged();
                stage.close();
            } catch (SQLException e) {
                MessageDialogUtils.showMessage(Alert.AlertType.ERROR, "Greška", "Došlo je do greške u radu s bazom podataka.",
                        "Razlog: " + e.getMessage());
            }
        }
    }

    private boolean isFormValid() {
        boolean isValid = true;

        Border errorBorder = new Border(new BorderStroke(Color.RED,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT));

        if (nazivTextArea.getText().isEmpty() || nazivTextArea.getText().length() < 3) {
            isValid = false;
            nazivTextArea.setBorder(errorBorder);
        } else {
            nazivTextArea.setBorder(null);
        }

        if (kolicinaNaSkladistuTextArea.getText().isEmpty()
                || !isIntegerAndBiggerThen(kolicinaNaSkladistuTextArea.getText(), 0)) {
            isValid = false;
            kolicinaNaSkladistuTextArea.setBorder(errorBorder);
        } else {
            kolicinaNaSkladistuTextArea.setBorder(null);
        }

        if (minimalneZaliheTextArea.getText().isEmpty()
                || !isIntegerAndBiggerThen(minimalneZaliheTextArea.getText(), 0)) {
            isValid = false;
            minimalneZaliheTextArea.setBorder(errorBorder);
        } else {
            minimalneZaliheTextArea.setBorder(null);
        }

        if (godisnjaPotraznjaTextArea.getText().isEmpty()
                || !isIntegerAndBiggerThen(godisnjaPotraznjaTextArea.getText(), 0)) {
            isValid = false;
            godisnjaPotraznjaTextArea.setBorder(errorBorder);
        } else {
            godisnjaPotraznjaTextArea.setBorder(null);
        }

        if (troskoviSkladistenjaTextArea.getText().isEmpty()
                || !isNumberAndBiggerThen(troskoviSkladistenjaTextArea.getText(), 0)) {
            isValid = false;
            troskoviSkladistenjaTextArea.setBorder(errorBorder);
        } else {
            troskoviSkladistenjaTextArea.setBorder(null);
        }

        if (troskoviNabaveTextArea.getText().isEmpty()
                || !isNumberAndBiggerThen(troskoviNabaveTextArea.getText(), 0)) {
            isValid = false;
            troskoviNabaveTextArea.setBorder(errorBorder);
        } else {
            troskoviNabaveTextArea.setBorder(null);
        }

        if (jedinicnaCijenaTextArea.getText().isEmpty()
                || !isNumberAndBiggerThen(jedinicnaCijenaTextArea.getText(), 0)) {
            isValid = false;
            jedinicnaCijenaTextArea.setBorder(errorBorder);
        } else {
            jedinicnaCijenaTextArea.setBorder(null);
        }

        return isValid;
    }
}
