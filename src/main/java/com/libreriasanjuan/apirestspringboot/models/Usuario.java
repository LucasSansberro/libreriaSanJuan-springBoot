package com.libreriasanjuan.apirestspringboot.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.persistence.*;

@Entity
@Table(name = "usuarios")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="usuario_id")
    private Long usuarioId;
    @Column(name="usuario_correo", unique = true)
    private String usuarioCorreo;
    @Column(name="usuario_clave")
    private String usuarioClave;
    @Column( name="is_admin")
    private Boolean isAdmin = false;
    
}
