package co.edu.uniquindio.tiendaUQ.controladores;

import co.edu.uniquindio.tiendaUQ.exceptions.CampoObligatorioException;
import co.edu.uniquindio.tiendaUQ.exceptions.CampoRepetido;
import co.edu.uniquindio.tiendaUQ.exceptions.CampoVacioException;
import co.edu.uniquindio.tiendaUQ.modelo.Cliente;
import co.edu.uniquindio.tiendaUQ.modelo.Tienda;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class RegisterControlador {

    private final Tienda tienda = Tienda.getInstance();
    @FXML
    private TextField txtIdentificacion, txtNombre, txtDireccion, txtUsuario, txtContrasena;
    @FXML
    private Button btnGuardar, btnRegresar;

    /*
    Metodo para registrar a un cliente
    1. Tiene excepciones en caso de no llenar los datos
    2. Crea un cliente e invoca el registro desde tienda
    3. obtiene el texto de cada txt y lo setea a su respectivo campo
    4. notifica en caso de que esté bien o mal
    */

    public void registrarCliente(ActionEvent actionEvent) throws CampoObligatorioException,CampoVacioException{
        try {
            Cliente cliente = tienda.registrarCliente(
                    txtIdentificacion.getText(),
                    txtNombre.getText(),
                    txtDireccion.getText(),
                    txtUsuario.getText(),
                    txtContrasena.getText()
            );
            tienda.mostrarMensaje(Alert.AlertType.INFORMATION, "Se ha registrado correctamente el cliente: " + cliente.getNombre());
        } catch (CampoRepetido e) {
            tienda.mostrarMensaje(Alert.AlertType.ERROR,e.getMessage());
        }
    }

    /*
    Metodo que regresa a la ventana login al darle al btnRegresar
    */

    public void regresarLogin(ActionEvent event) {
        Object evt = event.getSource();
        if (evt.equals(btnRegresar)) {
            tienda.loadStage("/ventanas/login.fxml", event);
        }
    }
}
