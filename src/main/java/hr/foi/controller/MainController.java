package hr.foi.controller;

import hr.foi.Main;
import javafx.fxml.FXMLLoader;

import java.io.IOException;

public class MainController {

    public void onSkladisteButtonClicked() {
        applyView("/SkladisteView.fxml");
    }

    public void onMjereButtonClicked() {
        applyView("/MjereView.fxml");
    }

    public void onDokumentiButtonClicked() {
        applyView("/DokumentiView.fxml");
    }

    public void onPrikazTipovaButtonClicked() {
        applyView("/TipoviDokumentaView.fxml");
    }

    public void onZaposleniciButtonClicked() {
        applyView("/ZaposleniciView.fxml");
    }

    public void onPoslovniPartnerButtonClicked() {
        applyView("/PoslovniPartneriView.fxml");
    }

    private void applyView(String viewName) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(viewName));

        try {
            Main.setCenterPane(fxmlLoader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
