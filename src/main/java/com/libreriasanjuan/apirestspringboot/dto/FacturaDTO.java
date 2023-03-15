package com.libreriasanjuan.apirestspringboot.dto;

import com.libreriasanjuan.apirestspringboot.models.Libro;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class FacturaDTO {
    private Long usuarioId;

    private int precioTotal;
    private LocalDate fecha;

    private List<Libro> librosComprados;

}
