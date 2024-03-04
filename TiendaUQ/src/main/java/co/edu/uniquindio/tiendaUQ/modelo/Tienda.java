package co.edu.uniquindio.tiendaUQ.modelo;

import co.edu.uniquindio.tiendaUQ.exceptions.CampoObligatorioException;
import co.edu.uniquindio.tiendaUQ.exceptions.CampoRepetido;
import co.edu.uniquindio.tiendaUQ.exceptions.CampoVacioException;
import co.edu.uniquindio.tiendaUQ.utils.ArchivoUtils;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.*;

public class Tienda {
    private final String RUTA_CLIENTES = "src/main/resources/serializable/cliente.ser";
    private final String RUTA_PRODUCTOS = "src/main/resources/serializable/productos.ser";
    private final String RUTA_VENTAS = "src/main/resources/serializable/venta.ser";
    private static Tienda tienda;
    private Map<String, Cliente> clientes = new HashMap<>();
    private Map<String, Producto> productos = new HashMap<>();
    private Map<String, Producto> carrito = new HashMap<>();
    private ArrayList<Venta> ventas = new ArrayList<>();
    private Map<String, Producto> carritoCompra = new HashMap<>();
    private Venta ventaRealizada = new Venta();
    private Cliente CLIENTE_SESION = new Cliente();
    private ListaVentas historialVentas = new ListaVentas();
    public static Tienda getInstance() {
        if (tienda == null) {
            tienda = new Tienda();
        }
        return tienda;
    }
    private void leerClientes() {
        try (ObjectInputStream entrada = new ObjectInputStream(new FileInputStream(RUTA_CLIENTES))) {
            HashMap<String, Cliente> listaClientes = (HashMap<String, Cliente>) entrada.readObject();
            clientes.putAll(listaClientes);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    private void leerProductos() {
        try (ObjectInputStream entrada = new ObjectInputStream(new FileInputStream(RUTA_PRODUCTOS))) {
            HashMap<String, Producto> listaProductos = (HashMap<String, Producto>) entrada.readObject();
            productos.putAll(listaProductos);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    private void leerVentas() {
        ventas = new ArrayList<>();
        try (ObjectInputStream entrada = new ObjectInputStream(new FileInputStream(RUTA_VENTAS))) {
            ArrayList<Venta> ventas1 = (ArrayList<Venta>) entrada.readObject();
            for (Venta paquete : ventas1) {
                ventas.add(paquete);
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    public Cliente registrarCliente(String identificationNumber, String nombre, String direccion, String usuario, String contrasena) throws CampoVacioException, CampoObligatorioException, CampoRepetido {
        if (nombre == null || nombre.isEmpty()) {
            throw new CampoObligatorioException(("Es necesario ingresar el nombre"));
        }
        if (direccion == null || direccion.isEmpty()) {
            throw new CampoVacioException("Es necesario ingresar la direccion.");
        }
        if (usuario == null || usuario.isEmpty()) {
            throw new CampoVacioException("Es necesario ingresar el usuario");
        }
        if (identificationNumber == null) {
            throw new CampoVacioException("Es necesario ingresar el usuario");
        }
        if (contrasena == null || contrasena.isEmpty()) {
            throw new CampoVacioException("Es necesario ingresar la contraseña");
        }
        if (tienda.verifyCredentials(usuario, contrasena)) {
            throw new CampoRepetido("Las credenciales proporcionadas no estan disponibles");
        }
        Cliente cliente = Cliente.builder().
                nombre(nombre).
                direccion(direccion).
                numeroIdentificacion(identificationNumber).
                usuario(usuario).
                contrasena(contrasena)
                .build();
        clientes.put(identificationNumber, cliente);
        ArchivoUtils.serializarClientes(RUTA_CLIENTES, (HashMap) clientes);
        return cliente;
    }
    private boolean verifyCredentials(String usuario, String contrasena) {
        boolean state = false;
        for (Cliente c : clientes.values()) {
            if (c.getUsuario().equals(usuario) && c.getContrasena().equals(contrasena)) {
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
    public boolean verifyUser(String user, String password) {
        return (findUser(user, password)) ? true : false;
    }
    private boolean findUser(String user, String password) {
        for (Cliente cliente : clientes.values()) {
            if (cliente.getUsuario().equals(user) && cliente.getContrasena().equals(password)) {
                return true;
            }
        }
        return false;
    }
    public void mostrarMensaje(Alert.AlertType tipo, String mensaje) {
        Alert alert = new Alert(tipo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.show();
    }
    public void initializar() {
        leerClientes();
        leerProductos();
        leerVentas();
    }
    public void registrarProducto(String name, String code, String price, String quantity) throws CampoObligatorioException, CampoVacioException, CampoRepetido {
        if (name == null || name.isEmpty()) {
            throw new CampoObligatorioException(("Es necesario ingresar el nombre"));
        }
        if (code == null || code.isEmpty()) {
            throw new CampoVacioException("Es necesario ingresar el codigo.");
        }
        if (price == null || price.isEmpty()) {
            throw new CampoVacioException("Es necesario ingresar el precio");
        }
        if (quantity == null || quantity.isEmpty()) {
            throw new CampoVacioException("Es necesario ingresar la cantidad");
        }
        if (tienda.verifyCodes(code)) {
            throw new CampoRepetido("El codigo del producto no esta disponible");
        }
        Producto producto = Producto.builder()
                .nombre(name)
                .cantidad(Integer.parseInt(quantity))
                .codigo(code)
                .precio(Float.valueOf(price))
                .build();
        productos.put(code,producto);
        ArchivoUtils.serializarClientes(RUTA_PRODUCTOS, (HashMap) productos);
    }
    private boolean verifyCodes(String code) {
        for (Producto producto : productos.values()) {
            if (producto.getCodigo().equals(code)) {
                return true;
            }
        }
        return false;
    }
    public HashMap<String, Producto> enviarProductos() {
        return (HashMap<String, Producto>) productos;
    }
    public void eliminarProducto(Producto productoSeleccionado) {
        productos.remove(productoSeleccionado.getCodigo());
    }
    public void editarProducto(String name, String price, String quantity, Producto oldProduct) throws CampoObligatorioException, CampoVacioException, CampoRepetido {
        if (name == null || name.isEmpty()) {
            throw new CampoObligatorioException(("Es necesario ingresar el nombre"));
        }
        if (price == null || price.isEmpty()) {
            throw new CampoVacioException("Es necesario ingresar el precio");
        }
        if (quantity == null || quantity.isEmpty()) {
            throw new CampoVacioException("Es necesario ingresar la cantidad");
        }
        Producto producto = Producto.builder()
                .nombre(name)
                .cantidad(Integer.parseInt(quantity))
                .precio(Float.valueOf(price))
                .codigo(oldProduct.getCodigo())
                .build();
        productos.replace(oldProduct.getCodigo(), oldProduct,producto);
        ArchivoUtils.serializarClientes(RUTA_PRODUCTOS, (HashMap) productos);
    }
    public boolean verifyInventory(Producto productoSeleccionado, int cantidad) {
        for (Producto producto : productos.values()) {
            if (producto.getCodigo().equals(productoSeleccionado.getCodigo())) {
                if(producto.getCantidad()>= cantidad){
                    Producto productoUpdate = Producto.builder()
                            .codigo(producto.getCodigo())
                            .precio(producto.getPrecio())
                            .cantidad(producto.getCantidad()-cantidad)
                            .nombre(producto.getNombre())
                            .build();
                    productos.replace(producto.getCodigo(),productoUpdate);
                    return true;
                }else{
                    return false;
                }
            }
        }
        return false;
    }
    public void updateInventory(Producto productoSeleccionado) {
        for (Producto producto : productos.values()) {
            if (producto.getCodigo().equals(productoSeleccionado.getCodigo())) {
                Producto productoReplace = Producto.builder()
                        .nombre(producto.getNombre())
                        .codigo(producto.getCodigo())
                        .precio(producto.getPrecio())
                        .cantidad(producto.getCantidad() + productoSeleccionado.getCantidad())
                        .build();
                productos.replace(productoSeleccionado.getCodigo(), producto, productoReplace);
            }
        }
    }
    public void recibirCarrito(Map<String, Producto> productosCarrito) {
        carrito = productosCarrito;
    }
    public Cliente guardarCliente(String user, String password) {
        Cliente findUser = new Cliente();
        for (Cliente cliente : clientes.values()) {
            if (cliente.getUsuario().equals(user) && cliente.getContrasena().equals(password)) {
                findUser = cliente;
            }
        }
        return findUser;
    }
    public void almacenarCliente(Cliente cliente) {
        CLIENTE_SESION = cliente;
    }
    public void serializar() {
        ArchivoUtils.serializarClientes(RUTA_CLIENTES, (HashMap<String, Cliente>) clientes);
        ArchivoUtils.serializarProductos(RUTA_PRODUCTOS, (HashMap<String, Producto>) productos);
        ArchivoUtils.serializarVentas(RUTA_VENTAS, ventas);
    }
    public Cliente enviarCliente() {
        return CLIENTE_SESION;
    }
    public String vincularCodigo() {
        if(ventas.size() == 0){
            return "0";
        }else{
            int codigo = 0;
            codigo = Integer.parseInt(ventas.get(ventas.size()-1).getCodigo())+1;
            return codigo+"";
        }
    }
    public void almacenarVenta(Venta venta) {
        historialVentas.anadirVenta(venta);
        ventas.add(venta);
        ArchivoUtils.serializarVentas(RUTA_VENTAS,ventas);
    }
    public void receiptPage(Venta venta, HashMap<String, Producto> carrito) {
        ventaRealizada = venta;
        carritoCompra = carrito;
    }
    public HashMap<String,Producto> carritoVenta(){
        return (HashMap<String, Producto>) carritoCompra;
    }
    public Venta enviarVenta(){
        return ventaRealizada;
    }
    public void inicializar() {
        carrito = new HashMap<>();
        carritoCompra = new HashMap<>();
        ventaRealizada = new Venta();
        CLIENTE_SESION = new Cliente();
    }
    public Map<String, Producto> enviarCarrito() {
        return carrito;
    }
    public void limpiarCarrito(HashMap<String, Producto> carrito) {
        for(Producto producto : carrito.values()){
            updateInventory(producto);
        }
    }
    public void limpiarCarritoPay()
    {
        carrito = new HashMap<>();
    }
}

