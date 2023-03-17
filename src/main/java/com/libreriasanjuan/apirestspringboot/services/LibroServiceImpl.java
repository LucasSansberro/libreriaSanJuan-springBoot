package com.libreriasanjuan.apirestspringboot.services;

import com.libreriasanjuan.apirestspringboot.dto.LibrosMasVendidosDTO;
import com.libreriasanjuan.apirestspringboot.exceptions.ResourceNotFoundException;
import com.libreriasanjuan.apirestspringboot.models.Libro;
import com.libreriasanjuan.apirestspringboot.repositories.LibroRepository;
import com.libreriasanjuan.apirestspringboot.services.interfaces.LibroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    public List<Libro> getAllLibros() {
        return repositorio.findAll();
    }

    @Override
    public List<LibrosMasVendidosDTO> getMasVendidos() {
        int numeroPagina = 0;
        int elementosPagina = 3;
        Pageable pageable = PageRequest.of(numeroPagina, elementosPagina);
        return repositorio.librosMasVendidos(pageable);
    }

    @Override
    public Libro saveLibro(Libro libro) {
        if (repositorio.findByLibroTitulo(libro.getLibroTitulo()).isEmpty()) {
            return repositorio.save(libro);
        } else {
            return null;
        }
    }

    @Override
    public Libro updateById(Long id, Libro libroActualizado) {
        Libro libro = repositorio.findById(id).orElseThrow(() -> new ResourceNotFoundException("No existe un libro con el id:" + id));
        if (repositorio.findByLibroTitulo(libroActualizado.getLibroTitulo()).isEmpty() || id.equals(repositorio.findByLibroTitulo(libroActualizado.getLibroTitulo()).get().getLibroId())) {
            libro.setLibroPrecio(libroActualizado.getLibroPrecio());
            libro.setLibroTitulo(libroActualizado.getLibroTitulo());
            libro.setLibroAutor(libroActualizado.getLibroAutor());
            libro.setLibroPortada(libroActualizado.getLibroPortada());
            return repositorio.save(libro);
        } else {
            return null;
        }
    }

    @Override
    public Libro deleteById(Long id) {
        Libro libro = repositorio.findById(id).orElseThrow(() -> new ResourceNotFoundException("No existe un libro con el id:" + id));
        repositorio.deleteById(id);
        return libro;
    }
}
