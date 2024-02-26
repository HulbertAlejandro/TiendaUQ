package co.edu.uniquindio.tiendaUQ.controladores;

import co.edu.uniquindio.tiendaUQ.modelo.Tienda;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class HomeControlador {

    private final Tienda tienda = Tienda.getInstance();
    @FXML
    Button btnInventario, bttShopping;

    public void inventario(ActionEvent event){
        Object evt = event.getSource();
        if(evt.equals(btnInventario)){
            tienda.loadStage("/ventanas/inventoryPage.fxml", event);
        }
    }
    public void shoppingPage(ActionEvent event){
        Object evt = event.getSource();
        if(evt.equals(bttShopping)){
            tienda.loadStage("/ventanas/shopping.fxml", event);
        }
    }
}
