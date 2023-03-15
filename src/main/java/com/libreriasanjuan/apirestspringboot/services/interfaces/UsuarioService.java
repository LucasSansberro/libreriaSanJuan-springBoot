package com.libreriasanjuan.apirestspringboot.services.interfaces;

import com.libreriasanjuan.apirestspringboot.dto.UsuarioDTO;
import com.libreriasanjuan.apirestspringboot.models.Usuario;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UsuarioService {
    ResponseEntity<List<UsuarioDTO>> getAllUsers();

    ResponseEntity<UsuarioDTO> getUserByMail(Usuario usuarioLogin);

    ResponseEntity<?> saveUser(Usuario usuarioRegistro);


    ResponseEntity<UsuarioDTO> updateById(Long id, Usuario usuarioActualizado);

    ResponseEntity<UsuarioDTO> deleteById(Long id);
}
