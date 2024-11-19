package com.dev.DevsHotel.domain.validaciones;

import com.dev.DevsHotel.manejoErrores.TipoError;

public class ValidacionException extends RuntimeException {

    private final TipoError tipo;

    public ValidacionException(TipoError tipo, String mensaje) {
        super(mensaje);
        this.tipo = tipo;
    }

    // Constructor adicional para casos de servicio
    public ValidacionException(TipoError tipo, String mensaje, Throwable causa) {
        super(mensaje, causa);
        this.tipo = tipo;
    }

    // Constructor simple para mensajes sin tipo de error
    public ValidacionException(String mensaje) {
        this(TipoError.ERROR_DE_SERVICIO, mensaje);
    }

    public ValidacionException(String mensaje, Throwable causa) {
        this(TipoError.ERROR_DE_SERVICIO, mensaje, causa);
    }

    public TipoError getTipo() {
        return tipo;
    }

}



