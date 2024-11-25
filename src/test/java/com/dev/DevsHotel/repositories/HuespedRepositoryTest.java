package com.dev.DevsHotel.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.StackWalker.Option;
import java.util.Optional;

import org.checkerframework.checker.units.qual.g;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.dev.DevsHotel.domain.huesped.DatosHuesped;
import com.dev.DevsHotel.domain.huesped.Huesped;


@DataJpaTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
public class HuespedRepositoryTest {

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
    private HuespedRepository huespedRepository;

    @Autowired
    private TestEntityManager entityManager;


    @Test
    @DisplayName("Debe encontrar un huésped por su email")
    void testFindFirstByEmail() {

        //Give
        registrarHuesped(
            "Francisco Thielen", 
            "franThielen@gmail.com", 
            "123456789"
        );

        // When
        Optional<Huesped> huespedEncontrado = huespedRepository.findFirstByEmail("franThielen@gmail.com");

        //Then
        assertThat(huespedEncontrado).isPresent()
            .get()
            .satisfies(huesped -> {
                assertThat(huesped.getNombreCompleto()).isEqualTo("Francisco Thielen");
                assertThat(huesped.getEmail()).isEqualTo("franThielen@gmail.com");
                assertThat(huesped.getTelefono()).isEqualTo("123456789");
            });
    }

    @Test
    @DisplayName("Debe retornar vacío cuando el email no existe")
    void testBuscarPorEmailInexistente() {

        Optional<Huesped> huespedEncontrado = huespedRepository.findFirstByEmail("franThielen@gmail.com");

        assertThat(huespedEncontrado).isEmpty();
    }

    private Huesped registrarHuesped(
        String nombreCompleto, 
        String email, 
        String telefono
    ) {

        var huesped = new Huesped(new DatosHuesped(nombreCompleto, email, telefono));
        entityManager.persist(huesped);
        entityManager.flush();

        return huesped;
    }
}
