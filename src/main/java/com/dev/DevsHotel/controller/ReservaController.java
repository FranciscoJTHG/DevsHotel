package com.dev.DevsHotel.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dev.DevsHotel.domain.reserva.DatosActualizarReserva;
import com.dev.DevsHotel.domain.reserva.DatosDetalleReserva;
import com.dev.DevsHotel.domain.reserva.DatosListadoReservas;
import com.dev.DevsHotel.domain.reserva.DatosRegistroReserva;
import com.dev.DevsHotel.domain.utils.PageResponse;
import com.dev.DevsHotel.services.ReservaService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/reservas")
@SecurityRequirement(name = "bearer-key")
public class ReservaController 
{

    @Autowired
    private ReservaService reservaService;

    @SuppressWarnings("rawtypes")
    @PostMapping
    public ResponseEntity registrarReserva(@RequestBody @Valid DatosRegistroReserva datosRegistroReserva) 
    {
        var reserva = reservaService.registrarReserva(datosRegistroReserva);
        return ResponseEntity.ok(reserva);

    }

    @GetMapping
    public ResponseEntity<PageResponse<DatosListadoReservas>> listarReservas(
        @RequestParam(required = false) String nombreHuesped,
        @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fechaCheckIn,
        @PageableDefault(
            page = 0, 
            size = 10, 
            sort = "fechaCheckIn",
            direction = Direction.ASC) Pageable paginacion) 
    {

        return ResponseEntity.ok(reservaService.listarReservas(nombreHuesped, fechaCheckIn, paginacion));

    }

    @GetMapping("/topico/{id}")
    public ResponseEntity<DatosDetalleReserva> detalleReserva(@PathVariable Long id) {
        
        var reserva = reservaService.detalleReserva(id);
        return ResponseEntity.ok(new DatosDetalleReserva(reserva));
    }

    @PutMapping("/topico")
    public ResponseEntity<DatosDetalleReserva> actualizarReserva(@RequestBody @Valid DatosActualizarReserva datosActualizarReserva) {

        var reserva = reservaService.actualizarReserva(datosActualizarReserva);
        return ResponseEntity.ok(reserva);
    }

    @DeleteMapping("/topico/{id}")
    public ResponseEntity<DatosDetalleReserva> eliminarReserva(@PathVariable Long id) {

        var datosReserva = reservaService.desactivarReserva(id);

        return ResponseEntity.ok(datosReserva);
    }

}
