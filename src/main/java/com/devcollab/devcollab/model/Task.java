package com.devcollab.devcollab.model;

import com.devcollab.devcollab.enums.TaskPriority;
import com.devcollab.devcollab.enums.TaskStatus;
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

    private TaskPriority priority;

    private TaskStatus status;

    private LocalDateTime dueDate;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;
}
