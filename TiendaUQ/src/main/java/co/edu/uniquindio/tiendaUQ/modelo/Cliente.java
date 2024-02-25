package co.edu.uniquindio.tiendaUQ.modelo;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class Cliente implements Serializable {

    private String numeroIdentificacion, nombre, direccion, usuario, contrasena;

}
