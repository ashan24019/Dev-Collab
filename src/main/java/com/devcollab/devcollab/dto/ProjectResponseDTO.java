package com.devcollab.devcollab.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ProjectResponseDTO {

    private String id;

    private String name;

    private String description;

    private String ownerId;

    private List<String> memberIds;

    private String status;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}
