package com.devcollab.devcollab.service;

import com.devcollab.devcollab.dto.CreateProjectDTO;
import com.devcollab.devcollab.dto.PageResponseDTO;
import com.devcollab.devcollab.dto.ProjectMapper;
import com.devcollab.devcollab.dto.ProjectResponseDTO;
import com.devcollab.devcollab.enums.UserRole;
import com.devcollab.devcollab.exception.ResourceNotFoundException;
import com.devcollab.devcollab.model.Project;
import com.devcollab.devcollab.model.User;
import com.devcollab.devcollab.repository.ProjectRepository;
import com.devcollab.devcollab.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserRepository userRepository;

    public ProjectResponseDTO createProject(CreateProjectDTO dto) {
        userRepository.findById(dto.getOwnerId())
                .orElseThrow(() -> new ResourceNotFoundException("Owner not found with id: " + dto.getOwnerId()));
        Project project = ProjectMapper.toProject(dto);
        Project saved = projectRepository.save(project);

        return ProjectMapper.toResponseDTO(saved);
    }

    public PageResponseDTO<ProjectResponseDTO> getAllProjects(
            String currentUserId,
            String currentUserRole,
            int page,
            int size) {

        List<ProjectResponseDTO> projectList;

        Pageable pageable = PageRequest.of(page, size,  Sort.by("createdAt").descending());

        if (currentUserRole.equals(UserRole.ADMIN.name())) {
            projectList =  projectRepository.findAll(pageable)
                    .stream()
                    .map(ProjectMapper :: toResponseDTO)
                    .toList();
        } else {
            projectList =  projectRepository.findByOwnerIdOrMemberIds(currentUserId, List.of(currentUserId), pageable)
                    .stream()
                    .map(ProjectMapper :: toResponseDTO)
                    .toList();
        }

        long totalElements = projectRepository.count();
        int totalPages = (int) Math.ceil((double) totalElements / size);

        return new PageResponseDTO<>(
                projectList,
                page,
                size,
                totalElements,
                totalPages,
                page + 1 < totalPages,
                page > 0
        );
    }

    public ProjectResponseDTO getProjectById(String id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found with id: " + id));
        return ProjectMapper.toResponseDTO(project);
    }

    public ProjectResponseDTO addMember(String projectId, String userId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found with id: " + projectId));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        if(!project.getMemberIds().contains(user.getId())) {
            project.getMemberIds().add(user.getId());
            projectRepository.save(project);
        }

        return ProjectMapper.toResponseDTO(project);
    }

    public PageResponseDTO<ProjectResponseDTO> getProjectsByUser(String userId, int page, int size) {
        userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());

        Page<Project> asOwner = projectRepository.findByOwnerId(userId, pageable);
        Page<Project> asMember = projectRepository.findByMemberIdsContaining(userId, pageable);

        List<ProjectResponseDTO> projectList = Stream.concat(
                        asOwner.getContent().stream(),
                        asMember.getContent().stream())
                .distinct()
                .map(ProjectMapper :: toResponseDTO)
                .collect(Collectors.toList());

        long totalElements = asOwner.getTotalElements() + asMember.getTotalElements();
        int totalPages = (int) Math.ceil((double) totalElements / size);

        return new PageResponseDTO<>(
                projectList,
                page,
                size,
                totalElements,
                totalPages,
                page + 1 < totalPages,
                page > 0
        );
    }
}
