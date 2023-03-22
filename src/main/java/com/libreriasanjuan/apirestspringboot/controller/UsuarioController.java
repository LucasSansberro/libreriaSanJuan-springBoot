package com.libreriasanjuan.apirestspringboot.controller;

import com.libreriasanjuan.apirestspringboot.dto.UsuarioDTO;
import com.libreriasanjuan.apirestspringboot.exceptions.AuthenticationErrorException;
import com.libreriasanjuan.apirestspringboot.exceptions.ResourceNotFoundException;
import com.libreriasanjuan.apirestspringboot.models.Usuario;
import com.libreriasanjuan.apirestspringboot.services.UsuarioServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api")
@Slf4j
public class UsuarioController {

    private final UsuarioServiceImpl usuarioServiceImpl;

    @Autowired
    public UsuarioController(UsuarioServiceImpl usuarioServiceImpl) {
        this.usuarioServiceImpl = usuarioServiceImpl;
    }

    @GetMapping("/usuarios")
    public ResponseEntity<List<UsuarioDTO>> getAllUsers() {
        List<UsuarioDTO> listaUsuarios = usuarioServiceImpl.getAllUsers();
        return ResponseEntity.ok(listaUsuarios);
    }

    @PostMapping("/usuarios/login")
    public ResponseEntity<?> loginUser(@RequestBody Usuario usuarioLogin) {
        try {
            UsuarioDTO userLogueado = usuarioServiceImpl.loginUser(usuarioLogin);
            log.info("Usuario logueado: " + usuarioLogin);
            return ResponseEntity.ok(userLogueado);
        } catch (AuthenticationErrorException error) {
            log.warn("Error durante el login: " + error);
            return ResponseEntity.badRequest().body(error.getMessage());
        }

    }

    @PostMapping("/usuarios")
    public ResponseEntity<?> saveUser(@RequestBody Usuario usuarioRegistro) {
        try {
            UsuarioDTO usuarioRegistrado = usuarioServiceImpl.saveUser(usuarioRegistro);
            log.info("Usuario registrado: " + usuarioRegistrado);
            return ResponseEntity.status(HttpStatus.CREATED).body(usuarioRegistrado);
        } catch (AuthenticationErrorException | DataIntegrityViolationException error) {
            if (Objects.equals(error.getMessage(), "Acceso no autorizado")) {
                log.error("Posible ataque en la creación de usuario: " + error);
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error.getMessage());
            } else {
                log.warn("Error creando un usuario: " + error);
                return ResponseEntity.badRequest().body(error.getMessage());
            }
        }
    }

    @PutMapping("/usuarios/{id}")
    public ResponseEntity<?> updateById(@PathVariable Long id, @RequestBody Usuario usuarioActualizado) {
        try {
            UsuarioDTO userActualizado = usuarioServiceImpl.updateById(id, usuarioActualizado);
            log.info("Usuario con ID " + id + " actualizado: " + userActualizado);
            return ResponseEntity.ok(userActualizado);
        } catch (ResourceNotFoundException | DataIntegrityViolationException error) {
            log.warn("Error al actualizar el usuario con ID: " + id + " - " + error.getMessage());
            return ResponseEntity.badRequest().body(error.getMessage());
        }
    }

    @DeleteMapping("/usuarios/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        try {
            UsuarioDTO usuarioEliminado = usuarioServiceImpl.deleteById(id);
            log.info("Usuario con ID " + id + " eliminado");
            return ResponseEntity.ok(usuarioEliminado);
        } catch (ResourceNotFoundException error) {
            log.warn("Error al eliminar el usuario con ID: " + id + " - " + error.getMessage());
            return ResponseEntity.badRequest().body(error.getMessage());
        }

    }
}

//TODO Hacer un objeto de response