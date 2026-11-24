package com.skilllance.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "proposals",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "unique_project_freelancer",
                        columnNames = {"project_id", "freelancer_id"}
                )
        }
)
public class Proposal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Project ID is required")
    @Positive(message = "Project ID must be greater than 0")
    @Column(name = "project_id", nullable = false)
    private Long projectId;

    @NotNull(message = "Freelancer ID is required")
    @Positive(message = "Freelancer ID must be greater than 0")
    @Column(name = "freelancer_id", nullable = false)
    private Long freelancerId;

    @NotNull(message = "Bid amount is required")
    @Positive(message = "Bid amount must be greater than 0")
    @Column(nullable = false)
    private Double bidAmount;

    @NotNull(message = "Estimated days are required")
    @Positive(message = "Estimated days must be greater than 0")
    @Column(nullable = false)
    private Integer estimatedDays;

    @NotBlank(message = "Cover letter is required")
    @Size(
            min = 20,
            max = 3000,
            message = "Cover letter must be between 20 and 3000 characters"
    )
    @Column(nullable = false, length = 3000)
    private String coverLetter;

    @Column(nullable = false)
    private String status;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    public Proposal() {
    }

    public Proposal(
            Long projectId,
            Long freelancerId,
            Double bidAmount,
            Integer estimatedDays,
            String coverLetter,
            String status
    ) {
        this.projectId = projectId;
        this.freelancerId = freelancerId;
        this.bidAmount = bidAmount;
        this.estimatedDays = estimatedDays;
        this.coverLetter = coverLetter;
        this.status = status;
        this.createdAt = LocalDateTime.now();
    }

    @PrePersist
    protected void onCreate() {

        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }

        if (status == null || status.isBlank()) {
            status = "PENDING";
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

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Long getFreelancerId() {
        return freelancerId;
    }

    public void setFreelancerId(Long freelancerId) {
        this.freelancerId = freelancerId;
    }

    public Double getBidAmount() {
        return bidAmount;
    }

    public void setBidAmount(Double bidAmount) {
        this.bidAmount = bidAmount;
    }

    public Integer getEstimatedDays() {
        return estimatedDays;
    }

    public void setEstimatedDays(Integer estimatedDays) {
        this.estimatedDays = estimatedDays;
    }

    public String getCoverLetter() {
        return coverLetter;
    }

    public void setCoverLetter(String coverLetter) {
        this.coverLetter = coverLetter;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}