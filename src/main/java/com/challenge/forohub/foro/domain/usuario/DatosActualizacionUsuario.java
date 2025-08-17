package com.challenge.forohub.foro.domain.usuario;

import jakarta.validation.constraints.NotBlank;

public record DatosActualizacionUsuario(
        @NotBlank String login,
        @NotBlank String contraseña
) {
}
