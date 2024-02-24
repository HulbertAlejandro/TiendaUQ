package co.edu.uniquindio.tiendaUQ.Class;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Producto implements Serializable{
    private String codigo, nombre;
    private int cantidad;
    private float precio;
}
