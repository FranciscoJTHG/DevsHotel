package com.dev.DevsHotel.domain.validaciones;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.dev.DevsHotel.domain.huesped.DatosHuesped;
import com.dev.DevsHotel.domain.huesped.Huesped;
import com.dev.DevsHotel.domain.reserva.DatosRegistroReserva;
import com.dev.DevsHotel.domain.reserva.Reserva;
import com.dev.DevsHotel.manejoErrores.TipoError;
import com.dev.DevsHotel.repositories.HuespedRepository;

@Component
public class ValidadorHuespedRegistrado implements ValidadorDeHuesped {

    @Autowired
    private HuespedRepository huespedRepository;

    @Override
    public void validar(DatosHuesped datosHuesped) {

        if (huespedRepository.findFirstByEmail(datosHuesped.email()).isPresent()) {
            throw new ValidacionException(TipoError.EMAIL_DUPLICADO, "El Huesped ya se encuentra registrado");
        } 
    }

}
