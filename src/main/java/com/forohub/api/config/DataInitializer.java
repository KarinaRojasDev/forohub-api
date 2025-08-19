package com.forohub.api.config;

import com.forohub.api.model.User;
import com.forohub.api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Verificar si el usuario admin ya existe
        if (userRepository.findByUsername("admin").isEmpty()) {
            User admin = User.builder()
                    .username("admin")
                    .password(passwordEncoder.encode("1234")) // contraseña encriptada
                    .role("ROLE_ADMIN")
                    .build();

            userRepository.save(admin);
            System.out.println("Usuario admin creado: admin / 1234");
        } else {
            System.out.println("Usuario admin ya existe.");
        }
    }
}
