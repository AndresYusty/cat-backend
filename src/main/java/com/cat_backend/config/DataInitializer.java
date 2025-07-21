package com.cat_backend.config;

import com.cat_backend.dto.CreateUserDTO;
import com.cat_backend.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {
    
    private final UserService userService;
    
    public DataInitializer(UserService userService) {
        this.userService = userService;
    }
    
    @Override
    public void run(String... args) throws Exception {
        // Crear usuarios de prueba si no existen
        try {
            CreateUserDTO testUser = new CreateUserDTO();
            testUser.setUsername("test");
            testUser.setPassword("test123");
            testUser.setFullName("Usuario de Prueba");
            userService.register(testUser);
            System.out.println("Usuario de prueba creado: test/test123");
        } catch (Exception e) {
            System.out.println("El usuario de prueba ya existe o hubo un error: " + e.getMessage());
        }
    }
} 