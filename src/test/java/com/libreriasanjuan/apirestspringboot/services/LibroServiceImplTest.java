package com.libreriasanjuan.apirestspringboot.services;

import com.libreriasanjuan.apirestspringboot.datos.DatosDummy;
import com.libreriasanjuan.apirestspringboot.dto.LibrosMasVendidosDTO;
import com.libreriasanjuan.apirestspringboot.models.CompraLibro;
import com.libreriasanjuan.apirestspringboot.models.Factura;
import com.libreriasanjuan.apirestspringboot.models.Libro;
import com.libreriasanjuan.apirestspringboot.models.Usuario;
import com.libreriasanjuan.apirestspringboot.repositories.CompraLibroRepository;
import com.libreriasanjuan.apirestspringboot.repositories.FacturaRepository;
import com.libreriasanjuan.apirestspringboot.repositories.LibroRepository;
import com.libreriasanjuan.apirestspringboot.repositories.UsuarioRepositorio;
import com.libreriasanjuan.apirestspringboot.services.interfaces.LibroService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@SpringBootTest
class LibroServiceImplTest {
    private final LibroRepository libroRepository;
    private final UsuarioRepositorio usuarioRepositorio;
    private final CompraLibroRepository compraLibroRepository;
    private final FacturaRepository facturaRepository;
    private final LibroService libroService;

    @Autowired
    public LibroServiceImplTest(LibroRepository libroRepository, UsuarioRepositorio usuarioRepositorio, CompraLibroRepository compraLibroRepository, FacturaRepository facturaRepository, LibroService libroService) {
        this.libroRepository = libroRepository;
        this.usuarioRepositorio = usuarioRepositorio;
        this.compraLibroRepository = compraLibroRepository;
        this.facturaRepository = facturaRepository;
        this.libroService = libroService;
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
    void getAllLibros() {
        //GIVEN
        //BEFORE EACH

        //WHEN
        List<Libro> libros = this.libroService.getAllLibros();

        //THEN
        assertThat(libros.isEmpty()).isFalse();
        assertThat(libros.size()).isEqualTo(2);
    }

    @Test
    void getMasVendidos() {
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
        List<LibrosMasVendidosDTO> librosMasVendidos = this.libroService.getMasVendidos();

        //THEN
        assertThat(librosMasVendidos.isEmpty()).isFalse();
        assertThat(librosMasVendidos.size()).isEqualTo(2);

        this.compraLibroRepository.deleteAll();
        this.facturaRepository.deleteAll();
        this.usuarioRepositorio.deleteAll();

    }

    @Test
    void saveLibro() {
        //GIVEN
        Libro nuevoLibro = Libro.builder().libroPrecio(500).libroTitulo("LibroTest3").libroAutor("TestMan").libroPortada("PortadaBonita.jpg").build();
        Libro libroRepetido = DatosDummy.getLibroTest();

        //WHEN
        Libro libroCreado = this.libroService.saveLibro(nuevoLibro);
        Libro libroNoCreado = this.libroService.saveLibro(libroRepetido);

        //THEN
        assertThat(libroCreado.getLibroId()).isInstanceOf(Long.class);
        assertThat(libroNoCreado).isEqualTo(null);
    }

    @Test
    void updateById() {
        //GIVEN
        Libro libroOriginal = this.libroRepository.findAll().get(0);
        Libro cambiosLibro = Libro.builder().libroPrecio(500).libroTitulo("LibroTest3").libroAutor("TestMan").libroPortada("PortadaBonita.jpg").build();
        Libro libroRepetido = DatosDummy.getLibro2Test();

        //WHEN
        Libro libroCambiado = this.libroService.updateById(libroOriginal.getLibroId(), cambiosLibro);
        Libro libroErrorRepetido = this.libroService.updateById(libroOriginal.getLibroId(), libroRepetido);

        //THEN
        assertThat(libroCambiado.getLibroTitulo().equals(cambiosLibro.getLibroTitulo())).isTrue();
        assertThat(libroCambiado.getLibroId().equals(libroOriginal.getLibroId())).isTrue();
        assertThat(libroErrorRepetido).isEqualTo(null);
        assertThatThrownBy(() -> this.libroService.updateById(80L, cambiosLibro)).isInstanceOf(RuntimeException.class);
    }

    @Test
    void deleteById() {
        //GIVEN
        Libro libroABorrar = this.libroRepository.findAll().get(0);
        Long idLibroABorrar = libroABorrar.getLibroId();
        Long idErroneo = 80L;

        //WHEN
        Libro libroBorrado = this.libroService.deleteById(idLibroABorrar);

        //THEN
        assertThat(libroBorrado).isInstanceOf(Libro.class);
        assertThat(libroBorrado.getLibroTitulo().equals(libroABorrar.getLibroTitulo())).isTrue();
        assertThatThrownBy(() -> this.libroService.deleteById(idErroneo));
    }
}