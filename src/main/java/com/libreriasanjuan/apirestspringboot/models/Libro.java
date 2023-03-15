package com.libreriasanjuan.apirestspringboot.models;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "libros")
@Data
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "libro_id")
    private Long libroId;
    @Column(name = "libro_precio")
    private Integer libroPrecio;
    @Column(name = "libro_titulo")
    private String libroTitulo;
    @Column(name = "libro_autor")
    private String libroAutor;
    @Column(name = "libro_portada")
    private String libroPortada;
    @Transient //Considerar implementar un DTO
    private int libroCantidad;
}
