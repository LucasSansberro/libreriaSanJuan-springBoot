package com.libreriasanjuan.apirestspringboot.controller;

import com.libreriasanjuan.apirestspringboot.dto.UsuarioDTO;
import com.libreriasanjuan.apirestspringboot.exceptions.AuthenticationErrorException;
import com.libreriasanjuan.apirestspringboot.exceptions.ResourceNotFoundException;
import com.libreriasanjuan.apirestspringboot.models.Usuario;
import com.libreriasanjuan.apirestspringboot.services.UsuarioServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api")
@Slf4j
@Api(value = "Usuarios", tags = "CRUD de usuarios")
public class UsuarioController {

    private final UsuarioServiceImpl usuarioServiceImpl;

    @Autowired
    public UsuarioController(UsuarioServiceImpl usuarioServiceImpl) {
        this.usuarioServiceImpl = usuarioServiceImpl;
    }

    @GetMapping("/usuarios")
    @ApiOperation(value = "Traer a todos los usuarios")
    public ResponseEntity<List<UsuarioDTO>> getAllUsers() {
        List<UsuarioDTO> listaUsuarios = usuarioServiceImpl.getAllUsers();
        return ResponseEntity.ok(listaUsuarios);
    }

    @PostMapping("/usuarios/login")
    @ApiOperation(value = "Iniciar sesión")
    public ResponseEntity<UsuarioDTO> loginUser(@RequestBody Usuario usuarioLogin) throws AuthenticationErrorException {
        UsuarioDTO userLogueado = usuarioServiceImpl.loginUser(usuarioLogin);
        log.info("Usuario logueado: " + usuarioLogin);
        return ResponseEntity.ok(userLogueado);
    }

    @PostMapping("/usuarios")
    @ApiOperation(value = "Registrar a un usuario")
    public ResponseEntity<UsuarioDTO> saveUser(@RequestBody Usuario usuarioRegistro) throws AuthenticationErrorException, DataIntegrityViolationException {
        UsuarioDTO usuarioRegistrado = usuarioServiceImpl.saveUser(usuarioRegistro);
        log.info("Usuario registrado: " + usuarioRegistrado);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioRegistrado);
    }

    @PutMapping("/usuarios/{id}")
    @ApiOperation(value = "Editar a un usuario")
    public ResponseEntity<UsuarioDTO> updateById(@PathVariable Long id, @RequestBody Usuario usuarioActualizado) throws AuthenticationErrorException, DataIntegrityViolationException {
        UsuarioDTO userActualizado = usuarioServiceImpl.updateById(id, usuarioActualizado);
        log.info("Usuario con ID " + id + " actualizado: " + userActualizado);
        return ResponseEntity.ok(userActualizado);
    }

    @DeleteMapping("/usuarios/{id}")
    @ApiOperation(value = "Eliminar a un usuario")
    public ResponseEntity<UsuarioDTO> deleteById(@PathVariable Long id) throws ResourceNotFoundException {
        UsuarioDTO usuarioEliminado = usuarioServiceImpl.deleteById(id);
        log.info("Usuario con ID " + id + " eliminado");
        return ResponseEntity.ok(usuarioEliminado);
    }
}