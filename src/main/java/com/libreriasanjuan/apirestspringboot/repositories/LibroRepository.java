package com.libreriasanjuan.apirestspringboot.repositories;

import com.libreriasanjuan.apirestspringboot.models.Libro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LibroRepository extends JpaRepository<Libro,Long> {
    Optional<Libro> findByLibroTitulo(String nombre);
}
