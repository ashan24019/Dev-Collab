package com.devcollab.devcollab.dto;

import com.devcollab.devcollab.enums.TaskPriority;
import com.devcollab.devcollab.enums.TaskStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UpdateTaskDTO {

    private String title;

    private String description;

    private String assignedToId;

    private TaskPriority priority;

    private TaskStatus status;

    private LocalDateTime dueDate;
}