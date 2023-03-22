package com.libreriasanjuan.apirestspringboot.services;

import com.libreriasanjuan.apirestspringboot.datos.DatosDummy;
import com.libreriasanjuan.apirestspringboot.dto.UsuarioDTO;
import com.libreriasanjuan.apirestspringboot.exceptions.AuthenticationErrorException;
import com.libreriasanjuan.apirestspringboot.mapper.UsuarioMapper;
import com.libreriasanjuan.apirestspringboot.models.Usuario;
import com.libreriasanjuan.apirestspringboot.repositories.UsuarioRepositorio;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
class UsuarioServiceImplTest {

    private UsuarioRepositorio usuarioRepositorio;
    private UsuarioMapper usuarioMapper;
    private UsuarioServiceImpl usuarioService;
    private Usuario usuario1;
    private Usuario usuario2;
    private UsuarioDTO usuarioDTO1;
    private UsuarioDTO usuarioDTO2;

    @BeforeEach
    void setUp() {
        usuarioRepositorio = mock(UsuarioRepositorio.class);
        usuarioMapper = mock(UsuarioMapper.class);
        usuarioService = new UsuarioServiceImpl(usuarioRepositorio, usuarioMapper);
        usuario1 = DatosDummy.getUsuarioTest();
        usuario2 = DatosDummy.getUsuario2Test();
        usuarioDTO1 = DatosDummy.getUsuarioDTOTest();
        usuarioDTO2 = DatosDummy.getUsuarioDTO2Test();
    }

    @AfterEach
    void tearDown() {

    }

    @Test
    void getAllUsers() {
        //GIVEN
        when(usuarioRepositorio.findAll()).thenReturn(Arrays.asList(usuario1, usuario2));
        when(usuarioMapper.convertirListaADTO(anyList())).thenReturn(Arrays.asList(usuarioDTO1, usuarioDTO2));

        //WHEN
        List<UsuarioDTO> usuarios = this.usuarioService.getAllUsers();

        //THEN
        assertThat(usuarios.isEmpty()).isFalse();
        assertThat(usuarios.size()).isEqualTo(2);
    }

    @Test
    void loginUser() {
        //GIVEN
        String trueMail = usuario1.getUsuarioCorreo();
        String truePassword = usuario1.getUsuarioClave();
        String falsoMail = "falsoMail.com";
        String falsoPassword = "falsaClave";

        Usuario usuarioFalsoMail = new Usuario(2L, falsoMail, truePassword, false);
        Usuario usuarioFalsaClave = new Usuario(3L, trueMail, falsoPassword, false);

        when(usuarioRepositorio.findByUsuarioCorreo(trueMail)).thenReturn(Optional.of(usuario1));
        when(usuarioRepositorio.findByUsuarioCorreo(falsoMail)).thenReturn(Optional.of(usuarioFalsoMail));
        when(usuarioMapper.BDaDTO(any(Usuario.class))).thenReturn(usuarioDTO1);

        //WHEN
        UsuarioDTO usuarioLogueado = this.usuarioService.loginUser(usuario1);

        //THEN
        assertThat(usuarioLogueado).isInstanceOf(UsuarioDTO.class);
        assertThatThrownBy(() -> this.usuarioService.loginUser(usuarioFalsoMail)).isInstanceOf(AuthenticationErrorException.class);
        assertThatThrownBy(() -> this.usuarioService.loginUser(usuarioFalsaClave)).isInstanceOf(AuthenticationErrorException.class);
    }

    @Test
    void saveUser() {
        //GIVEN
        String validMail = "NuevoUser@test.com";
        String validPassword = "clave";
        String invalidMail = "falsoMail.com";
        boolean isAdminAtacante = true;
        String repeatedMail = "repeatedMail@gmail.com";

        Usuario usuarioFalsoAdmin = new Usuario(1L, validMail, validPassword, isAdminAtacante);
        Usuario usuarioMailIncorrecto = new Usuario(1L, invalidMail, validPassword, false);
        Usuario usuarioRepetido = new Usuario(1L, repeatedMail, validPassword, false);

        when(usuarioRepositorio.findByUsuarioCorreo(validMail)).thenReturn(Optional.empty());
        when(usuarioRepositorio.findByUsuarioCorreo(invalidMail)).thenReturn(Optional.of(usuarioMailIncorrecto));
        when(usuarioRepositorio.findByUsuarioCorreo(repeatedMail)).thenReturn(Optional.of(new Usuario()));
        when(usuarioRepositorio.save(any(Usuario.class))).thenReturn(usuario1);
        when(usuarioMapper.BDaDTO(any(Usuario.class))).thenReturn(usuarioDTO1);

        //WHEN
        UsuarioDTO usuarioCreado = this.usuarioService.saveUser(usuario1);

        //THEN
        assertThatThrownBy(() -> this.usuarioService.saveUser(usuarioFalsoAdmin)).isInstanceOf(AuthenticationErrorException.class);
        assertThatThrownBy(() -> this.usuarioService.saveUser(usuarioMailIncorrecto)).isInstanceOf(AuthenticationErrorException.class);
        assertThat(this.usuarioService.saveUser(usuarioRepetido)).isEqualTo(null);
        assertThat(usuarioCreado).isInstanceOf(UsuarioDTO.class);
    }

    @Test
    void updateById() {
        //GIVEN
        String nuevoCorreo = "CambioCorreo@test.com";
        String nuevaClave = "CambioClave";

        Usuario usuarioEditado = new Usuario(1L, nuevoCorreo, nuevaClave, false);

        when(usuarioMapper.BDaDTO(any(Usuario.class))).thenReturn(usuarioDTO1);
        when(usuarioRepositorio.findById(1L)).thenReturn(Optional.of(usuario1));

        //WHEN
        UsuarioDTO usuarioPostEdit = this.usuarioService.updateById(1L, usuarioEditado);

        //THEN
        assertThatThrownBy(() -> this.usuarioService.updateById(80L, usuarioEditado)).isInstanceOf(RuntimeException.class);
        assertThat(usuarioPostEdit).isInstanceOf(UsuarioDTO.class);
    }

    @Test
    void deleteById() {
        //GIVEN
        when(usuarioMapper.BDaDTO(any(Usuario.class))).thenReturn(usuarioDTO1);
        when(usuarioRepositorio.findById(1L)).thenReturn(Optional.of(usuario1));

        //WHEN
        UsuarioDTO usuarioBorrado = this.usuarioService.deleteById(1L);

        //THEN
        assertThat(usuarioBorrado).isInstanceOf(UsuarioDTO.class);
        assertThatThrownBy(() -> this.usuarioService.deleteById(2L));
    }
}