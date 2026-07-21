package com.devcollab.devcollab.service;

import com.devcollab.devcollab.dto.CreateProjectDTO;
import com.devcollab.devcollab.dto.ProjectResponseDTO;
import com.devcollab.devcollab.enums.ProjectStatus;
import com.devcollab.devcollab.exception.ResourceNotFoundException;
import com.devcollab.devcollab.model.Project;
import com.devcollab.devcollab.model.User;
import com.devcollab.devcollab.repository.ProjectRepository;
import com.devcollab.devcollab.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProjectServiceTest {
    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ProjectService projectService;

    User testUser;

    Project testProject;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setId("mock user id");

        testProject = new Project();
        testProject.setId("mock id");
        testProject.setName("mock name");
        testProject.setOwnerId(testUser.getId());
        testProject.setStatus(ProjectStatus.ACTIVE);
        testProject.setMemberIds(new ArrayList<>());
    }

    @Test
    void createProject_shouldSaveProject_whenOwnerExists() {
        when(userRepository.findById("mock user id")).thenReturn(Optional.of(testUser));
        when(projectRepository.save(any())).thenReturn(testProject);

        CreateProjectDTO dto = new CreateProjectDTO();
        dto.setName(testProject.getName());
        dto.setDescription(testProject.getDescription());
        dto.setOwnerId(testProject.getOwnerId());

        ProjectResponseDTO result = projectService.createProject(dto);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(testProject.getId());
        assertThat(result.getName()).isEqualTo(testProject.getName());
        verify(projectRepository, times(1)).save(any());
    }

    @Test
    void createProject_shouldThrowException_whenOwnerNotFound() {
        when(userRepository.findById("nonexistent")).thenReturn(Optional.empty());

        CreateProjectDTO dto = new CreateProjectDTO();
        dto.setName(testProject.getName());
        dto.setDescription(testProject.getDescription());
        dto.setOwnerId("nonexistent");

        assertThatThrownBy(() -> projectService.createProject(dto))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("nonexistent");
    }

    @Test
    void getProjectById_shouldReturnProject_whenExists() {
        when(projectRepository.findById("mock id")).thenReturn(Optional.of(testProject));

        ProjectResponseDTO result = projectService.getProjectById("mock id");

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(testProject.getId());
        assertThat(result.getName()).isEqualTo(testProject.getName());
    }

    @Test
    void getProjectById_shouldThrowException_whenNotFound() {
        when(projectRepository.findById("nonexistent")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> projectService.getProjectById("nonexistent"))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("nonexistent");
    }

    @Test
    void addMember_shouldAddMember_whenProjectAndUserExist() {
        when(userRepository.findById("mock user id")).thenReturn(Optional.of(testUser));
        when(projectRepository.findById("mock id")).thenReturn(Optional.of(testProject));
        when(projectRepository.save(any())).thenReturn(testProject);

        ProjectResponseDTO result = projectService.addMember("mock id", "mock user id");

        assertThat(result.getId()).isEqualTo(testProject.getId());
        verify(projectRepository, times(1)).save(any());
    }
}
