package com.devcollab.devcollab.dto;

import com.devcollab.devcollab.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponseDTO {
    private String token;
    private String id;
    private String name;
    private String email;
    private UserRole role;
}
