package co.edu.uniquindio.tiendaUQ.modelo;

import lombok.*;

import java.io.Serializable;
@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor

public class DetalleVenta implements Serializable {
    private int cantidad;
    private float subtotal;
}
