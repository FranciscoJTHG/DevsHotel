package com.dev.DevsHotel.services;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.dev.DevsHotel.domain.usuario.Usuario;

@Service
@Transactional
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;

    public String generarToken(Usuario usuario) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withIssuer("DevsHotel-api")
                    .withSubject(usuario.getEmail())
                    .withExpiresAt(generarFechaExpiracion())
                    .sign(algorithm);

            
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Error al generar token", exception);
        }
    }

    public String getSubject(String token) {
        if (token == null) {
            throw new RuntimeException("Token no proporcionado");
        }
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("DevsHotel-api")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException exception) {
            throw new JWTVerificationException("Token JWT inválido");
        }
    }

    private Date generarFechaExpiracion() {
        return Date.from(LocalDateTime.now()
                .plusHours(2)
                .atZone(ZoneId.systemDefault())
                .toInstant());
    }
}
