package com.libreriasanjuan.apirestspringboot;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class ApirestSpringbootApplication implements CommandLineRunner {

    // Logger
    public static void main(String[] args) {
        SpringApplication.run(ApirestSpringbootApplication.class, args);
    }

    @Override
    public void run(String... args) {
        System.out.println("Servidor encendido");
    }
}
