package com.devcollab.devcollab.repository;

import com.devcollab.devcollab.enums.ProjectStatus;
import com.devcollab.devcollab.model.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ProjectRepository extends MongoRepository<Project, String> {
    Page<Project> findByOwnerId(String ownerId, Pageable pageable);
    Page<Project> findByMemberIdsContaining(String userId, Pageable pageable);
    List<Project> findByStatus(ProjectStatus status);
    Page<Project> findByOwnerIdOrMemberIds(String ownerId, List<String> memberIds, Pageable pageable);
}
