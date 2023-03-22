package com.libreriasanjuan.apirestspringboot.services;

import com.libreriasanjuan.apirestspringboot.datos.DatosDummy;
import com.libreriasanjuan.apirestspringboot.dto.LibrosMasVendidosDTO;
import com.libreriasanjuan.apirestspringboot.models.Libro;
import com.libreriasanjuan.apirestspringboot.repositories.LibroRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
class LibroServiceImplTest {
    private LibroRepository libroRepository;
    private LibroServiceImpl libroService;
    private Libro libro1;
    private Libro libro2;

    @BeforeEach
    void setUp() {
        libroRepository = mock(LibroRepository.class);
        libroService = new LibroServiceImpl(libroRepository);
        libro1 = DatosDummy.getLibroTest();
        libro2 = DatosDummy.getLibro2Test();
    }

    @AfterEach
    void tearDown() {

    }

    @Test
    void getAllLibros() {
        //GIVEN
        when(libroRepository.findAll()).thenReturn(Arrays.asList(libro1, libro2));

        //WHEN
        List<Libro> libros = this.libroService.getAllLibros();

        //THEN
        assertThat(libros.isEmpty()).isFalse();
        assertThat(libros.size()).isEqualTo(2);
    }

    @Test
    void getMasVendidos() {
        //GIVEN
        LibrosMasVendidosDTO masVendidoTest = new LibrosMasVendidosDTO(1L, 500, "LibroTest", "TestMan", "PortadaBonita.jpg", 5L);
        LibrosMasVendidosDTO masVendido2Test = new LibrosMasVendidosDTO(2L, 600, "LibroTest2", "TestMan", "PortadaBonita.jpg", 6L);
        when(libroRepository.librosMasVendidos(PageRequest.of(0, 3))).thenReturn(Arrays.asList(masVendidoTest, masVendido2Test));

        //WHEN
        List<LibrosMasVendidosDTO> librosMasVendidos = this.libroService.getMasVendidos();

        //THEN
        assertThat(librosMasVendidos.isEmpty()).isFalse();
        assertThat(librosMasVendidos.size()).isEqualTo(2);
    }

    @Test
    void saveLibro() {
        //GIVEN
        when(libroRepository.findByLibroTitulo(libro1.getLibroTitulo())).thenReturn(Optional.empty());
        when(libroRepository.findByLibroTitulo(libro2.getLibroTitulo())).thenReturn(Optional.of(new Libro()));
        when(libroRepository.save(libro1)).thenReturn(libro1);

        //WHEN
        Libro libroCreado = this.libroService.saveLibro(libro1);

        //THEN
        assertThat(libroCreado).isInstanceOf(Libro.class);
        assertThatThrownBy(() -> this.libroService.saveLibro(libro2)).isInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    void updateById() {
        //GIVEN
        when(libroRepository.findById(1L)).thenReturn(Optional.of(libro1));
        when(libroRepository.findById(2L)).thenReturn(Optional.of(libro2));
        when(libroRepository.findByLibroTitulo(libro1.getLibroTitulo())).thenReturn(Optional.empty());
        when(libroRepository.findByLibroTitulo(libro2.getLibroTitulo())).thenReturn(Optional.of(new Libro()));
        when(libroRepository.save(libro1)).thenReturn(libro1);

        //WHEN
        Libro libroCambiado = this.libroService.updateById(1L, libro1);

        //THEN
        assertThat(libroCambiado).isInstanceOf(Libro.class);
        assertThatThrownBy(() -> this.libroService.updateById(2L, libro2)).isInstanceOf(DataIntegrityViolationException.class);
        assertThatThrownBy(() -> this.libroService.updateById(80L, libro1)).isInstanceOf(RuntimeException.class);
    }

    @Test
    void deleteById() {
        //GIVEN
        when(libroRepository.findById(1L)).thenReturn(Optional.of(libro1));

        //WHEN
        Libro libroBorrado = this.libroService.deleteById(1L);

        //THEN
        assertThat(libroBorrado).isInstanceOf(Libro.class);
        assertThatThrownBy(() -> this.libroService.deleteById(2L));
    }
}