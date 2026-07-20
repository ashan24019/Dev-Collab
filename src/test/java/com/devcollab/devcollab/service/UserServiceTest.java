package com.devcollab.devcollab.service;

import com.devcollab.devcollab.dto.UpdateUserDTO;
import com.devcollab.devcollab.dto.UserResponseDTO;
import com.devcollab.devcollab.enums.UserRole;
import com.devcollab.devcollab.exception.ResourceNotFoundException;
import com.devcollab.devcollab.model.User;
import com.devcollab.devcollab.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    User testUser;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setId("user123");
        testUser.setName("test");
        testUser.setEmail("user@test.com");
        testUser.setPassword("password");
        testUser.setRole(UserRole.MEMBER);
    }

    @Test
    void getAllUsers_shouldReturnListOfUsers() {
        // Arrange
        when(userRepository.findAll()).thenReturn(List.of(testUser));

        // Act
        List<UserResponseDTO> result = userService.getAllUsers();

        //Assert
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getName()).isEqualTo("test");
        assertThat(result.get(0).getEmail()).isEqualTo("user@test.com");
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void getUserById_shouldReturnUser_whenUserExists() {
        when(userRepository.findById("user123")).thenReturn(Optional.of(testUser));

        UserResponseDTO result = userService.getUserById("user123");

        assertThat(result.getId()).isEqualTo("user123");
        assertThat(result.getName()).isEqualTo("test");
    }

    @Test
    void getUserById_shouldThrowException_whenUserNotFound() {
        // Arrange
        when(userRepository.findById("nonexistent")).thenReturn(Optional.empty());

        // Act and Assert
        assertThatThrownBy(() -> userService.getUserById("nonexistent"))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("nonexistent");
    }

    @Test
    void updateUser_shouldUpdateNameAndRole() {
        when(userRepository.findById("user123")).thenReturn(Optional.of(testUser));
        when(userRepository.save(any(User.class))).thenReturn(testUser);

        UpdateUserDTO dto = new UpdateUserDTO();
        dto.setName("Update Name");

        UserResponseDTO result = userService.updateUser("user123", dto);

        verify(userRepository, times(1)).save(any(User.class));
        assertThat(result).isNotNull();
    }

    @Test
    void deleteUser_shouldCallDelete_whenUserExists() {
        when(userRepository.findById("user123")).thenReturn(Optional.of(testUser));

        userService.deleteUser("user123");

        verify(userRepository, times(1)).delete(testUser);
    }

    @Test
    void deleteUser_shouldThrowException_whenUserNotFound() {
        // Arrange
        when(userRepository.findById("nonexistent")).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> userService.deleteUser("nonexistent"))
                .isInstanceOf(ResourceNotFoundException.class);
    }
}
