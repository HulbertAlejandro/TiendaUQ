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

public class Cliente implements Serializable {

    private String direccion, nombre, numeroIdentificacion;

}
