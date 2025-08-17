package com.challenge.forohub.foro.domain.respuesta;

import jakarta.validation.constraints.NotNull;

public record DatosRegistroRespuesta(
       @NotNull String mensaje,
        @NotNull Long topicoId,
        @NotNull Long usuarioId
) {
}
