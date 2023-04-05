package com.libreriasanjuan.apirestspringboot.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.HandlerMethod;

import java.util.Objects;


@ControllerAdvice
@Slf4j
public class ConfigHandlerException {
    @ExceptionHandler(AuthenticationErrorException.class)
    protected ResponseEntity<?> handleAuthenticationErrorException(AuthenticationErrorException ex, HandlerMethod handlerMethod) {
        log.warn("Error: '" + ex.getMessage() + "' en " + handlerMethod.getBeanType().getSimpleName() + "." + handlerMethod.getMethod().getName());
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    protected ResponseEntity<?> handleDataIntegrityViolationException(DataIntegrityViolationException ex, HandlerMethod handlerMethod) {
        if (Objects.equals(ex.getMessage(), "Acceso no autorizado")) {
            log.error("Posible ataque en la creación de usuario: " + ex);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
        } else {
            log.warn("Error: '" + ex.getMessage() + "' en " + handlerMethod.getBeanType().getSimpleName() + "." + handlerMethod.getMethod().getName());
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    protected ResponseEntity<?> handleAuthenticationErrorException(ResourceNotFoundException ex, HandlerMethod handlerMethod) {
        log.warn("Error: '" + ex.getMessage() + "' en " + handlerMethod.getBeanType().getSimpleName() + "." + handlerMethod.getMethod().getName());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
}
