package com.dev.DevsHotel.domain.reserva;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum FormaDePago {
    CREDITO("Crédito"),
    DEBITO("Débito"),
    EFECTIVO("Efectivo"),
    TRANSFERENCIA("Transferencia");

    private String descripcion;

    FormaDePago(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    @JsonCreator
    public static FormaDePago fromString(String value) {
        for (FormaDePago formaDePago : FormaDePago.values()) {
            if (formaDePago.name().equals(value) || formaDePago.getDescripcion().equalsIgnoreCase(value)) {
                return formaDePago;
            }
        }
        throw new IllegalArgumentException("Forma de pago no válida: " + value);
    }
}
