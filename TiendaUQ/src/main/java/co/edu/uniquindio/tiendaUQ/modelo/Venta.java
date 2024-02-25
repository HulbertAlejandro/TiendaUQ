package co.edu.uniquindio.tiendaUQ.modelo;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Venta implements Serializable {
    private String codigo;
    private LocalDate fecha;
    private Double total;
}
