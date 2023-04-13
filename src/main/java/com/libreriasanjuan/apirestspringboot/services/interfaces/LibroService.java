package com.libreriasanjuan.apirestspringboot.services.interfaces;

import com.libreriasanjuan.apirestspringboot.dto.LibroResponse;
import com.libreriasanjuan.apirestspringboot.dto.LibrosMasVendidosDTO;
import com.libreriasanjuan.apirestspringboot.models.Libro;

import java.util.List;

public interface LibroService {
    List<LibroResponse> getAllLibros();

    List<LibrosMasVendidosDTO> getMasVendidos();

    LibroResponse saveLibro(Libro libro);

    LibroResponse updateById(Long id, Libro libroActualizado);

    LibroResponse deleteById(Long id);
}
