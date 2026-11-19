package com.skilllance.controller;

import com.skilllance.entity.Project;
import com.skilllance.service.ProjectService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
@CrossOrigin(origins = "*")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping
    public ResponseEntity<Project> createProject(@RequestBody Project project) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(projectService.createProject(project));
    }

    @GetMapping
    public ResponseEntity<List<Project>> getAllProjects() {
        return ResponseEntity.ok(projectService.getAllProjects());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Project> getProjectById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(projectService.getProjectById(id));
        } catch (IllegalArgumentException exception) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/client/{clientId}")
    public ResponseEntity<List<Project>> getProjectsByClient(
            @PathVariable Long clientId) {
        return ResponseEntity.ok(
                projectService.getProjectsByClient(clientId)
        );
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<Project>> getProjectsByCategory(
            @PathVariable String category) {
        return ResponseEntity.ok(
                projectService.getProjectsByCategory(category)
        );
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Project>> getProjectsByStatus(
            @PathVariable String status) {
        return ResponseEntity.ok(
                projectService.getProjectsByStatus(status)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<Project> updateProject(
            @PathVariable Long id,
            @RequestBody Project project) {
        try {
            return ResponseEntity.ok(
                    projectService.updateProject(id, project)
            );
        } catch (IllegalArgumentException exception) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable Long id) {
        try {
            projectService.deleteProject(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException exception) {
            return ResponseEntity.notFound().build();
        }
    }
}
