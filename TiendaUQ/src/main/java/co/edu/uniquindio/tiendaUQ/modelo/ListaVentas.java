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
    public ArrayList<Venta> convertArrayList(){
        ArrayList<Venta> ventas = new ArrayList<>();
        Nodo nodo = nodoPrimero;
        while(nodo!=null){
            ventas.add(nodo.getVenta());
            nodo = nodo.getSiguiente();
        }
        return ventas;
    }
    public int size(){
        return tamano;
    }
}
