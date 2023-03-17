package com.libreriasanjuan.apirestspringboot.services.interfaces;

import com.libreriasanjuan.apirestspringboot.dto.FacturaDTO;
import com.libreriasanjuan.apirestspringboot.models.Factura;

public interface FacturaService {
    Factura saveFactura(FacturaDTO facturaDTO);

}
