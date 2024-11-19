package com.dev.DevsHotel.manejoErrores;

import org.springframework.http.HttpStatus;

public enum TipoError {
    ID_INVALIDO(HttpStatus.BAD_REQUEST),
    EMAIL_DUPLICADO(HttpStatus.CONFLICT),
    RECURSO_NO_ENCONTRADO(HttpStatus.NOT_FOUND),
    VALIDACION_CAMPOS(HttpStatus.BAD_REQUEST),
    ERROR_DE_NEGOCIO(HttpStatus.BAD_REQUEST),
    ERROR_DE_SERVICIO(HttpStatus.INTERNAL_SERVER_ERROR);


    private final HttpStatus status;

    TipoError(HttpStatus status) {
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
