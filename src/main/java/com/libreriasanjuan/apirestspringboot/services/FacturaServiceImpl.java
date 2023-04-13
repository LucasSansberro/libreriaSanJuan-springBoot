package com.libreriasanjuan.apirestspringboot.services;

import com.libreriasanjuan.apirestspringboot.dto.FacturaDTO;
import com.libreriasanjuan.apirestspringboot.dto.FacturaResponse;
import com.libreriasanjuan.apirestspringboot.exceptions.ResourceNotFoundException;
import com.libreriasanjuan.apirestspringboot.mapper.FacturaMapper;
import com.libreriasanjuan.apirestspringboot.models.CompraLibro;
import com.libreriasanjuan.apirestspringboot.models.Factura;
import com.libreriasanjuan.apirestspringboot.models.Usuario;
import com.libreriasanjuan.apirestspringboot.repositories.CompraLibroRepository;
import com.libreriasanjuan.apirestspringboot.repositories.FacturaRepository;
import com.libreriasanjuan.apirestspringboot.repositories.UsuarioRepositorio;
import com.libreriasanjuan.apirestspringboot.services.interfaces.FacturaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.persistence.PersistenceException;
import javax.transaction.Transactional;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Slf4j
public class FacturaServiceImpl implements FacturaService {
    private final UsuarioRepositorio usuarioRepositorio;
    private final FacturaRepository facturaRepository;
    private final CompraLibroRepository compraLibroRepository;
    private final FacturaMapper facturaMapper;

    @Transactional
    @Override
    public FacturaResponse saveFactura(FacturaDTO facturaDTO) {
        Usuario usuario = usuarioRepositorio.findById(facturaDTO.getUsuarioId()).orElseThrow(() -> new ResourceNotFoundException("No existe un usuario con el id:" + facturaDTO.getUsuarioId()));
        Factura factura = Factura.builder()
                .usuario(usuario)
                .precioTotal(facturaDTO.getPrecioTotal())
                .fecha(LocalDate.now())
                .build();
        try {
            facturaRepository.save(factura);
            facturaDTO.getLibrosComprados().forEach(libro -> {
                CompraLibro libroComprado = CompraLibro.builder()
                        .factura(factura)
                        .libro(libro)
                        .libroCantidad(libro.getLibroCantidad())
                        .precioTotal(libro.getLibroCantidad() * libro.getLibroPrecio())
                        .build();
                compraLibroRepository.save(libroComprado);
            });
            log.info("Factura creada: " + factura);
            return facturaMapper.BDaDTO(factura);
        } catch (PersistenceException ex) {
            throw new PersistenceException("Error guardando una factura en la BD: " + ex);
        }
    }
}
