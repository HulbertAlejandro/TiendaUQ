package co.edu.uniquindio.tiendaUQ.controladores;

import co.edu.uniquindio.tiendaUQ.modelo.Producto;
import co.edu.uniquindio.tiendaUQ.modelo.Tienda;
import co.edu.uniquindio.tiendaUQ.modelo.Venta;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.io.Serializable;
import java.net.URL;
import java.util.ResourceBundle;

public class ReceiptControlador implements Initializable {
    private final Tienda tienda = Tienda.getInstance();
    @FXML
    private Button bttBack;
    @FXML
    private Label  address, date,  id, name, receipt;
    @FXML
    private TableColumn<Producto, String> nameTable,priceTable,quantityTable,codeTable;
    @FXML
    private TableView<Producto> table;
    private void cargarTabla() {
        ObservableList<Producto> listaProductos = FXCollections.observableArrayList(tienda.carritoVenta().values());
        nameTable.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
        codeTable.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCodigo()));
        priceTable.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPrecio()+""));
        quantityTable.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCantidad()+""));
        table.setItems(listaProductos);
    }
    private void cargarDatos() {
        Venta venta = tienda.enviarVenta();
        address.setText(venta.getCliente().getDireccion());
        date.setText(venta.getFecha().toString());
        id.setText(venta.getCliente().getNumeroIdentificacion());
        name.setText(venta.getCliente().getNombre());
        receipt.setText(venta.getCodigo());
    }
    @FXML
    void back(ActionEvent event) {
        tienda.inicializar();
        tienda.loadStage("/ventanas/login.fxml",event);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cargarTabla();
        cargarDatos();
    }
}
