package com.dev.DevsHotel.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dev.DevsHotel.domain.usuario.DatosLoginUsuario;
import com.dev.DevsHotel.domain.usuario.Usuario;
import com.dev.DevsHotel.manejoErrores.ErrorResponse;
import com.dev.DevsHotel.security.DatosJWTToken;
import com.dev.DevsHotel.services.AutenticacionService;
import com.dev.DevsHotel.services.TokenService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth") 
@SecurityRequirement(name = "bearer-key")
public class AutenticacionController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @Autowired
    AutenticacionService autenticacionService;


    @PostMapping("/registro")
    public ResponseEntity<?> registrarUsuario(@RequestBody @Valid DatosLoginUsuario datosLoginUsuario) {

        var usuario = autenticacionService.registrarUsuario(datosLoginUsuario);
        return ResponseEntity.ok(usuario);
    }


    @PostMapping("/login")
    public ResponseEntity<?> autenticarUsuario(@RequestBody @Valid DatosLoginUsuario datosLoginUsuario) {

        Authentication authToken = new UsernamePasswordAuthenticationToken(datosLoginUsuario.email(), datosLoginUsuario.clave());

        System.out.println(authToken);

        var usuarioLogin = authenticationManager.authenticate(authToken);

        var JWTtoken = tokenService.generarToken((Usuario) usuarioLogin.getPrincipal());

        return ResponseEntity.ok(new DatosJWTToken(JWTtoken));
    }
}
