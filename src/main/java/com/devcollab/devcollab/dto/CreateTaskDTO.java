package com.devcollab.devcollab.dto;

import com.devcollab.devcollab.enums.TaskPriority;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;


@Data
public class CreateTaskDTO {

    @NotBlank(message = "Title is required")
    private String title;

    private String description;

    @NotBlank(message = "Project ID is required")
    private String projectId;

    private String assignedToId;

    @NotNull(message = "Priority is required")
    private TaskPriority priority;

    private LocalDateTime dueDate;
}
