package com.dev.DevsHotel.domain.huesped;


public record DatosListadoHuesped(
    Long id,
    String nombreCompleto,
    String email,
    String telefono
) {
    public DatosListadoHuesped(Huesped huesped) {
        this(
            huesped.getId(),
            huesped.getNombreCompleto(),
            huesped.getEmail(),
            huesped.getTelefono()
        );
    }
}
