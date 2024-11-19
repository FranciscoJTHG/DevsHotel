package com.dev.DevsHotel.domain.usuario;

public record DatosDetalleUsuario(
    Long id,
    String email,
    String clave
) {

    public DatosDetalleUsuario(Usuario usuario) {
        this(usuario.getId(), 
            usuario.getEmail(), 
            usuario.getClave());
    }
}
