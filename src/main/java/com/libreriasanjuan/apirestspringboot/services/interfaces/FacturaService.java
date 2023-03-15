package com.libreriasanjuan.apirestspringboot.services.interfaces;

import com.libreriasanjuan.apirestspringboot.dto.FacturaDTO;
import com.libreriasanjuan.apirestspringboot.models.Factura;
import org.springframework.http.ResponseEntity;

public interface FacturaService {
    ResponseEntity<Factura> saveFactura(FacturaDTO facturaDTO);

}
