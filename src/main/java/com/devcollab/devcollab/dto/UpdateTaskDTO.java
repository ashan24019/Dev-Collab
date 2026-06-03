package com.devcollab.devcollab.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UpdateTaskDTO {

    private String title;

    private String description;

    private String assignedToId;

    private String priority;

    private String status;

    private LocalDateTime dueDate;
}