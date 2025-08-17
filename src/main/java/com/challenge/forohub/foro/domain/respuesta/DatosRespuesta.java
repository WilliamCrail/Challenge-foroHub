package com.challenge.forohub.foro.domain.respuesta;

import java.time.LocalDateTime;

public record DatosRespuesta(
        Long id,
        String mensaje,
        Long topicoId,
        String autorLogin,
        LocalDateTime fechaCreacion
) {
    public DatosRespuesta(Respuesta respuesta) {
        this(respuesta.getId(),
                respuesta.getMensaje(),
                respuesta.getTopico().getId(),
                respuesta.getAutor().getLogin(),
                respuesta.getFechaCreacion());
    }
}
