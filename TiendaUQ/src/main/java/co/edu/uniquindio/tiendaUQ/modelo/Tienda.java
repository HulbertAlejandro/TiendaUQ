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
    /*
    SE ASIGNAN LAS RUTAS DE SERIALIZADO
     */
    private final String RUTA_CLIENTES = "src/main/resources/serializable/cliente.ser";
    private final String RUTA_PRODUCTOS = "src/main/resources/serializable/productos.ser";
    private final String RUTA_VENTAS = "src/main/resources/serializable/venta.ser";
    private final String RUTA_HISTORIAL = "src/main/resources/serializable/historialVentas.ser";
    /*
    SE CREA LA INSTANCIA TIENDA
     */
    private static Tienda tienda;
    /*
    SE CREAN LAS ESTRUCTURAS DE DATOS
     */
    private final Map<String, Cliente> clientes = new HashMap<>();
    private final Map<String, Producto> productos = new HashMap<>();
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
    /*
    SE LEEN LOS CLIENTES DEL ARCHIVO
     */
    private void leerClientes() {
        try (ObjectInputStream entrada = new ObjectInputStream(new FileInputStream(RUTA_CLIENTES))) {
            HashMap<String, Cliente> listaClientes = (HashMap<String, Cliente>) entrada.readObject();
            clientes.putAll(listaClientes);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    /*
    SE LEEN LOS PRODUCTOS DEL ARCHIVO
     */
    private void leerProductos() {
        try (ObjectInputStream entrada = new ObjectInputStream(new FileInputStream(RUTA_PRODUCTOS))) {
            HashMap<String, Producto> listaProductos = (HashMap<String, Producto>) entrada.readObject();
            productos.putAll(listaProductos);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    /*
    SE LEEN LAS VENTAS DEL ARCHIVO
     */
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
    /*
    SE LEEN LOS HISTORIALES DEL ARCHIVO
     */
    private void leerHistorialVentas() {
        try (ObjectInputStream entrada = new ObjectInputStream(new FileInputStream(RUTA_HISTORIAL))) {
            ListaVentas ventas1 = (ListaVentas) entrada.readObject();
            historialVentas = ventas1;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    /*
    METODO PARA REGISTRAR UN CLIENTE
     */
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
        if ((tienda.verifyId(identificationNumber) != null) || (identificationNumber == null) || identificationNumber.isEmpty()) {
            throw new CampoRepetido("Ya se encuentra un usuario registrado con la identificacion");
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
    /*
    METODO PARA VERIFICAR EL NUMERO DE IDENTIFICACION EXISTA EN EL SISTEMA
     */
    private Cliente verifyId(String identificationNumber) {
        Cliente cliente = null;
        for (Cliente client : clientes.values()){
            if(client.getNumeroIdentificacion().equals(identificationNumber))
            {
                cliente =  client;
            }
        }
        return cliente;
    }
    /*
    SE VERIFICAN LAS CREDENCIALES DEL USUARIO QUE QUIERE INGRESAR AL SISTEMA
     */
    private boolean verifyCredentials(String usuario, String contrasena) {
        boolean state = false;
        for (Cliente c : clientes.values()) {
            if (c.getUsuario().equals(usuario) && c.getContrasena().equals(contrasena)) {
                state = true;
                break;
            }
        }
        return state;
    }
    /*
    METODO PARA CARGAR LAS PESTAÑAS
     */
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
    /*
    METODO PARA VERIFICAR EL USUARIO
     */
    public boolean verifyUser(String user, String password) {
        return findUser(user, password);
    }
    /*
    METODO PARA BUSCAR EL USUARIO
     */
    private boolean findUser(String user, String password) {
        for (Cliente cliente : clientes.values()) {
            if (cliente.getUsuario().equals(user) && cliente.getContrasena().equals(password)) {
                return true;
            }
        }
        return false;
    }
    /*
    METODO PARA MOSTRAR ALERTAS
     */
    public void mostrarMensaje(Alert.AlertType tipo, String mensaje) {
        Alert alert = new Alert(tipo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.show();
    }
    /*
    METODO PARA INICIALIZAR LOS DATOS
     */
    public void initializar() {
        leerClientes();
        leerProductos();
        leerVentas();
        leerHistorialVentas();
    }

    /**
     * Registra un nuevo producto en la tienda.
     *
     * @param name     Nombre del producto a registrar.
     * @param code     Código del producto a registrar.
     * @param price    Precio del producto a registrar.
     * @param quantity Cantidad del producto a registrar.
     * @throws CampoObligatorioException Si algún campo obligatorio (nombre, código, precio o cantidad) está vacío o nulo.
     * @throws CampoVacioException       Si algún campo (código, precio o cantidad) está vacío o nulo.
     * @throws CampoRepetido             Si el código del producto ya está registrado en la tienda.
     */
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
                .precio(Float.parseFloat(price))
                .build();
        productos.put(code,producto);
        ArchivoUtils.serializarClientes(RUTA_PRODUCTOS, (HashMap) productos);
    }

    /**
     * Verifica si un código de producto ya está registrado en la tienda.
     *
     * @param code Código a verificar.
     * @return true si el código está registrado, false en caso contrario.
     */
    private boolean verifyCodes(String code) {
        for (Producto producto : productos.values()) {
            if (producto.getCodigo().equals(code)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Retorna un mapa que contiene todos los productos almacenados en la tienda.
     *
     * @return Un mapa que contiene los productos almacenados en la tienda, donde la clave es el código del producto y el valor es el objeto Producto correspondiente.
     */
    public HashMap<String, Producto> enviarProductos() {
        return (HashMap<String, Producto>) productos;
    }

    /**
     * Elimina un producto específico de la tienda.
     *
     * @param productoSeleccionado El producto que se desea eliminar.
     */
    public void eliminarProducto(Producto productoSeleccionado) {
        productos.remove(productoSeleccionado.getCodigo());
    }


    /**
     * Edita un producto existente en la tienda con nuevos valores proporcionados.
     *
     * @param name         Nuevo nombre del producto.
     * @param price        Nuevo precio del producto.
     * @param quantity     Nueva cantidad del producto.
     * @param oldProduct   Producto existente que se desea editar.
     * @throws CampoObligatorioException Si algún campo obligatorio (nombre, precio o cantidad) está vacío o nulo.
     * @throws CampoVacioException       Si algún campo (precio o cantidad) está vacío o nulo.
     * @throws CampoRepetido             Si el código del producto ya está registrado en la tienda.
     */
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
                .precio(Float.parseFloat(price))
                .codigo(oldProduct.getCodigo())
                .build();
        productos.replace(oldProduct.getCodigo(), oldProduct,producto);
        ArchivoUtils.serializarClientes(RUTA_PRODUCTOS, (HashMap) productos);
    }

    /*
    METODO PARA VERIFICAR EL INVENTARIO EN CUENTA A LA CANTIDAD DE PRODUCTO
     */
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
    /*
    METODO PARA ACTUALIZAR EL INVENTARIO AL MOMENTO DE REGRESAR UN PRODUCTO
    */
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
    /*
    METODO PARA RECIBIR EL CARRITO PARA MANEJARLO A DIFERENTES INTERFACES
     */
    public void recibirCarrito(Map<String, Producto> productosCarrito) {
        carrito = productosCarrito;
    }
    /*
    METODO PARA ALMACENAR LOS CLIENTES
     */
    public Cliente guardarCliente(String user, String password) {
        Cliente findUser = new Cliente();
        for (Cliente cliente : clientes.values()) {
            if (cliente.getUsuario().equals(user) && cliente.getContrasena().equals(password)) {
                findUser = cliente;
            }
        }
        return findUser;
    }
    /*
    METODO PARA ALMACENAR EL CLIENTE
     */
    public void almacenarCliente(Cliente cliente) {
        CLIENTE_SESION = cliente;
    }
    /*
    METODO PARA SERIALIZAR LOS DATOS ALMACENADOS
     */
    public void serializar() {
        ArchivoUtils.serializarClientes(RUTA_CLIENTES, (HashMap<String, Cliente>) clientes);
        ArchivoUtils.serializarProductos(RUTA_PRODUCTOS, (HashMap<String, Producto>) productos);
        ArchivoUtils.serializarVentas(RUTA_VENTAS, ventas);
        ArchivoUtils.serializarHistorialVentas(RUTA_HISTORIAL,historialVentas);
    }
    /*
    METODO PARA ENVIAR EL CLIENTE DE LA SESION A DIFERENTES INTERFACES
     */
    public Cliente enviarCliente() {
        return CLIENTE_SESION;
    }
    /*
    METODO PARA VINCULAR CODIGOS A LAS VENTAS REALIZADAS
     */
    public String vincularCodigo() {
        if(ventas.isEmpty()){
            return "0";
        }else{
            int codigo = 0;
            codigo = Integer.parseInt(ventas.get(ventas.size()-1).getCodigo())+1;
            return codigo+"";
        }
    }
    /*
    METODO PARA ALMACENAR LAS VENTAS EN LA LISTA ENLAZADA
     */
    public void almacenarVenta(Venta venta) {
        historialVentas.anadirVenta(venta);
        historialVentas.printList();
        ventas.add(venta);
        ArchivoUtils.serializarVentas(RUTA_VENTAS,ventas);
    }
    /*
    METODO PARA ALMACENAR TEMPORALMENTE LA VENTA REALIZADA Y EL CARRITO DE COMPRA VENDIDO
    */
    public void receiptPage(Venta venta, HashMap<String, Producto> carrito) {
        ventaRealizada = venta;
        carritoCompra = carrito;
    }
    /*
    METODO PARA ENVIAR EL CARRITO DE VENTA A DIFERENTES INTERFACES
     */
    public HashMap<String,Producto> carritoVenta(){
        return (HashMap<String, Producto>) carritoCompra;
    }
    /*
    METODO PARA ENVIAR LA VENTA REALIZADA A DIFERENTES INTERFACES
     */
    public Venta enviarVenta(){
        return ventaRealizada;
    }
    /*
    METODO PARA INCIALIZAR Y LIMPIAR LAS ESTRUCTURAS DE DATOS
     */
    public void inicializar() {
        carrito = new HashMap<>();
        carritoCompra = new HashMap<>();
        ventaRealizada = new Venta();
        CLIENTE_SESION = new Cliente();
    }
    /*
    METODO PARA SERIALIZAR LOS DATOS ALMACENADOS
     */
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
    public ListaVentas enviarVentas(){
        return historialVentas;
    }

    /*
    Metodo que filtra los clientes por su numero de identificacion en la lista historico de compras
    con un for each verifica su id y si es el usuario de la venta, se añade a su lista de compras personales
    */

    public ArrayList<Venta> filtrarCliente(ArrayList<Venta> historicoCompras) {
        ArrayList<Venta> compras = new ArrayList<>();
        for(Venta venta : historicoCompras){
            if(venta.getCliente().getNumeroIdentificacion().equals(CLIENTE_SESION.getNumeroIdentificacion())){
                compras.add(venta);
            }
        }
        return compras;
    }
}

