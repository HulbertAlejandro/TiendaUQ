package co.edu.uniquindio.tiendaUQ.Class;

import lombok.*;

import java.io.Serializable;
@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class Detalleventa implements Serializable {
    private int cantidad;
    private float subtotal;
}
