package com.libreriasanjuan.apirestspringboot.repositories;

import com.libreriasanjuan.apirestspringboot.dto.LibrosMasVendidosDTO;
import com.libreriasanjuan.apirestspringboot.models.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

public interface LibroRepository extends JpaRepository<Libro,Long> {
    Optional<Libro> findByLibroTitulo(String nombre);

    @Query("SELECT new com.libreriasanjuan.apirestspringboot.dto.LibrosMasVendidosDTO(l.libroId,l.libroPrecio - 100, l.libroTitulo,l.libroAutor,l.libroPortada, SUM(cl.libroCantidad)) " +
            "FROM Libro l JOIN l.compraLibros cl " +
            "GROUP BY l.libroTitulo " +
            "ORDER BY SUM(cl.libroCantidad) DESC")
    List<LibrosMasVendidosDTO> librosMasVendidos(Pageable pageable);
}
