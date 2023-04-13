package com.libreriasanjuan.apirestspringboot.controller;

import com.libreriasanjuan.apirestspringboot.dto.UsuarioDTO;
import com.libreriasanjuan.apirestspringboot.exceptions.AuthenticationErrorException;
import com.libreriasanjuan.apirestspringboot.exceptions.ResourceNotFoundException;
import com.libreriasanjuan.apirestspringboot.models.Usuario;
import com.libreriasanjuan.apirestspringboot.services.UsuarioServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api")
@Api(value = "Usuarios", tags = "CRUD de usuarios")
public class UsuarioController {

    private final UsuarioServiceImpl usuarioServiceImpl;

    @GetMapping("/usuarios")
    @ApiOperation(value = "Traer a todos los usuarios")
    public ResponseEntity<List<UsuarioDTO>> getAllUsers() {
        return ResponseEntity.ok(usuarioServiceImpl.getAllUsers());
    }

    @PostMapping("/usuarios/login")
    @ApiOperation(value = "Iniciar sesión")
    public ResponseEntity<UsuarioDTO> loginUser(@RequestBody Usuario usuarioLogin) throws AuthenticationErrorException {
        return ResponseEntity.ok(usuarioServiceImpl.loginUser(usuarioLogin));
    }

    @PostMapping("/usuarios")
    @ApiOperation(value = "Registrar a un usuario")
    public ResponseEntity<UsuarioDTO> saveUser(@RequestBody Usuario usuarioRegistro) throws AuthenticationErrorException, DataIntegrityViolationException {
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioServiceImpl.saveUser(usuarioRegistro));
    }

    @PutMapping("/usuarios/{id}")
    @ApiOperation(value = "Editar a un usuario")
    public ResponseEntity<UsuarioDTO> updateById(@PathVariable Long id, @RequestBody Usuario usuarioActualizado) throws AuthenticationErrorException, DataIntegrityViolationException {
        return ResponseEntity.ok(usuarioServiceImpl.updateById(id, usuarioActualizado));
    }

    @DeleteMapping("/usuarios/{id}")
    @ApiOperation(value = "Eliminar a un usuario")
    public ResponseEntity<UsuarioDTO> deleteById(@PathVariable Long id) throws ResourceNotFoundException {
        return ResponseEntity.ok(usuarioServiceImpl.deleteById(id));
    }
}