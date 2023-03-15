package com.libreriasanjuan.apirestspringboot.models;

import lombok.Data;


import javax.persistence.*;

@Entity
@Table(name = "usuarios")
@Data
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="usuario_id")
    private Long usuarioId;
    @Column(name="usuario_correo")
    private String usuarioCorreo;
    @Column(name="usuario_clave")
    private String usuarioClave;
    @Column( name="is_admin")
    private Boolean isAdmin = false;
    
}
