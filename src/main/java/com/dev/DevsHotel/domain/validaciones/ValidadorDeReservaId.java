package com.dev.DevsHotel.domain.validaciones;

import org.springframework.stereotype.Component;

import com.dev.DevsHotel.manejoErrores.TipoError;

@Component
public class ValidadorDeReservaId implements ValidadorDeReserva {

    @Override
    public void validar(Long id) {

        if (id == null) {
            throw new ValidacionException(TipoError.ID_INVALIDO, "El ID de la reserva no puede ser nulo");
        }

        if (id <= 0) {
            throw new ValidacionException(TipoError.ID_INVALIDO, "El ID de la reserva debe ser un número positivo");
        }
    }

}
