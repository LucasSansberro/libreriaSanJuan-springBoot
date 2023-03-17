package com.libreriasanjuan.apirestspringboot.datos;

import com.libreriasanjuan.apirestspringboot.models.Usuario;

public class DatosDummy {

    public static Usuario getUsuarioTest() {
        return new Usuario(1L, "test@test.com", "testClave", false);
    }

    public static Usuario getUsuario2Test() {
        return new Usuario(2L, "test2@test.com", "test2Clave", false);
    }


}
