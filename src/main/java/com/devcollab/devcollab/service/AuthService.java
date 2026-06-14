package com.devcollab.devcollab.service;

import com.devcollab.devcollab.config.JwtService;
import com.devcollab.devcollab.dto.AuthResponseDTO;
import com.devcollab.devcollab.dto.LoginRequestDTO;
import com.devcollab.devcollab.dto.RegisterRequestDTO;
import com.devcollab.devcollab.enums.UserRole;
import com.devcollab.devcollab.exception.ResourceNotFoundException;
import com.devcollab.devcollab.model.User;
import com.devcollab.devcollab.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    public AuthResponseDTO register(RegisterRequestDTO dto) {
        if (userRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new UsernameNotFoundException("Email already exists");
        }

        User user = new User();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRole(UserRole.MEMBER);

        User saved = userRepository.save(user);

        String token = jwtService.generateToken(saved.getId(), saved.getRole().name());

        return new AuthResponseDTO(
                token,
                saved.getId(),
                saved.getName(),
                saved.getEmail(),
                saved.getRole()
        );
    }

    public AuthResponseDTO login(LoginRequestDTO dto) {
        User user = userRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("Invalid email or password"));

        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid email or password"); // Never tell attacker which one is the wrong
        }

        String token = jwtService.generateToken(user.getId(), user.getRole().name());

        return new AuthResponseDTO(
                token,
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole()
        );
    }
}
