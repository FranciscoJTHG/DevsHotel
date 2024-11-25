package com.dev.DevsHotel.repositories;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.dev.DevsHotel.domain.usuario.DatosLoginUsuario;
import com.dev.DevsHotel.domain.usuario.Usuario;


@DataJpaTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
public class UsuarioRepositoryTest {

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
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TestEntityManager entityManager;


    @Test
    @DisplayName("Debe encontrar usuario por email")
    void testFindByEmail() {

        // Give
        registrarUsuario("franthielengaravito@gmail.com", "123456");

        //Wen
        UserDetails usuarioEncontrado = usuarioRepository.findByEmail("franthielengaravito@gmail.com");

        // Then
        assertThat(usuarioEncontrado).isNotNull();

        Usuario usuario = (Usuario) usuarioEncontrado;

        assertThat(usuario)
            .satisfies(u -> {
                assertThat(u.getEmail()).isEqualTo("franthielengaravito@gmail.com");
                assertThat(u.getPassword()).isEqualTo("123456");
            });

    }

    @Test
    @DisplayName("Debe retornar null cuando el email no existe")
    void testFindByEmailNoExiste() {
        // When
        UserDetails usuarioEncontrado = usuarioRepository.findByEmail("noexiste@gmail.com");

        // Then
        assertThat(usuarioEncontrado).isNull();
    }

    private Usuario registrarUsuario(String email, String clave) {
        var usuario = new Usuario(new DatosLoginUsuario(email, clave));
        entityManager.persist(usuario);
        entityManager.flush();

        return usuario;
    }
}
