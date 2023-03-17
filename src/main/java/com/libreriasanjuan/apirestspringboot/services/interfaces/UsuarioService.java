package com.libreriasanjuan.apirestspringboot.services.interfaces;

import com.libreriasanjuan.apirestspringboot.dto.UsuarioDTO;
import com.libreriasanjuan.apirestspringboot.models.Usuario;

import java.util.List;

public interface UsuarioService {
    List<UsuarioDTO> getAllUsers();

    UsuarioDTO loginUser(Usuario usuarioLogin);

    UsuarioDTO saveUser(Usuario usuarioRegistro);

    UsuarioDTO updateById(Long id, Usuario usuarioActualizado);

    UsuarioDTO deleteById(Long id);
}
