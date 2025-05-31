package sv.edu.udb.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import sv.edu.udb.controller.response.UserResponse;
import sv.edu.udb.service.implementation.UserDetailsImpl;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
@Tag(name = "Usuarios", description = "Operaciones relacionadas con los usuarios")
public class UserController {

    @GetMapping("/me")
    @Operation(
            summary = "Obtener datos del usuario autenticado",
            description = "Retorna la información del usuario actualmente autenticado con JWT u OAuth2",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    public UserResponse getCurrentUser(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return UserResponse.builder()
                .username(userDetails.getUsername())
                .email(userDetails.getUser().getEmail())
                .role(userDetails.getRole())
                .fechaCreacion(userDetails.getUser().getFechaCreacion())
                .build();
    }
}
