package com.libreriasanjuan.apirestspringboot.controller;

import com.libreriasanjuan.apirestspringboot.dto.LibrosMasVendidosDTO;
import com.libreriasanjuan.apirestspringboot.exceptions.ResourceNotFoundException;
import com.libreriasanjuan.apirestspringboot.models.Libro;
import com.libreriasanjuan.apirestspringboot.services.LibroServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api")
@Api(value = "Libros", tags = "CRUD de libros")
public class LibroController {
    private final LibroServiceImpl libroServiceImpl;

    @Autowired
    public LibroController(LibroServiceImpl libroServiceImpl) {
        this.libroServiceImpl = libroServiceImpl;
    }

    @GetMapping("/libros")
    @ApiOperation(value = "Traer todos los libros")
    public ResponseEntity<List<Libro>> getAllLibros() {
        return ResponseEntity.ok(libroServiceImpl.getAllLibros());
    }

    @GetMapping("/librosMasVendidos")
    @ApiOperation(value = "Traer todos los libros que los clientes han comprado, ordenados por unidades vendidas")
    public ResponseEntity<List<LibrosMasVendidosDTO>> getMasVendidos() {
        return ResponseEntity.ok(libroServiceImpl.getMasVendidos());
    }

    @PostMapping("/libros")
    @ApiOperation(value = "Crear un nuevo libro")
    public ResponseEntity<Libro> saveLibro(@RequestBody Libro libro) throws DataIntegrityViolationException {
        return ResponseEntity.status(HttpStatus.CREATED).body(libroServiceImpl.saveLibro(libro));
    }

    @PutMapping("/libros/{id}")
    @ApiOperation(value = "Editar un libro")
    public ResponseEntity<Libro> updateById(@PathVariable Long id, @RequestBody Libro libroActualizado) throws ResourceNotFoundException, DataIntegrityViolationException {
        return ResponseEntity.ok(libroServiceImpl.updateById(id, libroActualizado));
    }

    @DeleteMapping("/libros/{id}")
    @ApiOperation(value = "Eliminar un libro")
    public ResponseEntity<Libro> deleteById(@PathVariable Long id) throws ResourceNotFoundException {
        return ResponseEntity.ok(libroServiceImpl.deleteById(id));
    }
}