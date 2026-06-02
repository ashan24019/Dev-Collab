package com.devcollab.devcollab.controller;

import com.devcollab.devcollab.dto.CreateProjectDTO;
import com.devcollab.devcollab.dto.ProjectResponseDTO;
import com.devcollab.devcollab.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @PostMapping
    public ResponseEntity<ProjectResponseDTO> createProject(@RequestBody CreateProjectDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(projectService.createProject(dto));
    }

    @GetMapping
    public ResponseEntity<List<ProjectResponseDTO>> getAllProjects() {
        return ResponseEntity.ok(projectService.getAllProjects());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectResponseDTO> getProjectById(@PathVariable String id) {
        return ResponseEntity.ok(projectService.getProjectById(id));
    }

    @PostMapping("/{projectId}/members/{userId}")
    public ResponseEntity<ProjectResponseDTO> addMember(
            @PathVariable String projectId,
            @PathVariable String userId) {
        return ResponseEntity.ok(projectService.addMember(projectId, userId));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ProjectResponseDTO>> getProjectsByUser(@PathVariable String userId) {
        return ResponseEntity.ok(projectService.getProjectsByUser(userId));
    }
}
