package sv.edu.udb.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class Contenido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;

    private String tipo; // Serie o Película

    private int cantidadEpisodios;

    private int duracionPromedio; // en minutos

    private String genero;

    private String descripcion;

    private LocalDate fechaRegistro;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private sv.edu.udb.repository.domain.SecurityUser creadoPor;
}
