package com.challenge.forohub.foro.domain.topico;

import java.time.LocalDateTime;

public record DatosDetalleTopico(Long id,
                                 String titulo,
                                 String mensaje,
                                 String autor,
                                 Curso curso,
                                 Boolean status,
                                 LocalDateTime fechaDeCreacion
) {
    public DatosDetalleTopico(Topico topico) {
        this(
                topico.getId(),
                topico.getTitulo(),
                topico.getMensaje(),
                topico.getAutor(),
                topico.getCurso(),
                topico.getStatus(),
                topico.getFechaDeCreacion()
        );
    }
}
