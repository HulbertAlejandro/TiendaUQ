package co.edu.uniquindio.tiendaUQ.modelo;

import java.io.Serializable;
import java.util.ArrayList;

public class ListaVentas implements Serializable {
    private Nodo nodoPrimero;
    int tamano;
    public ListaVentas(){
        this.nodoPrimero = null;
        tamano = 0;
    }
    /**
     * Agrega una nueva venta al final de la lista enlazada de ventas.
     * @param venta La venta que se va a añadir a la lista.
     */
    public void anadirVenta(Venta venta){
        Nodo nodo = new Nodo(venta);
        if(nodoPrimero == null){
            nodoPrimero = nodo;
        }else{
            Nodo base = nodoPrimero;
            while(base.getSiguiente() != null){
                base = base.getSiguiente();
            }
            base.setSiguiente(nodo);
        }
        tamano+=1;
    }
    /**
     * Imprime los detalles de todas las ventas en la lista enlazada.
     * Cada venta se imprime con su código, valor, fecha y nombre del cliente.
     */
    public void printList(){
        Nodo nodo = nodoPrimero;
        while(nodo != null){
            System.out.println("Codigo de la venta: \n" + nodo.getVenta().getCodigo() +
                    "\nValor de la venta: \n" + nodo.getVenta().getTotal() +
                    "\nFecha de la venta: \n" + nodo.getVenta().getFecha() +
                    "\nNombre del cliente: \n" + nodo.getVenta().getCliente().getNombre());
            nodo = nodo.getSiguiente();
        }
    }
    /**
     * Convierte los datos de la lista enlazada de ventas a un ArrayList de ventas.
     * @return Un ArrayList que contiene todas las ventas de la lista enlazada.
     */
    public ArrayList<Venta> convertArrayList(){
        ArrayList<Venta> ventas = new ArrayList<>();
        Nodo nodo = nodoPrimero;
        while(nodo!=null){
            ventas.add(nodo.getVenta());
            nodo = nodo.getSiguiente();
        }
        return ventas;
    }
    /**
     * Devuelve el tamaño actual de la lista enlazada de ventas.
     * @return El número de elementos en la lista enlazada.
     */
    public int size(){
        return tamano;
    }
}
