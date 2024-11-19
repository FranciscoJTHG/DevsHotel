package com.dev.DevsHotel.manejoErrores;

import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.dev.DevsHotel.domain.validaciones.ValidacionException;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ValidationException;

@RestControllerAdvice
public class TratadorDeErrores {

    @Autowired
    private MessageSource messageSource;


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ErrorResponse>> tratarError400(MethodArgumentNotValidException e) {

        System.out.println("Error 400");
        var errores = e.getFieldErrors().stream().map(error -> {
            String mensaje = messageSource.getMessage(error, new Locale("es"));
            return new ErrorResponse(error.getField(), mensaje);
        }).toList();

        return ResponseEntity.badRequest().body(errores);
    }

    @ExceptionHandler(ValidacionException.class)
    public ResponseEntity<ErrorResponse> tratarError(ValidacionException e) {
        // HttpStatus status = e.getTipo().equals("id") ? HttpStatus.CONFLICT : HttpStatus.BAD_REQUEST;

        return ResponseEntity
            .status(e.getTipo().getStatus())
            .body(new ErrorResponse(e.getTipo().name(), e.getMessage()));
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> tratarErrorNotFound(EntityNotFoundException e) {
        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(new ErrorResponse("Id", e.getMessage()));
    }

    // Maneja cualquier otra excepción no manejada previamente
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> manejarExcepcionesGenerales(Exception ex) {
        
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocurrió un error inesperado.");
    }

}
