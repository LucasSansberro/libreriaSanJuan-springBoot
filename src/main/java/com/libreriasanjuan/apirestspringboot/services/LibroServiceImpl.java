package com.libreriasanjuan.apirestspringboot.services;

import com.libreriasanjuan.apirestspringboot.dto.LibroResponse;
import com.libreriasanjuan.apirestspringboot.dto.LibrosMasVendidosDTO;
import com.libreriasanjuan.apirestspringboot.exceptions.ResourceNotFoundException;
import com.libreriasanjuan.apirestspringboot.mapper.LibroMapper;
import com.libreriasanjuan.apirestspringboot.models.Libro;
import com.libreriasanjuan.apirestspringboot.repositories.LibroRepository;
import com.libreriasanjuan.apirestspringboot.services.interfaces.LibroService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.PersistenceException;
import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class LibroServiceImpl implements LibroService {
    private final LibroRepository repositorio;
    private final LibroMapper libroMapper;

    @Override
    public List<LibroResponse> getAllLibros() {
        return libroMapper.convertirListaADTO(repositorio.findAll());
    }

    @Override
    public List<LibrosMasVendidosDTO> getMasVendidos() {
        int numeroPagina = 0;
        int elementosPagina = 3;
        Pageable pageable = PageRequest.of(numeroPagina, elementosPagina);
        return repositorio.librosMasVendidos(pageable);
    }

    @Transactional
    @Override
    public LibroResponse saveLibro(Libro libro) {
        if (repositorio.findByLibroTitulo(libro.getLibroTitulo()).isPresent()) {
            throw new DataIntegrityViolationException("Ya existe un libro con ese título");
        } else {
            try {
                Libro libroCreado = repositorio.save(libro);
                log.info("Nuevo libro creado: " + libro);
                return libroMapper.BDaDTO(libroCreado);
            } catch (PersistenceException ex) {
                throw new PersistenceException("Error guardando un libro en la BD: " + ex);
            }

        }
    }

    @Transactional
    @Override
    public LibroResponse updateById(Long id, Libro libroActualizado) {
        Libro libro = repositorio.findById(id).orElseThrow(() -> new ResourceNotFoundException("No existe un libro con el id:" + id));
        if (repositorio.findByLibroTitulo(libroActualizado.getLibroTitulo()).isPresent() && !id.equals(repositorio.findByLibroTitulo(libroActualizado.getLibroTitulo()).get().getLibroId())) {
            throw new DataIntegrityViolationException("Ya existe un libro con ese título");
        } else {
            try {
                libro.setLibroPrecio(libroActualizado.getLibroPrecio());
                libro.setLibroTitulo(libroActualizado.getLibroTitulo());
                libro.setLibroAutor(libroActualizado.getLibroAutor());
                libro.setLibroPortada(libroActualizado.getLibroPortada());
                Libro libroActualizadoYGuardado = repositorio.save(libro);
                log.info("Libro con ID " + id + " actualizado: " + libroActualizadoYGuardado);
                return libroMapper.BDaDTO(libroActualizadoYGuardado);
            } catch (PersistenceException ex) {
                throw new PersistenceException("Error con la DB al actualizar un libro: " + ex);
            }
        }
    }

    @Transactional
    @Override
    public LibroResponse deleteById(Long id) {
        Libro libro = repositorio.findById(id).orElseThrow(() -> new ResourceNotFoundException("No existe un libro con el id:" + id));
        try {
            repositorio.deleteById(id);
            log.info("Libro con ID " + id + " eliminado");
            return libroMapper.BDaDTO(libro);
        } catch (PersistenceException ex) {
            throw new PersistenceException("Error con la DB al eliminar un libro: " + ex);
        }

    }
}
