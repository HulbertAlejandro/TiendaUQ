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
    private Button buttonRegister;
    @FXML
    private TextField user, password;
    /**
     * Verifica las credenciales del usuario cuando se realiza un evento de acción.
     * Si el usuario y la contraseña coinciden con "admin", carga la página de administrador.
     * Si las credenciales son válidas, carga la página de inicio del cliente y almacena el cliente.
     * Si las credenciales son inválidas, muestra un mensaje de error.
     * @param actionEvent El evento de acción que desencadena la verificación de las credenciales.
     */
    public void verify(ActionEvent actionEvent) {
        if(user.getText().equals("admin") && password.getText().equals("admin")){
            tienda.loadStage("/ventanas/adminPage.fxml", actionEvent);
        }else{
            if(tienda.verifyUser(user.getText(),password.getText())){
                tienda.almacenarCliente(tienda.guardarCliente(user.getText(),password.getText()));
                System.out.println(tienda.enviarCliente().getNombre());
                tienda.loadStage("/ventanas/homeClient.fxml",actionEvent);
            }else{
                tienda.mostrarMensaje(Alert.AlertType.ERROR, "Los datos ingresados son erroneos");
            }
        }
    }
    
    /**
    Metodo que avanza a la ventana de registro de usuarios
    */
    
    public void registrarUsuario(ActionEvent event){
        Object evt = event.getSource();
        if(evt.equals(buttonRegister)){
            tienda.loadStage("/ventanas/register.fxml", event);
        }
    }
}
