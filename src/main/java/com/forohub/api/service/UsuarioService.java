package com.forohub.api.service;

import com.forohub.api.model.User;
import com.forohub.api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User crearUsuario(String username, String password, String role) {
        User user = User.builder()
                .username(username)
                .password(passwordEncoder.encode(password)) // encriptar
                .role(role)
                .build();
        return userRepository.save(user);
    }
}