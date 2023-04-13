package com.libreriasanjuan.apirestspringboot.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.HandlerMethod;

import javax.persistence.PersistenceException;
import java.util.Objects;


@ControllerAdvice
@Slf4j
public class ConfigHandlerException {
    @ExceptionHandler(AuthenticationErrorException.class)
    protected ResponseEntity<String> handleAuthenticationErrorException(AuthenticationErrorException ex, HandlerMethod handlerMethod) {
        log.warn("Error: '" + ex.getMessage() + "' en " + handlerMethod.getBeanType().getSimpleName() + "." + handlerMethod.getMethod().getName());
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    protected ResponseEntity<String> handleDataIntegrityViolationException(DataIntegrityViolationException ex, HandlerMethod handlerMethod) {
        if (Objects.equals(ex.getMessage(), "Acceso no autorizado")) {
            log.error("Posible ataque en la creación de usuario: " + ex.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
        } else {
            log.warn("Error: '" + ex.getMessage() + "' en " + handlerMethod.getBeanType().getSimpleName() + "." + handlerMethod.getMethod().getName());
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    protected ResponseEntity<String> handleResourceNotFoundException(ResourceNotFoundException ex, HandlerMethod handlerMethod) {
        log.warn("Error: '" + ex.getMessage() + "' en " + handlerMethod.getBeanType().getSimpleName() + "." + handlerMethod.getMethod().getName());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
    @ExceptionHandler(PersistenceException.class)
    protected ResponseEntity<String> handleResourceNotFoundException(PersistenceException ex, HandlerMethod handlerMethod) {
        log.error("Error: '" + ex.getMessage() + "' en " + handlerMethod.getBeanType().getSimpleName() + "." + handlerMethod.getMethod().getName());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
    }
}
