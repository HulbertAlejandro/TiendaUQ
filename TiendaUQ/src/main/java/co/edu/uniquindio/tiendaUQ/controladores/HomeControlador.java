package co.edu.uniquindio.tiendaUQ.controladores;

import co.edu.uniquindio.tiendaUQ.modelo.Tienda;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class HomeControlador {

    private final Tienda tienda = Tienda.getInstance();

    @FXML
    Button btnCrearProducto, btnInventario;

    public void crearProducto(ActionEvent event){
        Object evt = event.getSource();
        if(evt.equals(btnCrearProducto)){
            tienda.loadStage("/ventanas/createProduct.fxml", event);
        }
    }

    public void inventario(ActionEvent event){
        Object evt = event.getSource();
        if(evt.equals(btnInventario)){
            tienda.loadStage("/ventanas/inventory.fxml", event);
        }
    }
}
