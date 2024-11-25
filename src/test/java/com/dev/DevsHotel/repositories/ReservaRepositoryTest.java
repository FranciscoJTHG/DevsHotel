package com.dev.DevsHotel.repositories;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.dev.DevsHotel.domain.huesped.DatosHuesped;
import com.dev.DevsHotel.domain.huesped.Huesped;
import com.dev.DevsHotel.domain.reserva.DatosRegistroReserva;
import com.dev.DevsHotel.domain.reserva.FormaDePago;
import com.dev.DevsHotel.domain.reserva.Reserva;

import jakarta.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;




@DataJpaTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
public class ReservaRepositoryTest {

    @Container
    private static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(
            "postgres:15-alpine").withDatabaseName("devshotel_test")
            .withUsername("test")
            .withPassword("test");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
        registry.add("spring.jpa.hibernate.ddl-auto", () -> "create-drop");
    }

    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private EntityManager entityManager;

    @Test
    @DisplayName("Deberia devolver un listado de reservas por fecha que estan activa")
    void testBuscarPorHuespedYFecha() {

        // Given
        DatosHuesped datosHuesped = new DatosHuesped(
            "Juan Perez", 
            "juanPerez@gmail.com", 
            "123456789"
        );

        registrarReserva(
            LocalDate.now(), 
            LocalDate.now().plusDays(2), 
            "200", 
            FormaDePago.DEBITO, 
            datosHuesped
        );

        // When
        List<Reserva> reservas = reservaRepository.buscarPorHuespedYFecha(null, LocalDate.now(), null);

        // Then
        assertThat(reservas).isNotEmpty();
        
    }

    @Test
    @DisplayName("Deberia devolver lista vacía cuando no hay reservas para la fecha")
    void testBuscarPorHuespedYFechaNoExistente() {

        // Buscar en una fecha futura donde no hay reservas
        List<Reserva> reservas = reservaRepository.buscarPorHuespedYFecha(
            null, 
            LocalDate.now().plusMonths(1), 
            null
        );

        assertThat(reservas)
            .isEmpty();
    }

    @Test
    @DisplayName("Deberia filtrar por huésped específico")
    void testBuscarPorHuespedEspecifico() {

        // Crear datos de prueba
        DatosHuesped datosHuesped = new DatosHuesped(
            "Francisco Thielen", 
            "franthielen@gmail.com", 
            "123456789"
        );

        this.registrarReserva(
            LocalDate.now(),
            LocalDate.now().plusDays(2),
            "300",
            FormaDePago.DEBITO, 
            datosHuesped
        );

        List<Reserva> reservas = reservaRepository.buscarPorHuespedYFecha(
            "Francisco Thielen", 
            null, 
            null
        );

        assertThat(reservas)
            .isNotEmpty()
            .hasSize(1)
            .first()
            .satisfies(reserva -> {
                assertThat(reserva.getHuesped().getNombreCompleto())
                    .isEqualTo("Francisco Thielen");
            });

    }

    private Reserva registrarReserva(
        LocalDate fechaCheckIn, 
        LocalDate fechaCheckOut, 
        String valor, 
        FormaDePago formaDePago, 
        DatosHuesped datosHuesped
    ) {

        // Crear y persistir el huésped primero
        Huesped huesped = new Huesped();
        huesped.setNombreCompleto(datosHuesped.nombreCompleto());
        huesped.setEmail(datosHuesped.email());
        huesped.setTelefono(datosHuesped.telefono());
        entityManager.persist(huesped);

        // Crear y persistir la reserva
        var reserva = new Reserva(
            datosRegistroReserva(
                fechaCheckIn, 
                fechaCheckOut, 
                valor, 
                formaDePago, 
                datosHuesped
            )
        );

        reserva.setHuesped(huesped);
        entityManager.persist(reserva);
        entityManager.flush();

        return reserva;
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
