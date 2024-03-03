package co.edu.uniquindio.tiendaUQ.controladores;

import co.edu.uniquindio.tiendaUQ.modelo.Tienda;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class LoginControlador {
    private final Tienda tienda = Tienda.getInstance();
    @FXML
    private Button buttonLogin, buttonRegister;
    @FXML
    private TextField user, password;
    public void verify(ActionEvent actionEvent) {
        if(tienda.verifyUser(user.getText(),password.getText())){
            tienda.almacenarCliente(tienda.guardarCliente(user.getText(),password.getText()));
            tienda.loadStage("/ventanas/home.fxml",actionEvent);
        }else{
            tienda.mostrarMensaje(Alert.AlertType.ERROR, "Los datos ingresados son erroneos");
        }
    }

    public void registrarUsuario(ActionEvent event){
        Object evt = event.getSource();
        if(evt.equals(buttonRegister)){
            tienda.loadStage("/ventanas/register.fxml", event);
        }
    }
}
