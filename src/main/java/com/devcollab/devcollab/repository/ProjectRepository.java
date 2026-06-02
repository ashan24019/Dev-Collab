package com.devcollab.devcollab.repository;

import com.devcollab.devcollab.model.Project;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ProjectRepository extends MongoRepository<Project, String> {
    List<Project> findByOwnerId(String ownerId);
    List<Project> findByMemberIdsContaining(String userId);
    List<Project> findByStatus(String status);

}
