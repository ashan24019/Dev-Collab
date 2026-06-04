package com.devcollab.devcollab.dto;

import com.devcollab.devcollab.enums.UserRole;
import lombok.Data;

@Data
public class RegisterRequestDTO {
    private String name;
    private String email;
    private String password;
    private UserRole role;
}
