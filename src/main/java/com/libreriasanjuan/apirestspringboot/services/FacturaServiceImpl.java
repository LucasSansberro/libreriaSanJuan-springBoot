package com.libreriasanjuan.apirestspringboot.services;

import com.libreriasanjuan.apirestspringboot.dto.FacturaDTO;
import com.libreriasanjuan.apirestspringboot.exceptions.ResourceNotFoundException;
import com.libreriasanjuan.apirestspringboot.models.CompraLibro;
import com.libreriasanjuan.apirestspringboot.models.Factura;
import com.libreriasanjuan.apirestspringboot.models.Usuario;
import com.libreriasanjuan.apirestspringboot.repositories.CompraLibroRepository;
import com.libreriasanjuan.apirestspringboot.repositories.FacturaRepository;
import com.libreriasanjuan.apirestspringboot.repositories.UsuarioRepositorio;
import com.libreriasanjuan.apirestspringboot.services.interfaces.FacturaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;

@Service
@Transactional
public class FacturaServiceImpl implements FacturaService {
    private final UsuarioRepositorio usuarioRepositorio;
    private final FacturaRepository facturaRepository;
    private final CompraLibroRepository compraLibroRepository;

    @Autowired
    public FacturaServiceImpl(UsuarioRepositorio usuarioRepositorio, FacturaRepository facturaRepository, CompraLibroRepository compraLibroRepository) {
        this.usuarioRepositorio = usuarioRepositorio;
        this.facturaRepository = facturaRepository;
        this.compraLibroRepository = compraLibroRepository;
    }


    @Override
    public Factura saveFactura(FacturaDTO facturaDTO) {
        Long usuarioId = facturaDTO.getUsuarioId();
        Usuario usuario = usuarioRepositorio.findById(usuarioId).orElseThrow(() -> new ResourceNotFoundException("No existe un usuario con el id:" + usuarioId));
        Factura factura = Factura.builder().usuario(usuario).precioTotal(facturaDTO.getPrecioTotal()).fecha(LocalDate.now()).build();
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
        return factura;
    }
}
