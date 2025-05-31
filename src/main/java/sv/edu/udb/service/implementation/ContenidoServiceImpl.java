package sv.edu.udb.service.implementation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sv.edu.udb.model.Contenido;
import sv.edu.udb.repository.ContenidoRepository;
import sv.edu.udb.repository.UserRepository;
import sv.edu.udb.service.ContenidoService;
import sv.edu.udb.repository.domain.SecurityUser;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ContenidoServiceImpl implements ContenidoService {

    private final ContenidoRepository contenidoRepo;
    private final UserRepository userRepo;

    @Override
    @Transactional
    public Contenido registrarContenido(Contenido contenido, String creadorEmail) {
        SecurityUser user = userRepo.findByEmail(creadorEmail)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (!user.getRole().equalsIgnoreCase("ADMIN")) {
            throw new SecurityException("Solo administradores pueden registrar contenido");
        }

        contenido.setCreadoPor(user);
        contenido.setFechaRegistro(LocalDate.now());
        return contenidoRepo.save(contenido);
    }

    @Override
    public List<Contenido> obtenerTodos() {
        return contenidoRepo.findAll();
    }
    @Override
    public List<Contenido> obtenerPorTipo(String tipo) {
        return contenidoRepo.findByTipoIgnoreCase(tipo);
    }
}
