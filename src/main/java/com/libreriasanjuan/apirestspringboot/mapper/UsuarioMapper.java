package com.libreriasanjuan.apirestspringboot.mapper;

import com.libreriasanjuan.apirestspringboot.dto.UsuarioDTO;
import com.libreriasanjuan.apirestspringboot.models.Usuario;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UsuarioMapper {

    public UsuarioDTO BDaDTO(Usuario usuario) {
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setUsuarioId(usuario.getUsuarioId());
        usuarioDTO.setUsuarioCorreo(usuario.getUsuarioCorreo());
        usuarioDTO.setAdmin(usuario.getIsAdmin());
        return usuarioDTO;
    }

    public List<UsuarioDTO> convertirListaADTO(List<Usuario> usuarios) {
        return usuarios.stream().map(this::BDaDTO).collect(Collectors.toList());
    }

}
