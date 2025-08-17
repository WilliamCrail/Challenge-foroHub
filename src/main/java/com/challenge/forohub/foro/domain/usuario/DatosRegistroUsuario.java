package com.challenge.forohub.foro.domain.usuario;

import jakarta.validation.constraints.NotBlank;

public record DatosRegistroUsuario(
        @NotBlank String login,
        @NotBlank String contraseña
) {
}
