package com.libreriasanjuan.apirestspringboot.mapper;

import com.libreriasanjuan.apirestspringboot.dto.FacturaResponse;
import com.libreriasanjuan.apirestspringboot.models.Factura;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FacturaMapper {
    @Mapping(source = "usuario.usuarioId", target = "usuarioId")
    FacturaResponse BDaDTO(Factura factura);
}
