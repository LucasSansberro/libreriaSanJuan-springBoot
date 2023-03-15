package com.libreriasanjuan.apirestspringboot.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDTO {
    private Long usuarioId;
    private String usuarioCorreo;
    private boolean isAdmin;
}
