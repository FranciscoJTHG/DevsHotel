package com.dev.DevsHotel.manejoErrores;

import org.springframework.validation.FieldError;

public record ErrorResponse(
    String tipo, 
    String mensaje
) {
    public ErrorResponse(FieldError error) {
        this(error.getField(), error.getDefaultMessage());
    }
    
}
