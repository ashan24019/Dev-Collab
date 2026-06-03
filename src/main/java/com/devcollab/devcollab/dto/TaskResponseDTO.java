package com.devcollab.devcollab.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TaskResponseDTO {

    private String id;

    private String title;

    private String description;

    private String projectId;

    private String createdById;

    private String assignedToId;

    private String priority;

    private String status;

    private LocalDateTime dueDate;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
