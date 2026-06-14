package com.devcollab.devcollab.controller;

import com.devcollab.devcollab.config.SecurityUtils;
import com.devcollab.devcollab.dto.CreateTaskDTO;
import com.devcollab.devcollab.dto.TaskResponseDTO;
import com.devcollab.devcollab.dto.UpdateTaskDTO;
import com.devcollab.devcollab.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'MEMBER')")
    public ResponseEntity<TaskResponseDTO> createTask(@Valid @RequestBody CreateTaskDTO dto) {
        String currentUserId = SecurityUtils.getCurrentUserId();
        return ResponseEntity.status(HttpStatus.CREATED).body(taskService.createTask(dto, currentUserId));
    }

    @GetMapping("/project/{projectId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MEMBER')")
    public ResponseEntity<List<TaskResponseDTO>> getTasksByProjectId(@PathVariable String projectId) {
        return ResponseEntity.ok(taskService.getTasksByProject(projectId));
    }

    @GetMapping("/project/{projectId}/status/{status}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MEMBER')")
    public ResponseEntity<List<TaskResponseDTO>> getTasksByProjectIdAndStatus(
            @PathVariable String projectId,
            @PathVariable String status) {
        return ResponseEntity.ok(taskService.getTasksByProjectAndStatus(projectId, status));
    }

    @GetMapping("/assignee/{userId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MEMBER')")
    public ResponseEntity<List<TaskResponseDTO>> getTasksByAssignee(@PathVariable String userId) {
        return ResponseEntity.ok(taskService.getTasksByAssignee(userId));
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MEMBER')")
    public ResponseEntity<TaskResponseDTO> updateTask(
            @PathVariable String id,
            @RequestBody UpdateTaskDTO dto) {
        return ResponseEntity.ok(taskService.updateTask(id, dto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<TaskResponseDTO> deleteTask(@PathVariable String id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }
}
