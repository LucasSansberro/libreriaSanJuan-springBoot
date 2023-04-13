package com.libreriasanjuan.apirestspringboot.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor @NoArgsConstructor
public class LibroResponse {
    private Long libroId;
    private Integer libroPrecio;
    private String libroTitulo;
    private String libroAutor;
    private String libroPortada;
}
