package com.libreriasanjuan.apirestspringboot.controller;

import com.libreriasanjuan.apirestspringboot.dto.FacturaDTO;
import com.libreriasanjuan.apirestspringboot.exceptions.ResourceNotFoundException;
import com.libreriasanjuan.apirestspringboot.models.Factura;
import com.libreriasanjuan.apirestspringboot.services.FacturaServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api")
@Slf4j
public class FacturaController {

    private final FacturaServiceImpl facturaServiceImpl;

    @Autowired
    public FacturaController(FacturaServiceImpl facturaServiceImpl) {
        this.facturaServiceImpl = facturaServiceImpl;
    }

    @PostMapping("/facturas")
    public ResponseEntity<?> saveFactura(@RequestBody FacturaDTO facturaDTO) {
        try {
            Factura factura = facturaServiceImpl.saveFactura(facturaDTO);
            log.info("Factura creada: " + factura);
            return ResponseEntity.status(HttpStatus.CREATED).body(factura);
        } catch (ResourceNotFoundException error) {
            log.warn("Error al crear una factura: " + error.getMessage());
            return ResponseEntity.badRequest().body(error.getMessage());
        }

    }

}
