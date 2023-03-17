package com.libreriasanjuan.apirestspringboot.controller;

import com.libreriasanjuan.apirestspringboot.dto.FacturaDTO;
import com.libreriasanjuan.apirestspringboot.models.Factura;
import com.libreriasanjuan.apirestspringboot.services.FacturaServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api")
public class FacturaController {

    private final FacturaServiceImpl facturaServiceImpl;

    @Autowired
    public FacturaController(FacturaServiceImpl facturaServiceImpl) {
        this.facturaServiceImpl = facturaServiceImpl;
    }

    @PostMapping("/facturas")
    public ResponseEntity<Factura> saveFactura(@RequestBody FacturaDTO facturaDTO) {
        Factura factura = facturaServiceImpl.saveFactura(facturaDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(factura);
    }

}
