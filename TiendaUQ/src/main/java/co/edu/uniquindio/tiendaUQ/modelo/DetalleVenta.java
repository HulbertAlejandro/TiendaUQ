package co.edu.uniquindio.tiendaUQ.modelo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Detalleventa implements Serializable {
    private int cantidad;
    private float subtotal;
}
