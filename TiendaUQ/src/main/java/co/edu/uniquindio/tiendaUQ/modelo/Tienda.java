package co.edu.uniquindio.tiendaUQ.modelo;

import co.edu.uniquindio.tiendaUQ.exceptions.CampoObligatorioException;
import co.edu.uniquindio.tiendaUQ.exceptions.CampoRepetido;
import co.edu.uniquindio.tiendaUQ.exceptions.CampoVacioException;
import co.edu.uniquindio.tiendaUQ.utils.ArchivoUtils;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
public class Tienda implements Initializable {
    private final String RUTA_CLIENTES = "src/main/resources/serializable/cliente.ser";
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
    private void leerClientes() {
        try (ObjectInputStream entrada = new ObjectInputStream(new FileInputStream(RUTA_CLIENTES))) {
            ArrayList<Cliente> listaClientes = (ArrayList<Cliente>) entrada.readObject();
            clientes.addAll(listaClientes);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    public Cliente registrarCliente(String identificationNumber, String nombre, String direccion, String usuario, String contrasena) throws CampoVacioException, CampoObligatorioException, CampoRepetido
    {
        if (nombre == null || nombre.isEmpty()) {
            throw new CampoObligatorioException(("Es necesario ingresar el nombre"));
        }
        if (direccion == null || direccion.isEmpty()) {
            throw new CampoVacioException("Es necesario ingresar la direccion.");
        }
        if(usuario == null || usuario.isEmpty()){
            throw new CampoVacioException("Es necesario ingresar el usuario");
        }
        if( identificationNumber == null){
            throw new CampoVacioException("Es necesario ingresar el usuario");
        }
        if(contrasena == null || contrasena.isEmpty()){
            throw new CampoVacioException("Es necesario ingresar la contraseña");
        }
        if (tienda.verifyCredentials(usuario,contrasena)) {
            throw new CampoRepetido("Las credenciales proporcionadas no estan disponibles");
        }
        Cliente cliente = Cliente.builder().
                nombre(nombre).
                direccion(direccion).
                numeroIdentificacion(identificationNumber).
                usuario(usuario).
                contrasena(contrasena)
                .build();
        clientes.add(cliente);
        ArchivoUtils.serializarClientes(RUTA_CLIENTES,clientes);
        return cliente;
    }
    private boolean verifyCredentials(String usuario, String contrasena) {
        boolean state = false;
        for (Cliente c : clientes)
        {
            if (c.getUsuario().equals(usuario) && c.getContrasena().equals(contrasena))
            {
                state = true;
            }
        }
        return state;
    }
    public void loadStage(String url, Event event) {
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        leerClientes();
    }
}
