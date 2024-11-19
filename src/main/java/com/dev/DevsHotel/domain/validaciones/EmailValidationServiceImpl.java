package com.dev.DevsHotel.domain.validaciones;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dev.DevsHotel.repositories.UsuarioRepository;

@Component
public class EmailValidationServiceImpl implements EmailValidationService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public boolean isEmailAvailable(String email) {
        return usuarioRepository.findByEmail(email) == null;
    }
}
