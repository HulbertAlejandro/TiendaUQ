package co.edu.uniquindio.tiendaUQ.controladores;

import co.edu.uniquindio.tiendaUQ.modelo.Tienda;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class InventoryControlador {

    private final Tienda tienda = Tienda.getInstance();
    @FXML
    private TextField txtCodigo, txtNombre, txtPrecio, txtCantidad;
    @FXML
    private Button btnRegresar;

    public void regresarLogin(ActionEvent event) {
        Object evt = event.getSource();
        if (evt.equals(btnRegresar)) {
            tienda.loadStage("/ventanas/home.fxml", event);
        }
    }
}
