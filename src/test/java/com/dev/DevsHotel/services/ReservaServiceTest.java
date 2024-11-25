package com.dev.DevsHotel.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;

import org.checkerframework.checker.units.qual.g;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.server.ResponseStatusException;

import com.dev.DevsHotel.domain.huesped.DatosHuesped;
import com.dev.DevsHotel.domain.huesped.Huesped;
import com.dev.DevsHotel.domain.reserva.DatosDetalleReserva;
import com.dev.DevsHotel.domain.reserva.DatosRegistroReserva;
import com.dev.DevsHotel.domain.reserva.FormaDePago;
import com.dev.DevsHotel.domain.reserva.Reserva;
import com.dev.DevsHotel.domain.usuario.Usuario;
import com.dev.DevsHotel.domain.validaciones.ValidadorDeHuesped;
import com.dev.DevsHotel.manejoErrores.ErrorResponse;
import com.dev.DevsHotel.repositories.HuespedRepository;
import com.dev.DevsHotel.repositories.ReservaRepository;
import com.dev.DevsHotel.repositories.UsuarioRepository;


@ExtendWith(MockitoExtension.class)
public class ReservaServiceTest {

    @Mock
    private HuespedRepository huespedRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private ReservaRepository reservaRepository;

    @Mock
    private List<ValidadorDeHuesped> validadorDeHuesped;

    @InjectMocks
    private ReservaService reservaService;

    @Test
    @DisplayName("Debe registrar una reserva")
    void testRegistrarReserva() {

        // Give
        DatosHuesped datosHuesped = new DatosHuesped(
            "Juan Perez", 
            "juanPerez@gmail.com", 
            "123456789"
        );

        DatosRegistroReserva datosRegistroReserva = this.datosRegistroReserva(
            LocalDate.now(), 
            LocalDate.now().plusDays(2), 
            "200", 
            FormaDePago.DEBITO, 
            datosHuesped
        );

        Reserva reserva = new Reserva(datosRegistroReserva);
        reserva.setId(1L);
        reserva.setActivo(true);

        // When
        when(reservaRepository.save(any(Reserva.class))).thenReturn(reserva);

        // Configurar el validador para que no haga nada
        doNothing().when(validadorDeHuesped).forEach(any());

        DatosDetalleReserva datosRespuestaReserva = reservaService.registrarReserva(datosRegistroReserva);

        // Then
        assertThat(datosRespuestaReserva).isNotNull();
        verify(reservaRepository).save(any(Reserva.class));
        verify(validadorDeHuesped).forEach(any());
    }

    @Test
    @DisplayName("Debe lanzar error cuando las fechas son inválidas")
    void testRegistrarReservaConFechasInvalidas() {

        // Give
        DatosHuesped datosHuesped = new DatosHuesped(
            "Juan Perez", 
            "juanPerez@gmail.com", 
            "123456789"
        );

        DatosRegistroReserva datosInvalidos = this.datosRegistroReserva(
            LocalDate.now(), 
            LocalDate.now().minusDays(2), 
            "200", 
            FormaDePago.DEBITO,
            datosHuesped
        );

        // Configurar el validador para que lance la excepción
        FieldError fieldError = new FieldError(
            "reserva", 
            "fechaCheckOut", 
            "La fecha de check-out debe ser posterior a la fecha de check-in");

        ErrorResponse errorResponse = new ErrorResponse(fieldError);

        doThrow(new ResponseStatusException(HttpStatus.BAD_REQUEST, errorResponse.mensaje()))
            .when(validadorDeHuesped).forEach(any());


        // When / Then
        assertThatThrownBy(() -> reservaService.registrarReserva(datosInvalidos))
            .isInstanceOf(ResponseStatusException.class);
    }

    private DatosRegistroReserva datosRegistroReserva(
        LocalDate fechaCheckIn,
        LocalDate fechaCheckOut,
        String valor,
        FormaDePago formaDePago,
        DatosHuesped datosHuesped
    ) { 
        return new DatosRegistroReserva(
            fechaCheckIn, 
            fechaCheckOut, 
            valor, 
            formaDePago, 
            datosHuesped
        );
    }
    
    


}
