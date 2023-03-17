package com.libreriasanjuan.apirestspringboot.controller;

import com.libreriasanjuan.apirestspringboot.dto.LibrosMasVendidosDTO;
import com.libreriasanjuan.apirestspringboot.models.Libro;
import com.libreriasanjuan.apirestspringboot.services.LibroServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api")
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
        Libro libroNuevo = libroServiceImpl.saveLibro(libro);
        if (libroNuevo == null) {
            return ResponseEntity.badRequest().body("Ya existe un libro con ese título");
        } else {
            return ResponseEntity.status(HttpStatus.CREATED).body(libroNuevo);
        }
    }

    @PutMapping("/libros/{id}")
    public ResponseEntity<?> updateById(@PathVariable Long id, @RequestBody Libro libroActualizado) {
        Libro libro = libroServiceImpl.updateById(id, libroActualizado);
        if (libro == null) {
            return ResponseEntity.badRequest().body("Ya existe un libro con ese título");
        } else {
            return ResponseEntity.ok(libro);
        }

    }

    @DeleteMapping("/libros/{id}")
    public ResponseEntity<Libro> deleteById(@PathVariable Long id) {
        Libro libro = libroServiceImpl.deleteById(id);
        return ResponseEntity.ok(libro);
    }
}