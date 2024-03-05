package co.edu.uniquindio.tiendaUQ.controladores;

import co.edu.uniquindio.tiendaUQ.modelo.Tienda;
import co.edu.uniquindio.tiendaUQ.modelo.Venta;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class SalesHistoryController implements Initializable {
    private final Tienda tienda = Tienda.getInstance();
    @FXML
    private TableColumn<Venta,String> dateTable, nameTable, priceTable;
    @FXML
    private TableView<Venta> table;
    private void cargarTabla() {
        ArrayList<Venta> historicoVentas = tienda.enviarVentas().convertArrayList();
        ObservableList<Venta> listaProductos = FXCollections.observableArrayList(historicoVentas);
        nameTable.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCliente().getNombre()));
        dateTable.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFecha().toString()));
        priceTable.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTotal()+""));
        table.setItems(listaProductos);
    }
    @FXML
    void back(ActionEvent event) {
        tienda.inicializar();
        tienda.loadStage("/ventanas/adminPage.fxml",event);
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cargarTabla();
    }

}
