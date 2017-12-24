package hr.foi;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main extends Application {

    private Stage primaryStage;
    private static BorderPane root;

    public static void main(String... args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;

        try {

            Class.forName("org.postgresql.Driver");

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/MainView.fxml"));
            root = fxmlLoader.load();

            Scene scene = new Scene(root, 1024, 768);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Upravljanje skladištem");
            primaryStage.setMinWidth(1050);
            primaryStage.setMinHeight(850);

            primaryStage.setMaxWidth(1050);
            primaryStage.setMaxHeight(850);

            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setCenterPane(Pane pane) {
        root.setCenter(pane);
    }

    public static Pane getCenterPane() {
        return (Pane) root.getCenter();
    }

}
