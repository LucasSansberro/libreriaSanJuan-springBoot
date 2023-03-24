package com.libreriasanjuan.apirestspringboot.mapper;

import com.libreriasanjuan.apirestspringboot.dto.UsuarioDTO;
import com.libreriasanjuan.apirestspringboot.models.Usuario;
import org.mapstruct.Mapper;

import java.util.List;


@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    UsuarioDTO BDaDTO(Usuario usuario);

    List<UsuarioDTO> convertirListaADTO(List<Usuario> usuarios);

}
