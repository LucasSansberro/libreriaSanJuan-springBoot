package com.libreriasanjuan.apirestspringboot.services.interfaces;

import com.libreriasanjuan.apirestspringboot.dto.LibrosMasVendidosDTO;
import com.libreriasanjuan.apirestspringboot.models.Libro;

import java.util.List;

public interface LibroService {
    List<Libro> getAllLibros();

    List<LibrosMasVendidosDTO> getMasVendidos();

    Libro saveLibro(Libro libro);

    Libro updateById(Long id, Libro libroActualizado);

    Libro deleteById(Long id);
}
