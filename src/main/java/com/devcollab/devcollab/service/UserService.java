package com.devcollab.devcollab.service;

import com.devcollab.devcollab.dto.RegisterRequestDTO;
import com.devcollab.devcollab.dto.UserMapper;
import com.devcollab.devcollab.dto.UserResponseDTO;
import com.devcollab.devcollab.exception.ResourceNotFoundException;
import com.devcollab.devcollab.model.User;
import com.devcollab.devcollab.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserResponseDTO createUser(RegisterRequestDTO dto) {
        User user = UserMapper.toUser(dto);
        User saved = userRepository.save(user);

        return UserMapper.toResponseDTO(saved);
    }

    public List<UserResponseDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(UserMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public UserResponseDTO getUserById(String id) {
        User user =  userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        return UserMapper.toResponseDTO(user);
    }
}
