package com.libreriasanjuan.apirestspringboot.services;

import com.libreriasanjuan.apirestspringboot.dto.LibrosMasVendidosDTO;
import com.libreriasanjuan.apirestspringboot.exceptions.ResourceNotFoundException;
import com.libreriasanjuan.apirestspringboot.models.Libro;
import com.libreriasanjuan.apirestspringboot.repositories.LibroRepository;
import com.libreriasanjuan.apirestspringboot.services.interfaces.LibroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class LibroServiceImpl implements LibroService {
    private final LibroRepository repositorio;

    @Autowired
    public LibroServiceImpl(LibroRepository repositorio) {
        this.repositorio = repositorio;
    }

    @Override
    public ResponseEntity<List<Libro>> getAllLibros() {
        List<Libro> libros = repositorio.findAll();
        return ResponseEntity.ok(libros);
    }

    @Override
    public ResponseEntity<List<LibrosMasVendidosDTO>> getMasVendidos(){
        int numeroPagina = 0;
        int elementosPagina = 3;
        Pageable pageable = PageRequest.of(numeroPagina, elementosPagina);
        List<LibrosMasVendidosDTO> librosMasVendidos = repositorio.librosMasVendidos(pageable);
        return ResponseEntity.ok(librosMasVendidos);
    }

    @Override
    public ResponseEntity<?> saveLibro(Libro libro) {
        if (repositorio.findByLibroTitulo(libro.getLibroTitulo()).isEmpty()) {
            Libro libroNuevo = repositorio.save(libro);
            return ResponseEntity.status(HttpStatus.CREATED).body(libroNuevo);
        } else {
            return ResponseEntity.badRequest().body("Ya existe un libro con ese título");
        }
    }

    @Override
    public ResponseEntity<?> updateById(Long id, Libro libroActualizado) {
        Libro libro = repositorio.findById(id).orElseThrow(() -> new ResourceNotFoundException("No existe un libro con el id:" + id));
        if (repositorio.findByLibroTitulo(libroActualizado.getLibroTitulo()).isEmpty() || id.equals(repositorio.findByLibroTitulo(libroActualizado.getLibroTitulo()).get().getLibroId())) {
            libro.setLibroPrecio(libroActualizado.getLibroPrecio());
            libro.setLibroTitulo(libroActualizado.getLibroTitulo());
            libro.setLibroAutor(libroActualizado.getLibroAutor());
            libro.setLibroPortada(libroActualizado.getLibroPortada());
            repositorio.save(libro);
            return ResponseEntity.ok(libro);
        } else {
            return ResponseEntity.badRequest().body("Ya existe un libro con ese título");
        }
    }

    @Override
    public ResponseEntity<Libro> deleteById(Long id) {
        Libro libro = repositorio.findById(id).orElseThrow(() -> new ResourceNotFoundException("No existe un libro con el id:" + id));
        repositorio.deleteById(id);
        return ResponseEntity.ok(libro);
    }
}
