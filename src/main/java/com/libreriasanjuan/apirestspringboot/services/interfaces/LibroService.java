package com.libreriasanjuan.apirestspringboot.services.interfaces;

import com.libreriasanjuan.apirestspringboot.dto.LibrosMasVendidosDTO;
import com.libreriasanjuan.apirestspringboot.models.Libro;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface LibroService {
    ResponseEntity<List<Libro>> getAllLibros();

    ResponseEntity<List<LibrosMasVendidosDTO>> getMasVendidos();

    ResponseEntity<?> saveLibro(Libro libro);

    ResponseEntity<?> updateById(Long id, Libro libroActualizado);

    ResponseEntity<Libro> deleteById(Long id);
}
