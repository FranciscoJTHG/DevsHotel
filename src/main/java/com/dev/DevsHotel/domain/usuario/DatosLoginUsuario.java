package com.dev.DevsHotel.domain.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record DatosLoginUsuario(
    
    @NotBlank(message = "{NotBlank.datosHuesped.email}")
    @Email(message = "{Email.datosHuesped.email}")
    String email,

    @NotBlank
    String clave
) {

}
