package com.libreriasanjuan.apirestspringboot.controller;

import com.libreriasanjuan.apirestspringboot.dto.UsuarioDTO;
import com.libreriasanjuan.apirestspringboot.models.Usuario;
import com.libreriasanjuan.apirestspringboot.services.UsuarioServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api")
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
    public ResponseEntity<UsuarioDTO> loginUser(@RequestBody Usuario usuarioLogin) {
        UsuarioDTO userLogueado = usuarioServiceImpl.loginUser(usuarioLogin);
        return ResponseEntity.ok(userLogueado);
    }

    @PostMapping("/usuarios")
    public ResponseEntity<?> saveUser(@RequestBody Usuario usuarioRegistro) {
        UsuarioDTO usuarioRegistrado = usuarioServiceImpl.saveUser(usuarioRegistro);
        if (usuarioRegistrado == null) {
            return ResponseEntity.badRequest().body("Ya existe un usuario con ese correo");
        } else {
            return ResponseEntity.status(HttpStatus.CREATED).body(usuarioRegistrado);
        }
    }

    @PutMapping("/usuarios/{id}")
    public ResponseEntity<UsuarioDTO> updateById(@PathVariable Long id, @RequestBody Usuario usuarioActualizado) {
        UsuarioDTO userActualizado = usuarioServiceImpl.updateById(id, usuarioActualizado);
        return ResponseEntity.ok(userActualizado);
    }

    @DeleteMapping("/usuarios/{id}")
    public ResponseEntity<UsuarioDTO> deleteById(@PathVariable Long id) {
        UsuarioDTO usuarioEliminado = usuarioServiceImpl.deleteById(id);
        return ResponseEntity.ok(usuarioEliminado);
    }
}