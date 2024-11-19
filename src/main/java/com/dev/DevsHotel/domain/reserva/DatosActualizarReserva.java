package com.dev.DevsHotel.domain.reserva;

import java.time.LocalDate;

import com.dev.DevsHotel.domain.huesped.DatosHuesped;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DatosActualizarReserva(

    @NotNull
    Long id,

    @NotNull(message = "{NotNull.datosRegistroReserva.fechaCheckIn}")
    @JsonFormat(pattern = "yyyy-MM-dd")
    LocalDate fechaCheckIn,

    @NotNull(message = "{NotNull.datosRegistroReserva.fechaCheckOut}")
    @JsonFormat(pattern = "yyyy-MM-dd")
    LocalDate fechaCheckOut,

    @NotBlank(message = "{NotBlank.valor}")
    String valor,

    @NotNull(message = "{NotNull.datosRegistroReserva.formaDePago}")
    @JsonProperty("formaDePago")
    FormaDePago formaDePago,

    @NotNull
    @Valid
    DatosHuesped datosHuesped
) {

}
