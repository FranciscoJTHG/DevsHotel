package com.dev.DevsHotel.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.dev.DevsHotel.domain.huesped.DatosHuesped;
import com.dev.DevsHotel.domain.reserva.DatosDetalleReserva;
import com.dev.DevsHotel.domain.reserva.DatosRegistroReserva;
import com.dev.DevsHotel.domain.reserva.Reserva;
import com.dev.DevsHotel.domain.usuario.DatosDetalleUsuario;
import com.dev.DevsHotel.domain.usuario.DatosLoginUsuario;
import com.dev.DevsHotel.domain.usuario.Usuario;
import com.dev.DevsHotel.domain.validaciones.EmailValidationService;
import com.dev.DevsHotel.domain.validaciones.ValidacionException;
import com.dev.DevsHotel.manejoErrores.TipoError;
import com.dev.DevsHotel.repositories.UsuarioRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class AutenticacionService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuariosRepository;

    @Autowired
    private  PasswordEncoder passwordEncoder;

    @Autowired
    private EmailValidationService emailValidationService;

    @Transactional
    public DatosDetalleUsuario registrarUsuario(DatosLoginUsuario datosLoginUsuario) {
        
        System.out.println("Registrando usuario... Servicio");
        if (!emailValidationService.isEmailAvailable(datosLoginUsuario.email())) 
            throw new ValidacionException(TipoError.ERROR_DE_NEGOCIO, "El email ya esta registrado");

        try {
            
            var loginUsuario = new Usuario(datosLoginUsuario);
    
            loginUsuario.setClave(passwordEncoder.encode(loginUsuario.getClave()));
    
            usuariosRepository.save(loginUsuario);
    
            return new DatosDetalleUsuario(loginUsuario);

        } catch (Exception e) {
            throw new RuntimeException("Error al registrar usuario", e);
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        
        UserDetails usuario = usuariosRepository.findByEmail(username);

        if (usuario == null) {
            // Establecer un atributo en el request actual
            RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
            if (requestAttributes != null) {
                ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) requestAttributes;
                servletRequestAttributes.getRequest().setAttribute("USER_NOT_FOUND", true);
            }

            throw new UsernameNotFoundException("Usuario no encontrado");
        }
        
        return usuario;
    }

}
