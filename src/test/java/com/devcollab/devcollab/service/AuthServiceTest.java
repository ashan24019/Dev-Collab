package com.devcollab.devcollab.service;

import com.devcollab.devcollab.config.JwtService;
import com.devcollab.devcollab.dto.AuthResponseDTO;
import com.devcollab.devcollab.dto.LoginRequestDTO;
import com.devcollab.devcollab.dto.RegisterRequestDTO;
import com.devcollab.devcollab.enums.UserRole;
import com.devcollab.devcollab.exception.ResourceNotFoundException;
import com.devcollab.devcollab.model.User;
import com.devcollab.devcollab.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private AuthService authService;

    @Test
    void register_shouldCreateUser_whenEmailNotTaken() {
        // Arrange
        RegisterRequestDTO dto = new RegisterRequestDTO();
        dto.setName("Ashan");
        dto.setEmail("ashan@test.com");
        dto.setPassword("123456");

        User savedUser = new User();
        savedUser.setId("user123");
        savedUser.setName("Ashan");
        savedUser.setEmail("ashan@test.com");
        savedUser.setPassword("hashedPassword");
        savedUser.setRole(UserRole.MEMBER);

        when(userRepository.findByEmail("ashan@test.com")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("123456")).thenReturn("hashedPassword");
        when(userRepository.save(any(User.class))).thenReturn(savedUser);
        when(jwtService.generateToken(anyString(), anyString())).thenReturn("fake.jwt.token");

        // Act
        AuthResponseDTO result = authService.register(dto);

        // Assert
        assertThat(result.getEmail()).isEqualTo("ashan@test.com");
        assertThat(result.getRole()).isEqualTo(UserRole.MEMBER);
        assertThat(result.getToken()).isEqualTo("fake.jwt.token");
        verify(passwordEncoder, times(1)).encode("123456");
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void register_shouldThrowException_whenEmailAlreadyExists() {
        // Arrange
        RegisterRequestDTO dto = new RegisterRequestDTO();
        dto.setEmail("existing@test.com");
        dto.setPassword("123456");

        when(userRepository.findByEmail("existing@test.com"))
                .thenReturn(Optional.of(new User()));

        // Act & Assert
        assertThatThrownBy(() -> authService.register(dto))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Email");
    }

    @Test
    void login_shouldReturnToken_whenCredentialsValid() {
        // Arrange
        LoginRequestDTO dto = new LoginRequestDTO();
        dto.setEmail("ashan@test.com");
        dto.setPassword("123456");

        User user = new User();
        user.setId("user123");
        user.setEmail("ashan@test.com");
        user.setPassword("hashedpassword");
        user.setRole(UserRole.MEMBER);

        when(userRepository.findByEmail("ashan@test.com")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("123456", "hashedpassword")).thenReturn(true);
        when(jwtService.generateToken(anyString(), anyString())).thenReturn("fake.jwt.token");

        // Act
        AuthResponseDTO result = authService.login(dto);

        // Assert
        assertThat(result.getToken()).isEqualTo("fake.jwt.token");
        assertThat(result.getEmail()).isEqualTo("ashan@test.com");
    }

    @Test
    void login_shouldThrowException_whenPasswordWrong() {
        // Arrange
        LoginRequestDTO dto = new LoginRequestDTO();
        dto.setEmail("ashan@test.com");
        dto.setPassword("wrongpassword");

        User user = new User();
        user.setEmail("ashan@test.com");
        user.setPassword("hashedpassword");
        user.setRole(UserRole.MEMBER);

        when(userRepository.findByEmail("ashan@test.com")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("wrongpassword", "hashedpassword")).thenReturn(false);

        // Act & Assert
        assertThatThrownBy(() -> authService.login(dto))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Invalid email or password");
    }

    @Test
    void login_shouldThrowException_whenUserNotFound() {
        // Arrange
        LoginRequestDTO dto = new LoginRequestDTO();
        dto.setEmail("notfound@test.com");
        dto.setPassword("123456");

        when(userRepository.findByEmail("notfound@test.com")).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> authService.login(dto))
                .isInstanceOf(ResourceNotFoundException.class);
    }
}
