package com.libreriasanjuan.apirestspringboot.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

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
    @Transient
    private int libroCantidad;

    @OneToMany(mappedBy = "libro", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<CompraLibro> compraLibros;
}
