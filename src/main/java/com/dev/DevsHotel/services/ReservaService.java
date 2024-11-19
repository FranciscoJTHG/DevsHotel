package com.dev.DevsHotel.services;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.dev.DevsHotel.domain.utils.PageResponse;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.dev.DevsHotel.domain.huesped.DatosHuesped;
import com.dev.DevsHotel.domain.huesped.Huesped;
import com.dev.DevsHotel.domain.reserva.DatosActualizarReserva;
import com.dev.DevsHotel.domain.reserva.DatosDetalleReserva;
import com.dev.DevsHotel.domain.reserva.DatosListadoReservas;
import com.dev.DevsHotel.domain.reserva.DatosRegistroReserva;
import com.dev.DevsHotel.domain.reserva.Reserva;
import com.dev.DevsHotel.domain.validaciones.ValidacionException;
import com.dev.DevsHotel.domain.validaciones.ValidadorDeHuesped;
import com.dev.DevsHotel.domain.validaciones.ValidadorDeReserva;
import com.dev.DevsHotel.manejoErrores.TipoError;
import com.dev.DevsHotel.repositories.HuespedRepository;
import com.dev.DevsHotel.repositories.ReservaRepository;

import jakarta.persistence.EntityNotFoundException;

import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ReservaService {

    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private List<ValidadorDeHuesped> validadorDeHuesped;

    @Autowired
    private List<ValidadorDeReserva> validadorDeReservas;

    @Transactional
    public DatosDetalleReserva registrarReserva(DatosRegistroReserva datosRegistroReserva) {
        
        DatosHuesped datosHuesped = datosRegistroReserva.datosHuesped();

        validadorDeHuesped.forEach(validador -> validador.validar(datosHuesped));

        var reserva = new Reserva(datosRegistroReserva);
        reservaRepository.save(reserva);

        return new DatosDetalleReserva(reserva);

    }
    
    @Transactional(readOnly = true)
    public PageResponse<DatosListadoReservas> listarReservas(String nombreHuesped, LocalDate fechaCheckIn, Pageable paginacion) {

        nombreHuesped = (nombreHuesped != null) ? nombreHuesped.trim() : "";
        
        var reservas = reservaRepository.buscarPorHuespedYFecha(nombreHuesped, fechaCheckIn, paginacion);
        var total = reservaRepository.contarReservas(nombreHuesped, fechaCheckIn);
        
        var datosReservas = reservas.stream()
            .map(DatosListadoReservas::new)
            .toList();
    
        var page = new PageImpl<>(datosReservas, paginacion, total);
        
        return PageResponse.from(page);
    }

    @Transactional(readOnly = true)
    public Reserva detalleReserva(Long id) {

        validadorDeReservas.forEach(validador -> validador.validar(id));

        return reservaRepository.findByIdWithHuesped(id)
                .orElseThrow(() -> new EntityNotFoundException("Reserva no encontrada"));
    }

    @Transactional
    public DatosDetalleReserva actualizarReserva(DatosActualizarReserva datosActualizarReserva) {

        try {
            Reserva reserva = detalleReserva(datosActualizarReserva.id());
            reserva.actualizarDatosReserva(datosActualizarReserva);
            return new DatosDetalleReserva(reserva);
        } catch (Exception e) {
            throw new ValidacionException(
                TipoError.ERROR_DE_SERVICIO,
                "Error al actualizar la reserva",
                e
            );
        }
    }

    @Transactional
    public DatosDetalleReserva desactivarReserva(Long id) {

        Reserva reserva = detalleReserva(id);

        // Validar si la reserva ya está cancelada
        if (!reserva.getActivo())
            throw new ValidacionException(TipoError.ERROR_DE_NEGOCIO, "La reserva ya está desactivada");

        reserva.desactivarReservacion();
        return new DatosDetalleReserva(reserva);
    }

}
