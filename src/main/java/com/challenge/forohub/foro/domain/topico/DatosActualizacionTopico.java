package com.challenge.forohub.foro.domain.topico;

public record DatosActualizacionTopico(
        String titulo,
        String mensaje,
        String autor,
        Curso curso
) {
}
