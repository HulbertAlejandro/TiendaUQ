package co.edu.uniquindio.tiendaUQ.controladores;

import co.edu.uniquindio.tiendaUQ.exceptions.CampoObligatorioException;
import co.edu.uniquindio.tiendaUQ.exceptions.CampoRepetido;
import co.edu.uniquindio.tiendaUQ.exceptions.CampoVacioException;
import co.edu.uniquindio.tiendaUQ.modelo.Producto;
import co.edu.uniquindio.tiendaUQ.modelo.Tienda;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import javax.swing.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class ShoppingControlador implements Initializable {
    
    /*
    Definir los elementos de la ventana
    */
    
    private final Tienda tienda = Tienda.getInstance();
    public Map<String, Producto> productosCarrito = new HashMap<>();
    @FXML
    public Label codeEdit;
    @FXML
    public Button bttAdd, bttRemove, bttBack, bttPay;
    @FXML
    public TableColumn<Producto, String> nameTable,codeTable, quantityTable,priceTable,nameTable1,codeTable1,priceTable1,quantityTable1;
    @FXML
    public TableView<Producto> table, table1;

    /*
    Metodo de añadir un producto al carro
    1. Verifica que sea un producto correcto
    2. Si es valido, solicita la cantidad de producto y valida que sea una cantidad posible
    3. Notifica que el producto se cargó con éxito en caso de pasar los criterios evaluados 
    */
    
    public void add(ActionEvent actionEvent) {
        if (table.getSelectionModel().getSelectedIndex() == -1) {
            tienda.mostrarMensaje(Alert.AlertType.ERROR, "Se intento seleccionar un producto erroneo");
        } else {
            Producto productoSeleccionado = table.getSelectionModel().getSelectedItem();
            System.out.println("Producto seleccionado: "+ productoSeleccionado.getNombre() +" " + productoSeleccionado.getCantidad()+ " " + productoSeleccionado.getCodigo());
            int cantidad = Integer.parseInt(JOptionPane.showInputDialog("Ingrese la cantidad de su producto"));
            if(cantidad<= 0){
                tienda.mostrarMensaje(Alert.AlertType.ERROR, "Ingrese una cantidad valida");
            }else{
                if(tienda.verifyInventory(productoSeleccionado, cantidad)){
                    tienda.mostrarMensaje(Alert.AlertType.CONFIRMATION, "Se cargo el producto al carrito");
                    productoSeleccionado.setCantidad(cantidad);
                    productosCarrito.put(productoSeleccionado.getCodigo(), productoSeleccionado);
                    cargarTabla();
                    cargarTablaCarrito();
                }else{
                    tienda.mostrarMensaje(Alert.AlertType.ERROR,"El producto no cuenta con las existencias necesaria");
                }
            }
        }
    }
    
        /*
        Metodo de pagar los producto del carro
        1. Verifica que los productos del carro existan
        2. Si es valido, solicita la cantidad de producto y valida que sea una cantidad posible
        3. Notifica que el producto se cargó con éxito en caso de pasar los criterios evaluados 
        */
        
    public void pay(ActionEvent actionEvent) {
        if(productosCarrito.isEmpty()){
            tienda.mostrarMensaje(Alert.AlertType.ERROR,"No se han ingresado productos al carrito");
        }else{
            tienda.recibirCarrito(productosCarrito);
            tienda.loadStage("/ventanas/paymentPage.fxml", actionEvent);
        }
    }
         /*
          Metodo de remover los producto del carro
          1. Verifica que los productos del carro existan
          2. Si es valido, recibe los productos del carro y pasa a la página de pagos
          3. Notifica que el carro está vacío en caso de no ser válido
          */

    public void remove(ActionEvent actionEvent) {
        if (table1.getSelectionModel().getSelectedIndex() == -1) {
            tienda.mostrarMensaje(Alert.AlertType.ERROR, "Se intento seleccionar un producto erroneo");
        } else {
            Producto productoSeleccionado = table1.getSelectionModel().getSelectedItem();
            eliminarProducto(productoSeleccionado, productoSeleccionado.getCodigo());
            tienda.mostrarMensaje(Alert.AlertType.CONFIRMATION, "Producto eliminado correctamente");
            tienda.loadStage("/ventanas/shopping.fxml",actionEvent);
        }
    }

    /*
    Metodo que elimina un producto seleccionado
    1. El metodo actualiza el inventario con el producto seleccionado y luego
    2. elimina del carrito el producto seleccionado
    3. finalmente actualiza los datos del carrito
    */

    private void eliminarProducto(Producto productoSeleccionado, String codigo) {
        tienda.updateInventory(productoSeleccionado);
        productosCarrito.remove(codigo,productoSeleccionado);
        tienda.recibirCarrito(productosCarrito);
    }

    /*
    Metodo que carga la tabla
    1. Obtiene la collections observable de los productos
    2. Setea los datos en las columnas dependiendo de su tipo y los setea
    */

    private void cargarTabla() {
        ObservableList<Producto> listaProductos = FXCollections.observableArrayList(tienda.enviarProductos().values());
        nameTable.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
        codeTable.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCodigo()));
        priceTable.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPrecio()+""));
        quantityTable.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCantidad()+""));
        table.setItems(listaProductos);
    }

    /*
    Metodo que carga la tabla del carrito
    1. Este metodo carga el carrito de compras como lista observable
    2. setea los datos en su columna por cada tipo
    */

    private void cargarTablaCarrito() {
        ObservableList<Producto> listaCarrito = FXCollections.observableArrayList(productosCarrito.values());
        nameTable1.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
        codeTable1.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCodigo()));
        priceTable1.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPrecio()+""));
        quantityTable1.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCantidad()+""));
        table1.setItems(listaCarrito);
    }

    /*
    Metodo que retorna a la ventana anterior
    1. Este metodo se vincula al botón back y hace que retorne a la ventana de inicio de los
    clientes
    2. este metodo tambien limpia el carrito
    */

    public void back(ActionEvent actionEvent) {
        tienda.limpiarCarrito((HashMap<String, Producto>) productosCarrito);
        Object evt = actionEvent.getSource();
        if (evt.equals(bttBack)) {
            tienda.loadStage("/ventanas/homeClient.fxml", actionEvent);
        }
    }
    /*
    Metodo que inicializa las tabla de shopping al abrir la ventana
    */
    
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(!tienda.enviarCarrito().isEmpty()){
            productosCarrito = tienda.enviarCarrito();
            cargarTablaCarrito();
        }
       cargarTabla();
    }
}
