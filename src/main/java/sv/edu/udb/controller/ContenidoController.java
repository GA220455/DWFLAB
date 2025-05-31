package sv.edu.udb.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import sv.edu.udb.model.Contenido;
import sv.edu.udb.service.implementation.SecUserServiceImpl;
import sv.edu.udb.service.ContenidoService;
import sv.edu.udb.service.implementation.UserDetailsImpl;
import sv.edu.udb.service.implementation.ContenidoServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/api/contenido")
@RequiredArgsConstructor
public class ContenidoController {

    private final ContenidoService contenidoService;

    @GetMapping
    public List<Contenido> obtenerTodos() {
        return contenidoService.obtenerTodos();
    }

    @PostMapping
    public ResponseEntity<?> registrarContenido(
            @RequestBody Contenido contenido,
            @AuthenticationPrincipal UserDetailsImpl usuario
    ) {
        Contenido nuevo = contenidoService.registrarContenido(contenido, usuario.getUsername());
        return ResponseEntity.ok(nuevo);
    }
    @GetMapping(params = "tipo")
    public List<Contenido> obtenerPorTipo(@RequestParam String tipo) {
        return contenidoService.obtenerPorTipo(tipo);
    }
}
