package co.edu.uniquindio.tiendaUQ.modelo;

import lombok.Getter;
import lombok.Setter;

public class Nodo {
    @Setter
    @Getter
    private Venta venta;
    private Nodo siguiente;
    public Nodo(Venta venta){
        this.venta = venta;
        siguiente = null;
    }
    public void setSiguiente(Nodo nodoPrimero) {
        this.siguiente = nodoPrimero;
    }
}
