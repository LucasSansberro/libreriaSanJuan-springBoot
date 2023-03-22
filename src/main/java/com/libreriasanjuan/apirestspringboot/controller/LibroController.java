package com.libreriasanjuan.apirestspringboot.controller;

import com.libreriasanjuan.apirestspringboot.dto.LibrosMasVendidosDTO;
import com.libreriasanjuan.apirestspringboot.exceptions.ResourceNotFoundException;
import com.libreriasanjuan.apirestspringboot.models.Libro;
import com.libreriasanjuan.apirestspringboot.services.LibroServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api")
@Slf4j
public class LibroController {
    private final LibroServiceImpl libroServiceImpl;

    @Autowired
    public LibroController(LibroServiceImpl libroServiceImpl) {
        this.libroServiceImpl = libroServiceImpl;
    }

    @GetMapping("/libros")
    public ResponseEntity<List<Libro>> getAllLibros() {
        List<Libro> listaLibros = libroServiceImpl.getAllLibros();
        return ResponseEntity.ok(listaLibros);
    }

    @GetMapping("/librosMasVendidos")
    public ResponseEntity<List<LibrosMasVendidosDTO>> getMasVendidos() {
        List<LibrosMasVendidosDTO> listaLibrosMasVendidos = libroServiceImpl.getMasVendidos();
        return ResponseEntity.ok(listaLibrosMasVendidos);
    }

    @PostMapping("/libros")
    public ResponseEntity<?> saveLibro(@RequestBody Libro libro) {
        try {
            Libro libroNuevo = libroServiceImpl.saveLibro(libro);
            log.info("Nuevo libro creado: " + libroNuevo);
            return ResponseEntity.status(HttpStatus.CREATED).body(libroNuevo);
        } catch (DataIntegrityViolationException error) {
            log.warn("Error creando libro: " + error.getMessage());
            return ResponseEntity.badRequest().body(error.getMessage());
        }
    }

    @PutMapping("/libros/{id}")
    public ResponseEntity<?> updateById(@PathVariable Long id, @RequestBody Libro libroActualizado) {
        try {
            Libro libro = libroServiceImpl.updateById(id, libroActualizado);
            log.info("Libro con ID " + id + " actualizado: " + libro);
            return ResponseEntity.ok(libro);
        } catch (ResourceNotFoundException | DataIntegrityViolationException error) {
            log.warn("Error al actualizar el libro con ID: " + id + " - " + error.getMessage());
            return ResponseEntity.badRequest().body("Ya existe un libro con ese título");
        }
    }

    @DeleteMapping("/libros/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        try {
            Libro libro = libroServiceImpl.deleteById(id);
            log.info("Libro con ID " + id + " eliminado");
            return ResponseEntity.ok(libro);
        } catch (ResourceNotFoundException error) {
            log.warn("Error al eliminar el libro con ID: " + id + " - " + error.getMessage());
            return ResponseEntity.badRequest().body(error.getMessage());
        }

    }
}