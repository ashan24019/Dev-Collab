package com.devcollab.devcollab.dto;

import com.devcollab.devcollab.enums.UserRole;
import lombok.Data;

@Data
public class UpdateUserDTO {
        private String name;
        private UserRole role;
}
