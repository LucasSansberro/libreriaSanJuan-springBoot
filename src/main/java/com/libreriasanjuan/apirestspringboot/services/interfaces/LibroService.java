package com.libreriasanjuan.apirestspringboot.services.interfaces;

import com.libreriasanjuan.apirestspringboot.models.Libro;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface LibroService {
    ResponseEntity<List<Libro>> getAllLibros();

    ResponseEntity<?> saveLibro(Libro libro);

    ResponseEntity<?> updateById(Long id, Libro libroActualizado);

    ResponseEntity<Libro> deleteById(Long id);
}
