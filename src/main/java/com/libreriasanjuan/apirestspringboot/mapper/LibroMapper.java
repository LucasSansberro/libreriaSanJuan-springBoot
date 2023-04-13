package com.libreriasanjuan.apirestspringboot.mapper;

import com.libreriasanjuan.apirestspringboot.dto.LibroResponse;
import com.libreriasanjuan.apirestspringboot.models.Libro;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface LibroMapper {
    LibroResponse BDaDTO(Libro libro);
    List<LibroResponse> convertirListaADTO(List<Libro> libros);
}
