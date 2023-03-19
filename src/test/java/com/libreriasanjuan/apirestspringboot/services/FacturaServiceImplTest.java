package com.libreriasanjuan.apirestspringboot.services;

import com.libreriasanjuan.apirestspringboot.datos.DatosDummy;
import com.libreriasanjuan.apirestspringboot.dto.FacturaDTO;
import com.libreriasanjuan.apirestspringboot.models.Factura;
import com.libreriasanjuan.apirestspringboot.models.Libro;
import com.libreriasanjuan.apirestspringboot.repositories.CompraLibroRepository;
import com.libreriasanjuan.apirestspringboot.repositories.FacturaRepository;
import com.libreriasanjuan.apirestspringboot.repositories.LibroRepository;
import com.libreriasanjuan.apirestspringboot.repositories.UsuarioRepositorio;
import com.libreriasanjuan.apirestspringboot.services.interfaces.FacturaService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@SpringBootTest
class FacturaServiceImplTest {
    private final LibroRepository libroRepository;
    private final UsuarioRepositorio usuarioRepositorio;
    private final CompraLibroRepository compraLibroRepository;
    private final FacturaRepository facturaRepository;
    private final FacturaService facturaService;

    @Autowired
    public FacturaServiceImplTest(LibroRepository libroRepository, UsuarioRepositorio usuarioRepositorio, CompraLibroRepository compraLibroRepository, FacturaRepository facturaRepository, FacturaService facturaService) {
        this.libroRepository = libroRepository;
        this.usuarioRepositorio = usuarioRepositorio;
        this.compraLibroRepository = compraLibroRepository;
        this.facturaRepository = facturaRepository;
        this.facturaService = facturaService;
    }

    @BeforeEach
    void setUp() {
        this.usuarioRepositorio.save(DatosDummy.getUsuarioTest());
        this.libroRepository.save(DatosDummy.getLibroTest());
        this.libroRepository.save(DatosDummy.getLibro2Test());
    }

    @AfterEach
    void tearDown() {
        this.compraLibroRepository.deleteAll();
        this.facturaRepository.deleteAll();
        this.usuarioRepositorio.deleteAll();
        this.libroRepository.deleteAll();
    }

    @Test
    void saveFactura() {
        //GIVEN
        Long usuarioId = this.usuarioRepositorio.findAll().get(0).getUsuarioId();
        int precioTotal = 1100;
        List<Libro> librosComprados = new ArrayList<>();
        librosComprados.add(this.libroRepository.findAll().get(0));
        librosComprados.add(this.libroRepository.findAll().get(1));
        LocalDate fecha = LocalDate.now();
        FacturaDTO facturaDTO = new FacturaDTO(usuarioId, precioTotal, fecha, librosComprados);
        FacturaDTO facturaUsuarioIdInexistente = new FacturaDTO(700L, precioTotal, fecha, librosComprados);
        //WHEN
        Factura factura = this.facturaService.saveFactura(facturaDTO);

        //THEN
        assertThat(factura).isInstanceOf(Factura.class);
        assertThat(this.compraLibroRepository.findAll().size()).isEqualTo(2);
        assertThatThrownBy(() -> this.facturaService.saveFactura(facturaUsuarioIdInexistente)).isInstanceOf(RuntimeException.class);
    }
}