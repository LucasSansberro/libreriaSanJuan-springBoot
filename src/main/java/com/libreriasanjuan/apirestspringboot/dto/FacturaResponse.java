package com.libreriasanjuan.apirestspringboot.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@AllArgsConstructor @NoArgsConstructor
public class FacturaResponse {
    private long FacturaId;
    private long UsuarioId;
    private int precioTotal;
    private LocalDate fecha;
}
