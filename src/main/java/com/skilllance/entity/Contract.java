package com.skilllance.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "contracts")
public class Contract {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Proposal ID is required")
    @Positive(message = "Proposal ID must be greater than 0")
    @Column(nullable = false, unique = true)
    private Long proposalId;

    @NotNull(message = "Project ID is required")
    @Positive(message = "Project ID must be greater than 0")
    @Column(nullable = false)
    private Long projectId;

    @NotNull(message = "Client ID is required")
    @Positive(message = "Client ID must be greater than 0")
    @Column(nullable = false)
    private Long clientId;

    @NotNull(message = "Freelancer ID is required")
    @Positive(message = "Freelancer ID must be greater than 0")
    @Column(nullable = false)
    private Long freelancerId;

    @NotNull(message = "Agreed amount is required")
    @Positive(message = "Agreed amount must be greater than 0")
    @Column(nullable = false)
    private Double agreedAmount;

    @NotNull(message = "Start date is required")
    @Column(nullable = false)
    private LocalDate startDate;

    @NotNull(message = "End date is required")
    @Column(nullable = false)
    private LocalDate endDate;

    @Column(nullable = false)
    private String status;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    public Contract() {
    }

    public Contract(
            Long proposalId,
            Long projectId,
            Long clientId,
            Long freelancerId,
            Double agreedAmount,
            LocalDate startDate,
            LocalDate endDate,
            String status
    ) {
        this.proposalId = proposalId;
        this.projectId = projectId;
        this.clientId = clientId;
        this.freelancerId = freelancerId;
        this.agreedAmount = agreedAmount;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.createdAt = LocalDateTime.now();
    }

    @PrePersist
    protected void onCreate() {

        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }

        if (status == null || status.isBlank()) {
            status = "ACTIVE";
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

    public Long getProposalId() {
        return proposalId;
    }

    public void setProposalId(Long proposalId) {
        this.proposalId = proposalId;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public Long getFreelancerId() {
        return freelancerId;
    }

    public void setFreelancerId(Long freelancerId) {
        this.freelancerId = freelancerId;
    }

    public Double getAgreedAmount() {
        return agreedAmount;
    }

    public void setAgreedAmount(Double agreedAmount) {
        this.agreedAmount = agreedAmount;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
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