package com.devcollab.devcollab.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDateTime;


@Data
public class CreateTaskDTO {

    @NotBlank(message = "Title is required")
    private String title;

    private String description;

    @NotBlank(message = "Project ID is required")
    private String projectId;

    // TODO: Remove after JWT implementation - set from security context
    @NotBlank(message = "Creator ID is required")
    private String createdById;

    private String assignedToId;

    @NotBlank(message = "Priority is required")
    private String priority;

    private LocalDateTime dueDate;
}
