package co.edu.uniquindio.tiendaUQ.modelo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Venta implements Serializable {
    private String codigo;
    private LocalDate fecha;
    private Double total;
}
