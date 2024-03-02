package co.edu.uniquindio.tiendaUQ.controladores;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class HistoryControlador {

    @FXML
    private TableColumn<?, ?> priceTable;

    @FXML
    private TableColumn<?, ?> nameTable;

    @FXML
    private TableColumn<?, ?> quantityTable;

    @FXML
    private TableColumn<?, ?> dateTable;

    @FXML
    private TableView<?> table;

    @FXML
    private TableColumn<?, ?> codeTable;

    @FXML
    private Button bttBack;

    @FXML
    private Button bttSelect;

    @FXML
    void back(ActionEvent event) {

    }

    @FXML
    void select(ActionEvent event) {

    }

}
