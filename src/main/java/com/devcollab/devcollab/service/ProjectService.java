package com.devcollab.devcollab.service;

import com.devcollab.devcollab.dto.CreateProjectDTO;
import com.devcollab.devcollab.dto.ProjectMapper;
import com.devcollab.devcollab.dto.ProjectResponseDTO;
import com.devcollab.devcollab.exception.ResourceNotFoundException;
import com.devcollab.devcollab.model.Project;
import com.devcollab.devcollab.model.User;
import com.devcollab.devcollab.repository.ProjectRepository;
import com.devcollab.devcollab.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public List<ProjectResponseDTO> getAllProjects() {
        return projectRepository.findAll()
                .stream()
                .map(ProjectMapper :: toResponseDTO)
                .collect(Collectors.toList());
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

    public List<ProjectResponseDTO> getProjectsByUser(String userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        List<Project> asOwner = projectRepository.findByOwnerId(userId);
        List<Project> asMember = projectRepository.findByMemberIdsContaining(userId);

        return Stream.concat(asOwner.stream(), asMember.stream())
                .distinct()
                .map(ProjectMapper::toResponseDTO)
                .collect(Collectors.toList());
    }
}
