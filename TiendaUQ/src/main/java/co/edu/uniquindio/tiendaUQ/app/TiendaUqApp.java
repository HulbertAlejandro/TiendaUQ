package co.edu.uniquindio.tiendaUQ.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class TiendaUqApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        FXMLLoader loader = new FXMLLoader(TiendaUqApp.class.getResource("/ventanas/login.fxml"));
        Parent parent = loader.load();

        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.setTitle("UQ Online Store");
        stage.show();
    }

    public static void main(String[] args) {
        launch(TiendaUqApp.class, args);
    }
}
