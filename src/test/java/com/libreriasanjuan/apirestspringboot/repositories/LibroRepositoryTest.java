package com.libreriasanjuan.apirestspringboot.repositories;

import com.libreriasanjuan.apirestspringboot.datos.DatosDummy;
import com.libreriasanjuan.apirestspringboot.dto.LibrosMasVendidosDTO;
import com.libreriasanjuan.apirestspringboot.models.CompraLibro;
import com.libreriasanjuan.apirestspringboot.models.Factura;
import com.libreriasanjuan.apirestspringboot.models.Libro;
import com.libreriasanjuan.apirestspringboot.models.Usuario;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class LibroRepositoryTest {
    private final LibroRepository libroRepository;
    private final UsuarioRepositorio usuarioRepositorio;
    private final CompraLibroRepository compraLibroRepository;

    private final FacturaRepository facturaRepository;

    @Autowired
    LibroRepositoryTest(LibroRepository libroRepository, UsuarioRepositorio usuarioRepositorio, CompraLibroRepository compraLibroRepository, FacturaRepository facturaRepository) {
        this.libroRepository = libroRepository;
        this.usuarioRepositorio = usuarioRepositorio;
        this.compraLibroRepository = compraLibroRepository;
        this.facturaRepository = facturaRepository;
    }

    @BeforeEach
    void setUp() {
        this.libroRepository.save(DatosDummy.getLibroTest());
        this.libroRepository.save(DatosDummy.getLibro2Test());
    }

    @AfterEach
    void tearDown() {
        this.libroRepository.deleteAll();
    }

    @Test
    void findByLibroTitulo() {
        //GIVEN
        //BEFORE EACH

        //WHEN
        Optional<Libro> libroBuscado = this.libroRepository.findByLibroTitulo(DatosDummy.getLibroTest().getLibroTitulo());

        //THEN
        assertThat(libroBuscado.isPresent()).isTrue();
        assertThat(libroBuscado.get().getLibroTitulo().equals(DatosDummy.getLibroTest().getLibroTitulo())).isTrue();
    }

    @Test
    void librosMasVendidos() {
        //GIVEN
        this.usuarioRepositorio.save(DatosDummy.getUsuarioTest());
        Libro libroExistente = this.libroRepository.findAll().get(0);
        Libro libroExistente2 = this.libroRepository.findAll().get(1);
        Usuario usuarioExistente = this.usuarioRepositorio.findAll().get(0);
        Factura facturaExistenteDummy = DatosDummy.getFacturaTest();
        Factura facturaIdActualizado = Factura.builder().facturaId(1L).usuario(usuarioExistente).precioTotal(facturaExistenteDummy.getPrecioTotal()).fecha(facturaExistenteDummy.getFecha()).build();
        this.facturaRepository.save(facturaIdActualizado);
        Factura facturaExistente = this.facturaRepository.findAll().get(0);
        CompraLibro compraLibroIdActualizado = CompraLibro.builder().compraId(1L).factura(facturaExistente).libro(libroExistente).libroCantidad(1).precioTotal(500).build();
        CompraLibro compraLibroId2Actualizado = CompraLibro.builder().compraId(2L).factura(facturaExistente).libro(libroExistente2).libroCantidad(1).precioTotal(600).build();
        this.compraLibroRepository.save(compraLibroIdActualizado);
        this.compraLibroRepository.save(compraLibroId2Actualizado);

        //WHEN
        List<LibrosMasVendidosDTO> librosMasVendidosDTOS = this.libroRepository.librosMasVendidos(PageRequest.of(0, 2));

        //THEN
        assertThat(librosMasVendidosDTOS.size()).isEqualTo(2);
    }
}