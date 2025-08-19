package com.forohub.api.controller;

import com.forohub.api.dto.LoginDTO;
import com.forohub.api.model.User;
import com.forohub.api.repository.UserRepository;
import com.forohub.api.security.TokenService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginDTO loginDTO) {
        // Buscar usuario en DB
        User user = userRepository.findByUsername(loginDTO.getUsername())
                .orElse(null);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Usuario o contraseña incorrectos");
        }

        // Validar contraseña con PasswordEncoder
        if (!passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Usuario o contraseña incorrectos");
        }

        // Generar token JWT
        String token = tokenService.generateToken(user);

        // Devolver token
        return ResponseEntity.ok(Map.of("token", token));
    }
}