package co.edu.uniquindio.tiendaUQ.controladores;

import co.edu.uniquindio.tiendaUQ.modelo.Tienda;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class InicioControlador {
    private final Tienda tienda = Tienda.getInstance();
    @FXML
    private Button button;
    @FXML
    private TextField user, password;
    public void verify(ActionEvent actionEvent) {
        if(tienda.verifyUser(user.getText(),password.getText())){
            tienda.loadStage("TiendaUQ/src/main/resources/ventanas/homePage.fxml",actionEvent,"Se ingresa a la pagina principal");
        }else{
            tienda.mostrarMensaje(Alert.AlertType.ERROR, "Los datos ingresados son erroneos");
        }
    }

}
