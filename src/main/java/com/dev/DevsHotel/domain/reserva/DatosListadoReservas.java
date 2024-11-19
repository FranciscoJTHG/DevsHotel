package com.dev.DevsHotel.domain.reserva;

import java.time.LocalDate;

import com.dev.DevsHotel.domain.huesped.DatosListadoHuesped;

public record DatosListadoReservas(
    Long id,
    LocalDate fechaCheckIn,
    LocalDate fechaCheckOut,
    String valor,
    FormaDePago formaDePago,
    DatosListadoHuesped datosListadoHuesped,
    Boolean activo
) {

    public DatosListadoReservas(Reserva reserva) {
        this(reserva.getId(), 
                reserva.getFechaCheckIn(), 
                reserva.getFechaCheckOut(), 
                reserva.getValor(), 
                reserva.getFormaDePago(),
                new DatosListadoHuesped(reserva.getHuesped()),
                reserva.getActivo());
    }
}
