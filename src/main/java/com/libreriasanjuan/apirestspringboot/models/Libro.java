package com.libreriasanjuan.apirestspringboot.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "libros")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "libro_id")
    private Long libroId;
    @Column(name = "libro_precio")
    private Integer libroPrecio;
    @Column(name = "libro_titulo", unique = true)
    private String libroTitulo;
    @Column(name = "libro_autor")
    private String libroAutor;
    @Column(name = "libro_portada")
    private String libroPortada;
    @Transient
    private int libroCantidad;
}
