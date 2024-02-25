package co.edu.uniquindio.tiendaUQ.modelo;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Carrito implements Serializable {
    private String codigo;
}
