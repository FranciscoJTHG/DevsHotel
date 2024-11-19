package com.dev.DevsHotel.domain.reserva;

import java.time.LocalDate;

import com.dev.DevsHotel.domain.huesped.Huesped;

public record DatosDetalleReserva(
    Long id,
    LocalDate fechaCheckIn,
    LocalDate fechaCheckOut,
    String valor,
    FormaDePago formaDePago,
    Huesped huesped,
    Boolean activo
) {

    public DatosDetalleReserva(Reserva reserva) {
        this(reserva.getId(), 
                reserva.getFechaCheckIn(), 
                reserva.getFechaCheckOut(), 
                reserva.getValor(), 
                reserva.getFormaDePago(),
                reserva.getHuesped(),
                reserva.getActivo());
    }
}
