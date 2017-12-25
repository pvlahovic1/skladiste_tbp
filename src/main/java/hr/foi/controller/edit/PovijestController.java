package hr.foi.controller.edit;

import hr.foi.database.DatabaseWorker;
import hr.foi.model.Povijest;
import hr.foi.utils.MessageDialogUtils;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ResourceBundle;

public class PovijestController implements Initializable {

    @FXML private TableView<Povijest> povijestTableView;
    @FXML private TableColumn<Povijest, LocalDateTime> collDatum;
    @FXML private TableColumn<Povijest, Integer> collKolicina;
    @FXML private LineChart<String, Integer> povijestLineChart;

    private int idArtikla;
    private Stage stage;
    private List<Povijest> povijesti;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        collDatum.setCellValueFactory(new PropertyValueFactory<Povijest, LocalDateTime>("vremenskaOznaka"));
        collKolicina.setCellValueFactory(new PropertyValueFactory<Povijest, Integer>("kolicina"));
    }

    public void setIdArtikla(int idArtikla) {
        this.idArtikla = idArtikla;
        refreshData();
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    private void refreshData() {
        DatabaseWorker databaseWorker = DatabaseWorker.getInstance();

        try {
            povijesti = databaseWorker.getPovijestByArtiklId(idArtikla);
            povijestTableView.setItems(FXCollections.observableList(povijesti));
            refreshChart();

        } catch (SQLException e) {
            MessageDialogUtils.showMessage(Alert.AlertType.ERROR, "Greška",
                    "Došlo je do pogreške prilikom pohrane podataka.", "Razlog greške: " + e.getMessage());
            stage.close();
        }
    }

    private void refreshChart() {
        XYChart.Series<String, Integer> series = new XYChart.Series<>();

        series.setName("Zalihe artikla");

        for (Povijest p: povijesti) {
            series.getData().add(new XYChart.Data<>(p.getVremenskaOznaka().toString(), p.getKolicina()));
        }

        povijestLineChart.getData().add(series);
    }
}
