package com.dev.DevsHotel.domain.huesped;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record DatosHuesped(
    @NotBlank(message = "{NotNull.datosHuesped.nombreCompleto}")
    String nombreCompleto,

    @NotBlank(message = "{NotBlank.datosHuesped.email}")
    @Email(message = "{Email.datosHuesped.email}")
    String email,
    
    @NotBlank(message = "{NotBlank.datosHuesped.telefono}")
    String telefono
) {

}
