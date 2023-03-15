package com.libreriasanjuan.apirestspringboot.repositories;

import com.libreriasanjuan.apirestspringboot.models.Factura;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FacturaRepository extends JpaRepository<Factura, Long> {
}
