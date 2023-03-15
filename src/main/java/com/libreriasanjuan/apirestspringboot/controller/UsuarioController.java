package com.libreriasanjuan.apirestspringboot.controller;

import com.libreriasanjuan.apirestspringboot.dto.UsuarioDTO;
import com.libreriasanjuan.apirestspringboot.models.Usuario;
import com.libreriasanjuan.apirestspringboot.services.UsuarioServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
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
        return usuarioServiceImpl.getAllUsers();
    }

    @PostMapping("/usuarios")
    public ResponseEntity<?> saveUser(@RequestBody Usuario usuarioRegistro) {
        return usuarioServiceImpl.saveUser(usuarioRegistro);
    }

    @PostMapping("/usuarios/login")
    public ResponseEntity<UsuarioDTO> getUserByMail(@RequestBody Usuario usuarioLogin) {
        return usuarioServiceImpl.getUserByMail(usuarioLogin);
    }

    @PutMapping("/usuarios/{id}")
    public ResponseEntity<UsuarioDTO> updateById(@PathVariable Long id, @RequestBody Usuario usuarioActualizado) {
        return usuarioServiceImpl.updateById(id, usuarioActualizado);
    }

    @DeleteMapping("/usuarios/{id}")
    public ResponseEntity<UsuarioDTO> deleteById(@PathVariable Long id) {
        return usuarioServiceImpl.deleteById(id);
    }
}
