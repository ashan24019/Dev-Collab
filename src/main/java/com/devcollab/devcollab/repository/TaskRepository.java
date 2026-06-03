package com.devcollab.devcollab.repository;

import com.devcollab.devcollab.model.Task;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface TaskRepository extends MongoRepository<Task, String> {
    List<Task> findByProjectId(String projectId);
    List<Task> findByAssignedToId(String assignedToId);
    List<Task> findByProjectIdAndStatus(String projectId, String status);
    List<Task> findByProjectIdAndPriority(String projectId, String priority);
}
