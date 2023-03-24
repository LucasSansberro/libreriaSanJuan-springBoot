package com.libreriasanjuan.apirestspringboot.mapper;

import com.libreriasanjuan.apirestspringboot.dto.UsuarioDTO;
import com.libreriasanjuan.apirestspringboot.models.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;


@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    @Mapping(source = "isAdmin", target = "admin")
    UsuarioDTO BDaDTO(Usuario usuario);

    List<UsuarioDTO> convertirListaADTO(List<Usuario> usuarios);

}
