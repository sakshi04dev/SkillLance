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
        if (project.getStatus() == null || project.getStatus().isBlank()) {
            project.setStatus("OPEN");
        }

        return projectRepository.save(project);
    }

    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    public Project getProjectById(Long id) {
        return projectRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Project not found"));
    }

    public List<Project> getProjectsByClient(Long clientId) {
        return projectRepository.findByClientId(clientId);
    }

    public List<Project> getProjectsByCategory(String category) {
        return projectRepository.findByCategory(category);
    }

    public List<Project> getProjectsByStatus(String status) {
        return projectRepository.findByStatus(status);
    }

    public Project updateProject(Long id, Project updatedProject) {
        Project existingProject = getProjectById(id);

        existingProject.setTitle(updatedProject.getTitle());
        existingProject.setDescription(updatedProject.getDescription());
        existingProject.setBudget(updatedProject.getBudget());
        existingProject.setCategory(updatedProject.getCategory());
        existingProject.setStatus(updatedProject.getStatus());
        existingProject.setClientId(updatedProject.getClientId());

        return projectRepository.save(existingProject);
    }

    public void deleteProject(Long id) {
        if (!projectRepository.existsById(id)) {
            throw new IllegalArgumentException("Project not found");
        }

        projectRepository.deleteById(id);
    }
}
