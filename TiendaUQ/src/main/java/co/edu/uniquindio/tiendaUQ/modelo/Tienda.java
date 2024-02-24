package co.edu.uniquindio.tiendaUQ.modelo;

import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Tienda {

    private static Tienda tienda;
    private List<Cliente> clientes = new ArrayList<Cliente>();

    public static Tienda getInstance()
    {
        if(tienda== null)
        {
            tienda = new Tienda();
        }
        return tienda;
    }

    public void loadStage(String url, Event event, String mensaje) {
        try {
            ((Node) (event.getSource())).getScene().getWindow().hide();
            Parent root = FXMLLoader.load(Objects.requireNonNull(Tienda.class.getResource(url)));
            Scene scene = new Scene(root);
            Stage newStage = new Stage();
            newStage.setScene(scene);
            newStage.setTitle("UQ Online Store");
            newStage.show();
        } catch (Exception ignored) {
        }
    }
    public boolean verifyUser(String user, String password){
        return (findUser(user,password)) ? true : false;
    }

    private boolean findUser(String user, String password) {
        for (Cliente cliente : clientes){
           if(cliente.getUsuario().equals(user) && cliente.getContrasena().equals(password)){
               return true;
           }
        }
        return false;
    }
    public void mostrarMensaje(Alert.AlertType tipo, String mensaje){
        Alert alert = new Alert(tipo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.show();
    }
}
