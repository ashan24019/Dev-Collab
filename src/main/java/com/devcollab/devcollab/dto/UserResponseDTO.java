package com.devcollab.devcollab.dto;

import com.devcollab.devcollab.enums.UserRole;
import lombok.Data;

@Data
public class UserResponseDTO {
    private String id;
    private String name;
    private String email;
    private UserRole role;
}
