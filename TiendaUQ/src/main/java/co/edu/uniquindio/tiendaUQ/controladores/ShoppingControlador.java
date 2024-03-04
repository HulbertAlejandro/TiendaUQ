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
    public void pay(ActionEvent actionEvent) {
        if(productosCarrito.isEmpty()){
            tienda.mostrarMensaje(Alert.AlertType.ERROR,"No se han ingresado productos al carrito");
        }else{
            tienda.recibirCarrito(productosCarrito);
            tienda.loadStage("/ventanas/paymentPage.fxml", actionEvent);
        }
    }
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
    private void eliminarProducto(Producto productoSeleccionado, String codigo) {
        tienda.updateInventory(productoSeleccionado);
        productosCarrito.remove(codigo,productoSeleccionado);
        tienda.recibirCarrito(productosCarrito);
    }
    private void cargarTabla() {
        ObservableList<Producto> listaProductos = FXCollections.observableArrayList(tienda.enviarProductos().values());
        nameTable.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
        codeTable.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCodigo()));
        priceTable.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPrecio()+""));
        quantityTable.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCantidad()+""));
        table.setItems(listaProductos);
    }
    private void cargarTablaCarrito() {
        ObservableList<Producto> listaCarrito = FXCollections.observableArrayList(productosCarrito.values());
        nameTable1.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
        codeTable1.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCodigo()));
        priceTable1.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPrecio()+""));
        quantityTable1.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCantidad()+""));
        table1.setItems(listaCarrito);
    }
    public void back(ActionEvent actionEvent) {
        tienda.limpiarCarrito((HashMap<String, Producto>) productosCarrito);
        Object evt = actionEvent.getSource();
        if (evt.equals(bttBack)) {
            tienda.loadStage("/ventanas/home.fxml", actionEvent);
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(!tienda.enviarCarrito().isEmpty()){
            productosCarrito = tienda.enviarCarrito();
            cargarTablaCarrito();
        }
       cargarTabla();
    }
}
