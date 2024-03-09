package co.edu.uniquindio.tiendaUQ.controladores;

import co.edu.uniquindio.tiendaUQ.modelo.Producto;
import co.edu.uniquindio.tiendaUQ.modelo.Tienda;
import co.edu.uniquindio.tiendaUQ.modelo.Venta;
import co.edu.uniquindio.tiendaUQ.utils.ArchivoUtils;
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

import java.net.URL;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.ResourceBundle;

public class PaymentControlador implements Initializable {
    @FXML
    private Button bttBack;
    @FXML
    private Button bttPay;
    @FXML
    private Label codeEdit,total;
    @FXML
    private TableColumn<Producto,String> codeTable, nameTable, priceTable, quantityTable;
    @FXML
    private TableView<Producto> table;
    private final Tienda tienda = Tienda.getInstance();
    private HashMap<String, Producto> carrito;
    private float totalVenta = 0;
    @FXML
    void back(ActionEvent event) {
        tienda.loadStage("/ventanas/shopping.fxml",event);
        tienda.recibirCarrito(carrito);
    }
    /**
     * Procesa el pago de la venta actual y realiza las acciones correspondientes.
     * Crea una nueva venta con los detalles del cliente, el código, la fecha y el total de la venta.
     * Almacena la venta en la tienda.
     * Genera y muestra el recibo de la venta.
     * Limpia el carrito de compras.
     * Carga la página de recibo.
     * Serializa los datos de la tienda para guardarlos.
     * @param event El evento de acción que desencadena el pago.
     */
    @FXML
    void pay(ActionEvent event) {
        Venta venta = Venta.builder()
                .cliente(tienda.enviarCliente())
                .codigo(tienda.vincularCodigo())
                .fecha(LocalDate.now())
                .total((double) totalVenta).build();
        System.out.println(venta.getCliente().getNombre());
        tienda.almacenarVenta(venta);
        tienda.receiptPage(venta, carrito);
        tienda.limpiarCarritoPay();
        tienda.loadStage("/ventanas/receipt.fxml",event);
        tienda.serializar();
    }
    public void recibirCarrito(){
        carrito = (HashMap<String, Producto>) tienda.enviarCarrito();
    }
    /**
     * Calcula el total de la venta sumando los precios de los productos en el carrito.
     * Actualiza el campo de texto con el total de la venta.
     */
    public void realizarCuenta(){
        for (Producto producto : carrito.values()){
            totalVenta += producto.getPrecio()*producto.getCantidad();
        }
        total.setText(String.valueOf(totalVenta));
    }
    /**
     * Carga los datos del carrito en una tabla en la interfaz gráfica.
     * Utiliza una lista observable de productos para mostrar los datos dinámicamente.
     * Asigna los valores de nombre, código, precio y cantidad a las columnas de la tabla.
     * @see Producto
     */
    private void cargarTabla() {
        ObservableList<Producto> listaProductos = FXCollections.observableArrayList(carrito.values());
        nameTable.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
        codeTable.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCodigo()));
        priceTable.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPrecio()+""));
        quantityTable.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCantidad()+""));
        table.setItems(listaProductos);
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        recibirCarrito();
        cargarTabla();
        realizarCuenta();
    }
}
