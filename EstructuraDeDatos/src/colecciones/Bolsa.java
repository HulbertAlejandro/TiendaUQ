package colecciones;

import java.util.ArrayList;
import java.util.List;

public class Bolsa {

    private List<Object> objetos;

    public Bolsa(){
        objetos = new ArrayList<>();
    }

    public void agregarObjeto(Object objeto){
        objetos.add(objeto);
    }

    public void eliminarObjeto(Object objeto){
        objetos.remove(objeto);
    }

    public List<Object> obtenerObjetos(){
        return new ArrayList<>(objetos);
    }

    public static void main(String []args){
        Bolsa bolsa = new Bolsa();
        bolsa.agregarObjeto("Objeto 1");
        bolsa.agregarObjeto("Objeto 2");
        bolsa.agregarObjeto("Objeto 3");

        System.out.println("Objetos en la bolsa:");
        for(Object objeto : bolsa.obtenerObjetos()) {
            System.out.println(objeto);
        }

        bolsa.eliminarObjeto("Objeto 2");

        System.out.println("\nObjetos en la bolsa despues de eliminar el Objeto 2:");
        for(Object objeto : bolsa.obtenerObjetos()) {
            System.out.println(objeto);
        }
    }
}
