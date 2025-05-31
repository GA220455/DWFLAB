package sv.edu.udb.controller.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder(toBuilder = true) //Es para poder modificar el objeto luego de creado
@FieldNameConstants //Para generar constantes con los nombres de las propiedades
public class UserResponse {
    private String username;
    private String email;
    private String role;
    private LocalDateTime fechaCreacion;
}
