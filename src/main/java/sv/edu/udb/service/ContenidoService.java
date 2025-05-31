package sv.edu.udb.service;

import sv.edu.udb.model.Contenido;
import java.util.List;

public interface ContenidoService {
    Contenido registrarContenido(Contenido contenido, String creadorEmail);
    List<Contenido> obtenerTodos();

    List<Contenido> obtenerPorTipo(String tipo);
}
