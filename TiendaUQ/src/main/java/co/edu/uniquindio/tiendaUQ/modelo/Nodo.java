package co.edu.uniquindio.tiendaUQ.modelo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

public class Nodo implements Serializable {
    @Setter
    @Getter
    private Venta venta;
    private Nodo siguiente;
    public Nodo(Venta venta){
        this.venta = venta;
        siguiente = null;
    }
    public Venta getVenta() {
        return venta;
    }
    public void setVenta(Venta venta) {
        this.venta = venta;
    }
    public Nodo getSiguiente() {
        return siguiente;
    }
    public void setSiguiente(Nodo siguiente) {
        this.siguiente = siguiente;
    }

}
