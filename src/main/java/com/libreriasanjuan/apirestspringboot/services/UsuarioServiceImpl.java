package com.libreriasanjuan.apirestspringboot.services;

import com.libreriasanjuan.apirestspringboot.dto.UsuarioDTO;
import com.libreriasanjuan.apirestspringboot.exceptions.AuthenticationErrorException;
import com.libreriasanjuan.apirestspringboot.exceptions.ResourceNotFoundException;
import com.libreriasanjuan.apirestspringboot.mapper.UsuarioMapper;
import com.libreriasanjuan.apirestspringboot.models.Usuario;
import com.libreriasanjuan.apirestspringboot.repositories.UsuarioRepositorio;
import com.libreriasanjuan.apirestspringboot.services.interfaces.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepositorio repositorio;
    private final UsuarioMapper usuarioMapper;

    @Autowired
    public UsuarioServiceImpl(UsuarioRepositorio usuarioRepositorio, UsuarioMapper usuarioMapper) {
        this.repositorio = usuarioRepositorio;
        this.usuarioMapper = usuarioMapper;
    }

    @Override
    public ResponseEntity<List<UsuarioDTO>> getAllUsers() {
        List<Usuario> usuariosBD = repositorio.findAll();
        List<UsuarioDTO> usuariosDTO = usuarioMapper.convertirListaADTO(usuariosBD);
        return ResponseEntity.ok(usuariosDTO);
    }

    @Override
    public ResponseEntity<UsuarioDTO> getUserByMail(Usuario usuarioLogin) {
        if (!usuarioLogin.getUsuarioCorreo().matches("^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")) {
            throw new AuthenticationErrorException("Ingrese un correo válido");
        }

        Usuario usuario = repositorio.findByUsuarioCorreo(usuarioLogin.getUsuarioCorreo()).orElseThrow(() -> new AuthenticationErrorException("Error de credenciales"));
        if (usuario.getUsuarioClave().equals(usuarioLogin.getUsuarioClave())) {
            UsuarioDTO usuarioDTO = this.usuarioMapper.BDaDTO(usuario);
            return ResponseEntity.ok(usuarioDTO);
        } else {
            throw new AuthenticationErrorException("Error de credenciales");
        }
    }

    @Override
    public ResponseEntity<?> saveUser(Usuario usuarioRegistro) {
        if (usuarioRegistro.getIsAdmin()) {
            throw new AuthenticationErrorException("Acceso no autorizado");
        }
        if (!usuarioRegistro.getUsuarioCorreo().matches("^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")) {
            throw new AuthenticationErrorException("Ingrese un correo válido");
        }
        if (repositorio.findByUsuarioCorreo(usuarioRegistro.getUsuarioCorreo()).isEmpty()) {
            Usuario usuarioNuevo = repositorio.save(usuarioRegistro);
            UsuarioDTO usuarioDTO = this.usuarioMapper.BDaDTO(usuarioNuevo);
            return ResponseEntity.status(HttpStatus.CREATED).body(usuarioDTO);
        } else {
            return ResponseEntity.badRequest().body("Ya existe un usuario con ese correo");
        }
    }

    @Override
    public ResponseEntity<UsuarioDTO> updateById(Long id, Usuario usuarioActualizado) {
        Usuario usuario = repositorio.findById(id).orElseThrow(() -> new ResourceNotFoundException("No existe un usuario con el id:" + id));
        usuario.setUsuarioCorreo(usuarioActualizado.getUsuarioCorreo());
        usuario.setUsuarioClave(usuarioActualizado.getUsuarioClave());
        usuario.setIsAdmin(usuarioActualizado.getIsAdmin());
        UsuarioDTO usuarioDTO = this.usuarioMapper.BDaDTO(usuario);
        return ResponseEntity.ok(usuarioDTO);
    }

    @Override
    public ResponseEntity<UsuarioDTO> deleteById(Long id) {
        Usuario usuario = repositorio.findById(id).orElseThrow(() -> new ResourceNotFoundException("No existe un usuario con el id:" + id));
        repositorio.deleteById(id);
        UsuarioDTO usuarioDTO = this.usuarioMapper.BDaDTO(usuario);
        return ResponseEntity.ok(usuarioDTO);
    }

}

//TODO Hacer testing y swagger