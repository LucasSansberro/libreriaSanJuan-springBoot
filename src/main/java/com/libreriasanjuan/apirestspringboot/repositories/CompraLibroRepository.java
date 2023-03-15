package com.libreriasanjuan.apirestspringboot.repositories;

import com.libreriasanjuan.apirestspringboot.models.CompraLibro;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompraLibroRepository extends JpaRepository<CompraLibro, Long> {
}
