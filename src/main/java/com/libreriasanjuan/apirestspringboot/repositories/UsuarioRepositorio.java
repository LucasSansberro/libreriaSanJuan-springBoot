package com.libreriasanjuan.apirestspringboot.repositories;

import com.libreriasanjuan.apirestspringboot.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepositorio extends JpaRepository<Usuario,Long> {
    Optional<Usuario> findByUsuarioCorreo(String correo);
}
