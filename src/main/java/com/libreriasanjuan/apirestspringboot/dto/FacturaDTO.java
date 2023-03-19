package com.libreriasanjuan.apirestspringboot.dto;

import com.libreriasanjuan.apirestspringboot.models.Libro;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FacturaDTO {
    private Long usuarioId;

    private int precioTotal;
    private LocalDate fecha;

    private List<Libro> librosComprados;

}
