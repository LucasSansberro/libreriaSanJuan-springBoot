package com.libreriasanjuan.apirestspringboot.services;

import com.libreriasanjuan.apirestspringboot.datos.DatosDummy;
import com.libreriasanjuan.apirestspringboot.dto.FacturaDTO;
import com.libreriasanjuan.apirestspringboot.models.CompraLibro;
import com.libreriasanjuan.apirestspringboot.models.Factura;
import com.libreriasanjuan.apirestspringboot.repositories.CompraLibroRepository;
import com.libreriasanjuan.apirestspringboot.repositories.FacturaRepository;
import com.libreriasanjuan.apirestspringboot.repositories.UsuarioRepositorio;
import com.libreriasanjuan.apirestspringboot.services.interfaces.FacturaService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
class FacturaServiceImplTest {
    private CompraLibroRepository compraLibroRepository;
    private FacturaRepository facturaRepository;
    private UsuarioRepositorio usuarioRepositorio;
    private FacturaService facturaService;
    private Factura factura;
    private FacturaDTO facturaDTO;
    private CompraLibro compraLibro;


    @BeforeEach
    void setUp() {
        facturaRepository = mock(FacturaRepository.class);
        usuarioRepositorio = mock(UsuarioRepositorio.class);
        compraLibroRepository = mock(CompraLibroRepository.class);
        facturaService = new FacturaServiceImpl(usuarioRepositorio, facturaRepository, compraLibroRepository);
        factura = DatosDummy.getFacturaTest();
        facturaDTO = DatosDummy.getFacturaDTOTest();
        compraLibro = DatosDummy.getCompraLibroTest();
    }

    @AfterEach
    void tearDown() {

    }

    @Test
    void saveFactura() {
        //GIVEN
        FacturaDTO facturaUsuarioIdInexistente = new FacturaDTO(2L, 1100, LocalDate.now(), facturaDTO.getLibrosComprados());
        when(usuarioRepositorio.findById(1L)).thenReturn(Optional.of(factura.getUsuario()));
        when(facturaRepository.save(any(Factura.class))).thenReturn(factura);
        when(compraLibroRepository.save(any(CompraLibro.class))).thenReturn(compraLibro);

        //WHEN
        Factura factura = this.facturaService.saveFactura(facturaDTO);

        //THEN
        assertThat(factura).isInstanceOf(Factura.class);
        assertThatThrownBy(() -> this.facturaService.saveFactura(facturaUsuarioIdInexistente)).isInstanceOf(RuntimeException.class);
    }
}