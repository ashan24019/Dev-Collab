package com.devcollab.devcollab.dto;

import com.devcollab.devcollab.enums.ProjectStatus;
import com.devcollab.devcollab.model.Project;

public class ProjectMapper {

    public static Project toProject(CreateProjectDTO dto) {
        Project project = new Project();
        project.setName(dto.getName());
        project.setDescription(dto.getDescription());
        project.setOwnerId(dto.getOwnerId());
        project.setStatus(ProjectStatus.ACTIVE);

        return project;
    }

    public static ProjectResponseDTO toResponseDTO(Project project) {
        ProjectResponseDTO dto = new ProjectResponseDTO();
        dto.setId(project.getId());
        dto.setName(project.getName());
        dto.setDescription(project.getDescription());
        dto.setOwnerId(project.getOwnerId());
        dto.setMemberIds(project.getMemberIds());
        dto.setStatus(project.getStatus());
        dto.setCreatedAt(project.getCreatedAt());
        dto.setUpdatedAt(project.getUpdatedAt());

        return dto;
    }
}
