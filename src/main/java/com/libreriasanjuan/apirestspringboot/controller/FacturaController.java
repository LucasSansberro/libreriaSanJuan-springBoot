package com.libreriasanjuan.apirestspringboot.controller;

import com.libreriasanjuan.apirestspringboot.dto.FacturaDTO;
import com.libreriasanjuan.apirestspringboot.exceptions.ResourceNotFoundException;
import com.libreriasanjuan.apirestspringboot.models.Factura;
import com.libreriasanjuan.apirestspringboot.services.FacturaServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api")
@Api(value = "Facturas", tags = "Generación de facturas")
public class FacturaController {

    private final FacturaServiceImpl facturaServiceImpl;

    @Autowired
    public FacturaController(FacturaServiceImpl facturaServiceImpl) {
        this.facturaServiceImpl = facturaServiceImpl;
    }

    @PostMapping("/facturas")
    @ApiOperation(value = "Generar una factura con los libros comprados")
    public ResponseEntity<Factura> saveFactura(@RequestBody FacturaDTO facturaDTO) throws ResourceNotFoundException {
        return ResponseEntity.status(HttpStatus.CREATED).body(facturaServiceImpl.saveFactura(facturaDTO));
    }

}
