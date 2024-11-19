package com.dev.DevsHotel.domain.reserva;

import java.time.LocalDate;

import com.dev.DevsHotel.domain.huesped.Huesped;
import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "reserva")
@Entity(name = "Reserva")
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT-3")
    private LocalDate fechaCheckIn;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT-3")
    private LocalDate fechaCheckOut;

    private String valor;

    @Enumerated(EnumType.STRING)
    private FormaDePago formaDePago;
    
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "huesped_id")
    private Huesped huesped;

    private Boolean activo;


    public Reserva(DatosRegistroReserva datosRegistroReserva) {
        this.activo = true;
        this.fechaCheckIn = datosRegistroReserva.fechaCheckIn();
        this.fechaCheckOut = datosRegistroReserva.fechaCheckOut();
        this.valor = datosRegistroReserva.valor();
        this.formaDePago = datosRegistroReserva.formaDePago();
        this.huesped = new Huesped(datosRegistroReserva.datosHuesped());
    }

    public void actualizarDatosReserva(DatosActualizarReserva datosActualizarReserva) {

        if (datosActualizarReserva.fechaCheckIn() != null) {
            this.fechaCheckIn = datosActualizarReserva.fechaCheckIn();
        }

        if (datosActualizarReserva.fechaCheckOut() != null) {
            this.fechaCheckOut = datosActualizarReserva.fechaCheckOut();
        }

        if (datosActualizarReserva.valor() != null) {
            this.valor = datosActualizarReserva.valor();
        }

        if (datosActualizarReserva.formaDePago() != null) {
            this.formaDePago = datosActualizarReserva.formaDePago();
        }

        if (datosActualizarReserva.datosHuesped() != null) {
            this.huesped = huesped.actualizarDatosHuesped(datosActualizarReserva.datosHuesped());
        }
    }

    public void desactivarReservacion() {
        this.activo = false;
    }

}
