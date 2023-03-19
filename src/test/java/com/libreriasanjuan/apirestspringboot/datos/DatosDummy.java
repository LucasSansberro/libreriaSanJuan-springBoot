package com.libreriasanjuan.apirestspringboot.datos;


import com.libreriasanjuan.apirestspringboot.models.Factura;
import com.libreriasanjuan.apirestspringboot.models.Libro;
import com.libreriasanjuan.apirestspringboot.models.Usuario;

import java.time.LocalDate;


public class DatosDummy {

    public static Usuario getUsuarioTest() {
        return new Usuario(1L, "test@test.com", "testClave", false);
    }

    public static Usuario getUsuario2Test() {
        return new Usuario(2L, "test2@test.com", "test2Clave", false);
    }

    public static Libro getLibroTest() {
        return Libro.builder().libroId(1L).libroPrecio(500).libroTitulo("LibroTest").libroAutor("TestMan").libroPortada("PortadaBonita.jpg").build();
    }

    public static Libro getLibro2Test() {
        return Libro.builder().libroId(2L).libroPrecio(600).libroTitulo("LibroTest2").libroAutor("TestMan").libroPortada("PortadaBonita.jpg").build();
    }

    public static Factura getFacturaTest(){
        return new Factura(1L,getUsuarioTest(),1100, LocalDate.now());
    }

}
