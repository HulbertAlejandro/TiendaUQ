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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.logging.Level;

public class InventoryControlador implements Initializable {

    private final Tienda tienda = Tienda.getInstance();
    @FXML
    public TextField nameEdit, priceEdit, quantityEdit;
    @FXML
    public Label codeEdit;
    @FXML
    public Button bttEdit, bttRemove, bttBack, bttCreate;
    @FXML
    public Button bttSelect;
    @FXML
    public TextField nameCreate,codeCreate, priceCreate,quantityCreate;
    @FXML
    public TableColumn<Producto, String> nameTable,codeTable, quantityTable,priceTable;
    @FXML
    public TableView<Producto> table;
        public void create(ActionEvent actionEvent){
        try {
            tienda.registrarProducto(nameCreate.getText(),codeCreate.getText(),priceCreate.getText(),quantityCreate.getText());
            tienda.mostrarMensaje(Alert.AlertType.INFORMATION, "Se ha registrado correctamente el producto: " + nameCreate.getText());
            tienda.loadStage("/ventanas/inventoryPage.fxml",actionEvent);
        } catch (CampoObligatorioException | CampoVacioException | CampoRepetido e) {
            tienda.mostrarMensaje(Alert.AlertType.ERROR,e.getMessage());
        }
    }
    public void select(ActionEvent actionEvent) {
        if (table.getSelectionModel().getSelectedIndex() == -1) {
            tienda.mostrarMensaje(Alert.AlertType.ERROR, "Se intento seleccionar un producto erroneo");
        } else {
            tienda.mostrarMensaje(Alert.AlertType.CONFIRMATION, "Se selecciono un producto");
            Producto productoSeleccionado = table.getSelectionModel().getSelectedItem();
            nameEdit.setText(productoSeleccionado.getNombre());
            codeEdit.setText(productoSeleccionado.getCodigo());
            priceEdit.setText(productoSeleccionado.getPrecio()+"");
            quantityEdit.setText(productoSeleccionado.getCantidad()+"");
        }
    }
    public void edit(ActionEvent actionEvent) {
        try {
            tienda.editarProducto(nameEdit.getText(),priceEdit.getText(),quantityEdit.getText(),table.getSelectionModel().getSelectedItem());
            tienda.mostrarMensaje(Alert.AlertType.INFORMATION, "Se ha registrado correctamente el producto: " + nameCreate.getText());
            tienda.loadStage("/ventanas/inventoryPage.fxml",actionEvent);
        } catch (CampoObligatorioException | CampoVacioException | CampoRepetido e) {
            tienda.mostrarMensaje(Alert.AlertType.ERROR, e.getMessage());
        }
    }
    public void remove(ActionEvent actionEvent) {
        if (table.getSelectionModel().getSelectedIndex() == -1) {
            tienda.mostrarMensaje(Alert.AlertType.ERROR, "Se intento seleccionar un producto erroneo");
        } else {
            Producto productoSeleccionado = table.getSelectionModel().getSelectedItem();
            tienda.eliminarProducto(productoSeleccionado);
            tienda.mostrarMensaje(Alert.AlertType.CONFIRMATION, "Producto eliminado correctamente");
            tienda.loadStage("/ventanas/inventoryPage.fxml",actionEvent);
        }
    }
    private void cargarTabla() {
        ObservableList<Producto> listaProductos = FXCollections.observableArrayList(tienda.enviarProductos().values());
        nameTable.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
        codeTable.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCodigo()));
        priceTable.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPrecio()+""));
        quantityTable.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCantidad()+""));
        table.setItems(listaProductos);
    }
    public void back(ActionEvent actionEvent) {
        Object evt = actionEvent.getSource();
        if (evt.equals(bttBack)) {
            tienda.loadStage("/ventanas/home.fxml", actionEvent);
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
       cargarTabla();
    }
}
