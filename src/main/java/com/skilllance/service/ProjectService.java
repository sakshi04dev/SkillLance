package com.skilllance.service;

import com.skilllance.entity.Project;
import com.skilllance.repository.ProjectRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public Project createProject(Project project) {

        if (project == null) {
            throw new IllegalArgumentException(
                    "Project details are required"
            );
        }

        if (project.getTitle() == null
                || project.getTitle().isBlank()) {
            throw new IllegalArgumentException(
                    "Project title is required"
            );
        }

        if (project.getDescription() == null
                || project.getDescription().isBlank()) {
            throw new IllegalArgumentException(
                    "Project description is required"
            );
        }

        if (project.getBudget() == null
                || project.getBudget() <= 0) {
            throw new IllegalArgumentException(
                    "Budget must be greater than 0"
            );
        }

        if (project.getCategory() == null
                || project.getCategory().isBlank()) {
            throw new IllegalArgumentException(
                    "Category is required"
            );
        }

        if (project.getClientId() == null
                || project.getClientId() <= 0) {
            throw new IllegalArgumentException(
                    "Client ID must be greater than 0"
            );
        }

        project.setTitle(project.getTitle().trim());
        project.setDescription(project.getDescription().trim());
        project.setCategory(project.getCategory().trim());

        if (project.getStatus() == null
                || project.getStatus().isBlank()) {
            project.setStatus("OPEN");
        } else {
            project.setStatus(
                    normalizeStatus(project.getStatus())
            );
        }

        return projectRepository.save(project);
    }

    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    public Project getProjectById(Long id) {

        if (id == null || id <= 0) {
            throw new IllegalArgumentException(
                    "Invalid project ID"
            );
        }

        return projectRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Project not found"
                        )
                );
    }

    public List<Project> getProjectsByClient(Long clientId) {

        if (clientId == null || clientId <= 0) {
            throw new IllegalArgumentException(
                    "Invalid client ID"
            );
        }

        return projectRepository.findByClientId(clientId);
    }

    public List<Project> getProjectsByCategory(String category) {

        if (category == null || category.isBlank()) {
            throw new IllegalArgumentException(
                    "Category is required"
            );
        }

        return projectRepository.findByCategory(
                category.trim()
        );
    }

    public List<Project> getProjectsByStatus(String status) {

        if (status == null || status.isBlank()) {
            throw new IllegalArgumentException(
                    "Status is required"
            );
        }

        return projectRepository.findByStatus(
                normalizeStatus(status)
        );
    }

    public Project updateProject(
            Long id,
            Project updatedProject
    ) {

        Project existingProject = getProjectById(id);

        if (updatedProject == null) {
            throw new IllegalArgumentException(
                    "Project details are required"
            );
        }

        if (updatedProject.getTitle() == null
                || updatedProject.getTitle().isBlank()) {
            throw new IllegalArgumentException(
                    "Project title is required"
            );
        }

        if (updatedProject.getDescription() == null
                || updatedProject.getDescription().isBlank()) {
            throw new IllegalArgumentException(
                    "Project description is required"
            );
        }

        if (updatedProject.getBudget() == null
                || updatedProject.getBudget() <= 0) {
            throw new IllegalArgumentException(
                    "Budget must be greater than 0"
            );
        }

        if (updatedProject.getCategory() == null
                || updatedProject.getCategory().isBlank()) {
            throw new IllegalArgumentException(
                    "Category is required"
            );
        }

        existingProject.setTitle(
                updatedProject.getTitle().trim()
        );

        existingProject.setDescription(
                updatedProject.getDescription().trim()
        );

        existingProject.setBudget(
                updatedProject.getBudget()
        );

        existingProject.setCategory(
                updatedProject.getCategory().trim()
        );

        if (updatedProject.getStatus() == null
                || updatedProject.getStatus().isBlank()) {
            existingProject.setStatus("OPEN");
        } else {
            existingProject.setStatus(
                    normalizeStatus(
                            updatedProject.getStatus()
                    )
            );
        }

        return projectRepository.save(existingProject);
    }

    public void deleteProject(Long id) {

        if (id == null || id <= 0) {
            throw new IllegalArgumentException(
                    "Invalid project ID"
            );
        }

        if (!projectRepository.existsById(id)) {
            throw new IllegalArgumentException(
                    "Project not found"
            );
        }

        projectRepository.deleteById(id);
    }

    private String normalizeStatus(String status) {

        String normalizedStatus =
                status.trim().toUpperCase();

        if (!normalizedStatus.equals("OPEN")
                && !normalizedStatus.equals("IN_PROGRESS")
                && !normalizedStatus.equals("COMPLETED")
                && !normalizedStatus.equals("CANCELLED")) {

            throw new IllegalArgumentException(
                    "Status must be OPEN, IN_PROGRESS, COMPLETED or CANCELLED"
            );
        }

        return normalizedStatus;
    }
}