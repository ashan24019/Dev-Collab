package com.devcollab.devcollab.dto;

import com.devcollab.devcollab.enums.TaskStatus;
import com.devcollab.devcollab.model.Task;

public class TaskMapper {

    public static Task toTask(CreateTaskDTO dto, String createdById) {
        Task task = new Task();

        task.setTitle(dto.getTitle());
        task.setDescription(dto.getDescription());
        task.setProjectId(dto.getProjectId());
        task.setCreatedById(createdById);
        task.setAssignedToId(dto.getAssignedToId());
        task.setPriority(dto.getPriority());
        task.setStatus(TaskStatus.TODO);
        task.setDueDate(dto.getDueDate());

        return task;
    }

    public static TaskResponseDTO toResponseDTO(Task task) {
        TaskResponseDTO responseDTO = new TaskResponseDTO();

        responseDTO.setId(task.getId());
        responseDTO.setTitle(task.getTitle());
        responseDTO.setDescription(task.getDescription());
        responseDTO.setProjectId(task.getProjectId());
        responseDTO.setCreatedById(task.getCreatedById());
        responseDTO.setAssignedToId(task.getAssignedToId());
        responseDTO.setPriority(task.getPriority());
        responseDTO.setStatus(task.getStatus());
        responseDTO.setDueDate(task.getDueDate());
        responseDTO.setCreatedAt(task.getCreatedAt());
        responseDTO.setUpdatedAt(task.getUpdatedAt());

        return responseDTO;
    }
}