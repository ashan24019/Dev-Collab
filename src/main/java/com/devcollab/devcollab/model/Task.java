package com.devcollab.devcollab.model;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document(collection = "task")
public class Task {

    @Id
    private String id;

    private String title;

    private String description;

    private String projectId;

    private String createdById;

    private String assignedToId;

    private String priority; // "LOW", "MEDIUM", "HIGH"

    private String status;   // "TODO", "IN_PROGRESS", "DONE"

    private LocalDateTime dueDate;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;
}
