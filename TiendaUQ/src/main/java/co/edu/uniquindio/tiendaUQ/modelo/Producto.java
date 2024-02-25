package co.edu.uniquindio.tiendaUQ.modelo;

import lombok.*;

import java.io.Serializable;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Producto implements Serializable{
    private String codigo, nombre;
    private int cantidad;
    private float precio;
}
