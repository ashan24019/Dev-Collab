package com.devcollab.devcollab.repository;

import com.devcollab.devcollab.enums.TaskStatus;
import com.devcollab.devcollab.model.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface TaskRepository extends MongoRepository<Task, String> {
    Page<Task> findByProjectId(String projectId, Pageable pageable);
    List<Task> findByAssignedToId(String assignedToId);
    List<Task> findByProjectIdAndStatus(String projectId, TaskStatus status);
    List<Task> findByProjectIdAndPriority(String projectId, String priority);
}
