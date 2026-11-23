package com.skilllance.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "projects")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Project title is required")
    @Size(max = 150, message = "Project title must not exceed 150 characters")
    @Column(nullable = false)
    private String title;

    @NotBlank(message = "Project description is required")
    @Size(min = 20, max = 2000,
            message = "Description must be between 20 and 2000 characters")
    @Column(nullable = false, length = 2000)
    private String description;

    @NotNull(message = "Budget is required")
    @Positive(message = "Budget must be greater than 0")
    @Column(nullable = false)
    private Double budget;

    @NotBlank(message = "Category is required")
    @Size(max = 100, message = "Category must not exceed 100 characters")
    @Column(nullable = false)
    private String category;

    @Column(nullable = false)
    private String status;

    @NotNull(message = "Client ID is required")
    @Positive(message = "Client ID must be greater than 0")
    @Column(nullable = false)
    private Long clientId;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    public Project() {
    }

    public Project(
            String title,
            String description,
            Double budget,
            String category,
            String status,
            Long clientId
    ) {
        this.title = title;
        this.description = description;
        this.budget = budget;
        this.category = category;
        this.status = status;
        this.clientId = clientId;
        this.createdAt = LocalDateTime.now();
    }

    @PrePersist
    protected void onCreate() {

        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }

        if (status == null || status.isBlank()) {
            status = "OPEN";
        } else {
            status = status.trim().toUpperCase();
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getBudget() {
        return budget;
    }

    public void setBudget(Double budget) {
        this.budget = budget;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}