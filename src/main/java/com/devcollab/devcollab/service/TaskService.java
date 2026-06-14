package com.devcollab.devcollab.service;

import com.devcollab.devcollab.dto.CreateTaskDTO;
import com.devcollab.devcollab.dto.TaskMapper;
import com.devcollab.devcollab.dto.TaskResponseDTO;
import com.devcollab.devcollab.dto.UpdateTaskDTO;
import com.devcollab.devcollab.exception.ResourceNotFoundException;
import com.devcollab.devcollab.model.Task;
import com.devcollab.devcollab.repository.ProjectRepository;
import com.devcollab.devcollab.repository.TaskRepository;
import com.devcollab.devcollab.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProjectRepository projectRepository;

    public TaskResponseDTO createTask(CreateTaskDTO dto, String createdById) {
        projectRepository.findById(dto.getProjectId())
                .orElseThrow(() -> new ResourceNotFoundException("Project not found with id " + dto.getProjectId()));

        userRepository.findById(createdById)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + createdById));

        if (dto.getAssignedToId() != null && !dto.getAssignedToId().isBlank()) {
            userRepository.findById(dto.getAssignedToId())
                    .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + dto.getAssignedToId()));
        }

        Task task = TaskMapper.toTask(dto, createdById);
        Task saved = taskRepository.save(task);
        return TaskMapper.toResponseDTO(saved);
    }

    public TaskResponseDTO updateTask(String id, UpdateTaskDTO dto) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id " + id));

        if(dto.getTitle() != null) task.setTitle(dto.getTitle());
        if(dto.getDescription() != null) task.setDescription(dto.getDescription());
        if(dto.getAssignedToId() != null) task.setAssignedToId(dto.getAssignedToId());
        if(dto.getPriority() != null) task.setPriority(dto.getPriority());
        if(dto.getStatus() != null) task.setStatus(dto.getStatus());
        if(dto.getDueDate() != null) task.setDueDate(dto.getDueDate());

        Task updated = taskRepository.save(task);
        return TaskMapper.toResponseDTO(updated);
    }

    public void deleteTask(String id) {
        taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id " + id));
        taskRepository.deleteById(id);
    }

    public List<TaskResponseDTO> getTasksByProject(String projectId) {
        projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found with id " + projectId));

        return taskRepository.findByProjectId(projectId)
                .stream()
                .map(TaskMapper::toResponseDTO)
                .collect(Collectors.toList());

    }

    public List<TaskResponseDTO> getTasksByAssignee(String userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + userId));

        return taskRepository.findByAssignedToId(userId)
                .stream()
                .map(TaskMapper::toResponseDTO)
                .collect(Collectors.toList());

    }

    public List<TaskResponseDTO> getTasksByProjectAndStatus(String projectId,String status) {
        projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found with id " + projectId));

        return taskRepository.findByProjectIdAndStatus(projectId, status)
                .stream()
                .map(TaskMapper::toResponseDTO)
                .collect(Collectors.toList());
    }
}
