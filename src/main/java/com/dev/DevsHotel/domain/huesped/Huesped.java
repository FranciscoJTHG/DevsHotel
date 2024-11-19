package com.dev.DevsHotel.domain.huesped;

import com.dev.DevsHotel.domain.usuario.Usuario;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "huesped")
@Entity(name = "Huesped")
public class Huesped 
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombreCompleto;

    @Column(unique = true)
    private String email;
    
    private String telefono;

    // Relación uno a uno con Usuario
    // @OneToOne(fetch = FetchType.LAZY)
    // @JoinColumn(name = "usuario_id", unique = true)
    // private Usuario usuario;

    public Huesped(DatosHuesped datosHuesped) {
        this.nombreCompleto = datosHuesped.nombreCompleto();
        this.email = datosHuesped.email();
        this.telefono = datosHuesped.telefono();
    }

    public Huesped actualizarDatosHuesped(DatosHuesped datosHuesped) {
        this.nombreCompleto = datosHuesped.nombreCompleto();
        this.email = datosHuesped.email();
        this.telefono = datosHuesped.telefono();
        return this;
    }

}
