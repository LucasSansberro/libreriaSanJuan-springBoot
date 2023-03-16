package com.libreriasanjuan.apirestspringboot.controller;

import com.libreriasanjuan.apirestspringboot.dto.LibrosMasVendidosDTO;
import com.libreriasanjuan.apirestspringboot.models.Libro;
import com.libreriasanjuan.apirestspringboot.services.LibroServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
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
        return libroServiceImpl.getAllLibros();
    }

    @GetMapping("/librosMasVendidos")
    public ResponseEntity<List<LibrosMasVendidosDTO>> getMasVendidos(){
        return libroServiceImpl.getMasVendidos();
    }

    @PostMapping("/libros")
    public ResponseEntity<?> saveLibro(@RequestBody Libro libro) {
        return libroServiceImpl.saveLibro(libro);
    }

    @PutMapping("/libros/{id}")
    public ResponseEntity<?> updateById(@PathVariable Long id, @RequestBody Libro libroActualizado) {
        return libroServiceImpl.updateById(id, libroActualizado);
    }

    @DeleteMapping("/libros/{id}")
    public ResponseEntity<Libro> deleteById(@PathVariable Long id) {
        return libroServiceImpl.deleteById(id);
    }
}

//TODO Pasar las responseEntity al controlador