package com.libreriasanjuan.apirestspringboot.services;

import com.libreriasanjuan.apirestspringboot.dto.UsuarioDTO;
import com.libreriasanjuan.apirestspringboot.exceptions.AuthenticationErrorException;
import com.libreriasanjuan.apirestspringboot.exceptions.ResourceNotFoundException;
import com.libreriasanjuan.apirestspringboot.mapper.UsuarioMapper;
import com.libreriasanjuan.apirestspringboot.models.Usuario;
import com.libreriasanjuan.apirestspringboot.repositories.UsuarioRepositorio;
import com.libreriasanjuan.apirestspringboot.services.interfaces.UsuarioService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@Slf4j
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepositorio repositorio;
    private final UsuarioMapper usuarioMapper;

    @Autowired
    public UsuarioServiceImpl(UsuarioRepositorio usuarioRepositorio, UsuarioMapper usuarioMapper) {
        this.repositorio = usuarioRepositorio;
        this.usuarioMapper = usuarioMapper;
    }

    @Override
    public List<UsuarioDTO> getAllUsers() {
        List<Usuario> usuariosBD = repositorio.findAll();
        return usuarioMapper.convertirListaADTO(usuariosBD);
    }

    @Override
    public UsuarioDTO loginUser(Usuario usuarioLogin) {
        if (!usuarioLogin.getUsuarioCorreo().matches("^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")) {
            throw new AuthenticationErrorException("Ingrese un correo válido");
        }

        Usuario usuario = repositorio.findByUsuarioCorreo(usuarioLogin.getUsuarioCorreo()).orElseThrow(() -> new AuthenticationErrorException("Error de credenciales"));

        if (!usuario.getUsuarioClave().equals(usuarioLogin.getUsuarioClave())) {
            throw new AuthenticationErrorException("Error de credenciales");
        } else {
            log.info("Usuario logueado: " + usuarioLogin);
            return this.usuarioMapper.BDaDTO(usuario);
        }
    }

    @Override
    public UsuarioDTO saveUser(Usuario usuarioRegistro) {
        if (usuarioRegistro.getIsAdmin()) {
            throw new AuthenticationErrorException("Acceso no autorizado");
        }
        if (!usuarioRegistro.getUsuarioCorreo().matches("^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$") || usuarioRegistro.getUsuarioClave().trim().length() < 1) {
            throw new AuthenticationErrorException("Las credenciales ingresadas son inválidas");
        }
        if (repositorio.findByUsuarioCorreo(usuarioRegistro.getUsuarioCorreo()).isPresent()) {
            throw new DataIntegrityViolationException("Ya existe un usuario con ese correo");
        } else {
            Usuario usuarioNuevo = repositorio.save(usuarioRegistro);
            log.info("Usuario registrado: " + usuarioNuevo);
            return this.usuarioMapper.BDaDTO(usuarioNuevo);
        }
    }

    @Override
    public UsuarioDTO updateById(Long id, Usuario usuarioActualizado) {
        Usuario usuario = repositorio.findById(id).orElseThrow(() -> new ResourceNotFoundException("No existe un usuario con el id: " + id));
        if (repositorio.findByUsuarioCorreo(usuarioActualizado.getUsuarioCorreo()).isPresent() && !id.equals(repositorio.findByUsuarioCorreo(usuarioActualizado.getUsuarioCorreo()).get().getUsuarioId())) {
            throw new DataIntegrityViolationException("Ya existe un usuario con ese correo");
        } else {
            usuario.setUsuarioCorreo(usuarioActualizado.getUsuarioCorreo());
            usuario.setUsuarioClave(usuarioActualizado.getUsuarioClave());
            log.info("Usuario con ID " + id + " actualizado: " + usuario);
            return this.usuarioMapper.BDaDTO(usuario);
        }
    }

    @Override
    public UsuarioDTO deleteById(Long id) {
        Usuario usuario = repositorio.findById(id).orElseThrow(() -> new ResourceNotFoundException("No existe un usuario con el id:" + id));
        repositorio.deleteById(id);
        log.info("Usuario con ID " + id + " eliminado");
        return this.usuarioMapper.BDaDTO(usuario);
    }

}
