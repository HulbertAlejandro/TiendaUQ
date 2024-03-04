package co.edu.uniquindio.tiendaUQ.modelo;

public class ListaVentas {
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
            nodo.setSiguiente(nodoPrimero);
        }
        tamano+=1;
    }
}
