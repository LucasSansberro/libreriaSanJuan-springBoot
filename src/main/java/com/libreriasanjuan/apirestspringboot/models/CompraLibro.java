package com.libreriasanjuan.apirestspringboot.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "compra_libro")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompraLibro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "compra_id")
    private Long compraId;
    @ManyToOne
    @JoinColumn(name = "factura_id", referencedColumnName = "factura_id")
    private Factura factura;
    @ManyToOne
    @JoinColumn(name = "libro_id", referencedColumnName = "libro_id")
    private Libro libro;
    @Column(name = "libro_cantidad")
    private Integer libroCantidad;
    @Column(name = "precio_total")
    private int precioTotal;

}
