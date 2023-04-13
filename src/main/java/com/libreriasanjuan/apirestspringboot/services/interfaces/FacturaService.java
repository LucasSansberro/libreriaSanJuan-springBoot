package com.libreriasanjuan.apirestspringboot.services.interfaces;

import com.libreriasanjuan.apirestspringboot.dto.FacturaDTO;
import com.libreriasanjuan.apirestspringboot.dto.FacturaResponse;

public interface FacturaService {
    FacturaResponse saveFactura(FacturaDTO facturaDTO);

}
