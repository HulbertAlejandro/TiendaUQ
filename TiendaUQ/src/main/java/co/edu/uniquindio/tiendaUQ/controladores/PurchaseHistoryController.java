package co.edu.uniquindio.tiendaUQ.controladores;

import co.edu.uniquindio.tiendaUQ.modelo.Cliente;
import co.edu.uniquindio.tiendaUQ.modelo.Tienda;
import co.edu.uniquindio.tiendaUQ.modelo.Venta;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class PurchaseHistoryController implements Initializable {
    private final Tienda tienda = Tienda.getInstance();
    private ArrayList<Venta> historicoCompras = new ArrayList<>();
    @FXML
    private TableColumn<Venta,String> dateTable, nameTable, priceTable;
    @FXML
    private TableView<Venta> table;
    @FXML
    private Label name,address,id;
    
    /*
    Metodo que carga la tabla del historico de compras y setea en la tabla por sus valores
    */
    
    private void cargarTabla() {
        ObservableList<Venta> listaProductos = FXCollections.observableArrayList(historicoCompras);
        nameTable.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCliente().getNombre()));
        dateTable.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFecha().toString()));
        priceTable.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTotal()+""));
        table.setItems(listaProductos);
    }
    
    /*
    Metodo que retorna a la ventana de homeClient
    */
    
    @FXML
    void back(ActionEvent event) {
        tienda.inicializar();
        tienda.loadStage("/ventanas/homeClient.fxml",event);
    }
    
    /*
    Metodo que inicializa los datos en la ventana de historial de ventas y setea sus valores
    tambien obtiene el arraylis del historico de ventas y las imprime filtrando por cliente
    */
    
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Cliente cliente = tienda.enviarCliente();
        name.setText(cliente.getNombre());
        address.setText(cliente.getDireccion());
        id.setText(cliente.getNumeroIdentificacion());

        ArrayList<Venta> historico = new ArrayList<>();
        System.out.println("Lista de ventas: ");
        tienda.enviarVentas().printList();
        historico = tienda.enviarVentas().convertArrayList();
        historicoCompras = tienda.filtrarCliente(historico);

        cargarTabla();
    }

}
