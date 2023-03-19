package com.libreriasanjuan.apirestspringboot.services;

import com.libreriasanjuan.apirestspringboot.datos.DatosDummy;
import com.libreriasanjuan.apirestspringboot.dto.UsuarioDTO;
import com.libreriasanjuan.apirestspringboot.exceptions.AuthenticationErrorException;
import com.libreriasanjuan.apirestspringboot.models.Usuario;
import com.libreriasanjuan.apirestspringboot.repositories.UsuarioRepositorio;
import com.libreriasanjuan.apirestspringboot.services.interfaces.UsuarioService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@SpringBootTest
class UsuarioServiceImplTest {
    private final UsuarioRepositorio usuarioRepositorio;
    private final UsuarioService usuarioService;

    @Autowired
    public UsuarioServiceImplTest(UsuarioServiceImpl usuarioService, UsuarioRepositorio usuarioRepositorio) {
        this.usuarioService = usuarioService;
        this.usuarioRepositorio = usuarioRepositorio;
    }

    @BeforeEach
    void setUp() {
        this.usuarioRepositorio.save(DatosDummy.getUsuarioTest());
        this.usuarioRepositorio.save(DatosDummy.getUsuario2Test());
    }

    @AfterEach
    void tearDown() {
        this.usuarioRepositorio.deleteAll();
    }

    @Test
    void getAllUsers() {
        //GIVEN
        //BEFORE EACH

        //WHEN
        List<UsuarioDTO> usuarios = this.usuarioService.getAllUsers();

        //THEN
        assertThat(usuarios.isEmpty()).isFalse();
        assertThat(usuarios.size()).isEqualTo(2);
    }

    @Test
    void loginUser() {
        //GIVEN
        Long trueId = DatosDummy.getUsuarioTest().getUsuarioId();
        String trueMail = DatosDummy.getUsuarioTest().getUsuarioCorreo();
        String truePassword = DatosDummy.getUsuarioTest().getUsuarioClave();
        boolean trueIsAdmin = DatosDummy.getUsuarioTest().getIsAdmin();
        String falsoMail = "falsoMail.com";
        String falsoPassword = "falsaClave";

        //WHEN
        Usuario usuarioFalsoMail = new Usuario(trueId, falsoMail, truePassword, trueIsAdmin);
        Usuario usuarioFalsaClave = new Usuario(trueId, trueMail, falsoPassword, trueIsAdmin);
        UsuarioDTO usuarioLogueado = this.usuarioService.loginUser(DatosDummy.getUsuarioTest());

        //THEN
        assertThatThrownBy(() -> this.usuarioService.loginUser(usuarioFalsoMail)).isInstanceOf(AuthenticationErrorException.class);
        assertThatThrownBy(() -> this.usuarioService.loginUser(usuarioFalsaClave)).isInstanceOf(AuthenticationErrorException.class);
        assertThat(usuarioLogueado).isInstanceOf(UsuarioDTO.class);
    }

    @Test
    void saveUser() {
        //GIVEN
        Long validId = 3L;
        String validMail = "NuevoUser@test.com";
        String validPassword = "clave";
        boolean isAdminUser = false;
        boolean isAdminAtacante = true;
        String invalidMail = "falsoMail.com";
        Usuario usuarioExistente = this.usuarioRepositorio.findAll().get(0);

        //WHEN
        Usuario usuarioFalsoAdmin = new Usuario(validId, validMail, validPassword, isAdminAtacante);
        Usuario usuarioMailIncorrecto = new Usuario(validId, invalidMail, validPassword, isAdminUser);
        Usuario usuarioRepetido = new Usuario(usuarioExistente.getUsuarioId(), usuarioExistente.getUsuarioCorreo(), usuarioExistente.getUsuarioClave(), usuarioExistente.getIsAdmin());
        Usuario usuarioNuevo = new Usuario(validId, validMail, validPassword, isAdminUser);


        //THEN
        assertThatThrownBy(() -> this.usuarioService.saveUser(usuarioFalsoAdmin)).isInstanceOf(AuthenticationErrorException.class);
        assertThatThrownBy(() -> this.usuarioService.saveUser(usuarioMailIncorrecto)).isInstanceOf(AuthenticationErrorException.class);
        assertThat(this.usuarioService.saveUser(usuarioRepetido)).isEqualTo(null);
        assertThat(this.usuarioService.saveUser(usuarioNuevo)).isInstanceOf(UsuarioDTO.class);
    }

    @Test
    void updateById() {
        //GIVEN
        Usuario usuarioOriginal = this.usuarioRepositorio.findAll().get(0);
        String nuevoCorreo = "CambioCorreo@test.com";
        String nuevaClave = "CambioClave";

        //WHEN
        Usuario usuarioEditado = new Usuario(usuarioOriginal.getUsuarioId(), nuevoCorreo, nuevaClave, usuarioOriginal.getIsAdmin());
        UsuarioDTO usuarioPostEdit = this.usuarioService.updateById(usuarioOriginal.getUsuarioId(), usuarioEditado);

        //THEN
        assertThatThrownBy(() -> this.usuarioService.updateById(80L, usuarioEditado)).isInstanceOf(RuntimeException.class);
        assertThat(usuarioPostEdit.getUsuarioCorreo().equals(nuevoCorreo)).isTrue();
        assertThat(usuarioOriginal.getUsuarioId().equals(usuarioPostEdit.getUsuarioId())).isTrue();
    }

    @Test
    void deleteById() {
        //GIVEN
        Usuario usuarioABorrar = this.usuarioRepositorio.findAll().get(0);
        Long idUsuarioABorrar = usuarioABorrar.getUsuarioId();
        Long idErroneo = 80L;

        //WHEN
        UsuarioDTO usuarioBorrado = this.usuarioService.deleteById(idUsuarioABorrar);


        //THEN
        assertThat(usuarioBorrado).isInstanceOf(UsuarioDTO.class);
        assertThat(usuarioABorrar.getUsuarioCorreo().equals(usuarioBorrado.getUsuarioCorreo())).isTrue();
        assertThatThrownBy(() -> this.usuarioService.deleteById(idErroneo));
    }
}