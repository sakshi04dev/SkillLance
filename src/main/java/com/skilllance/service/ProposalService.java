package com.skilllance.service;

import com.skilllance.entity.Proposal;
import com.skilllance.repository.ProposalRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProposalService {

    private final ProposalRepository proposalRepository;

    public ProposalService(ProposalRepository proposalRepository) {
        this.proposalRepository = proposalRepository;
    }

    public Proposal createProposal(Proposal proposal) {

        validateProposal(proposal);

        if (proposalRepository.existsByProjectIdAndFreelancerId(
                proposal.getProjectId(),
                proposal.getFreelancerId()
        )) {
            throw new IllegalArgumentException(
                    "Freelancer has already submitted a proposal for this project"
            );
        }

        proposal.setCoverLetter(
                proposal.getCoverLetter().trim()
        );

        if (proposal.getStatus() == null
                || proposal.getStatus().isBlank()) {

            proposal.setStatus("PENDING");

        } else {
            proposal.setStatus(
                    normalizeStatus(proposal.getStatus())
            );
        }

        return proposalRepository.save(proposal);
    }

    public List<Proposal> getAllProposals() {
        return proposalRepository.findAll();
    }

    public Proposal getProposalById(Long id) {

        if (id == null || id <= 0) {
            throw new IllegalArgumentException(
                    "Invalid proposal ID"
            );
        }

        return proposalRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Proposal not found"
                        )
                );
    }

    public List<Proposal> getProposalsByProject(
            Long projectId
    ) {

        if (projectId == null || projectId <= 0) {
            throw new IllegalArgumentException(
                    "Invalid project ID"
            );
        }

        return proposalRepository.findByProjectId(projectId);
    }

    public List<Proposal> getProposalsByFreelancer(
            Long freelancerId
    ) {

        if (freelancerId == null || freelancerId <= 0) {
            throw new IllegalArgumentException(
                    "Invalid freelancer ID"
            );
        }

        return proposalRepository.findByFreelancerId(
                freelancerId
        );
    }

    public List<Proposal> getProposalsByStatus(
            String status
    ) {

        if (status == null || status.isBlank()) {
            throw new IllegalArgumentException(
                    "Status is required"
            );
        }

        return proposalRepository.findByStatus(
                normalizeStatus(status)
        );
    }

    public Proposal updateProposal(
            Long id,
            Proposal updatedProposal
    ) {

        Proposal existingProposal = getProposalById(id);

        if (updatedProposal == null) {
            throw new IllegalArgumentException(
                    "Proposal details are required"
            );
        }

        if (updatedProposal.getBidAmount() == null
                || updatedProposal.getBidAmount() <= 0) {
            throw new IllegalArgumentException(
                    "Bid amount must be greater than 0"
            );
        }

        if (updatedProposal.getEstimatedDays() == null
                || updatedProposal.getEstimatedDays() <= 0) {
            throw new IllegalArgumentException(
                    "Estimated days must be greater than 0"
            );
        }

        if (updatedProposal.getCoverLetter() == null
                || updatedProposal.getCoverLetter().isBlank()) {
            throw new IllegalArgumentException(
                    "Cover letter is required"
            );
        }

        existingProposal.setBidAmount(
                updatedProposal.getBidAmount()
        );

        existingProposal.setEstimatedDays(
                updatedProposal.getEstimatedDays()
        );

        existingProposal.setCoverLetter(
                updatedProposal.getCoverLetter().trim()
        );

        if (updatedProposal.getStatus() == null
                || updatedProposal.getStatus().isBlank()) {

            existingProposal.setStatus("PENDING");

        } else {

            existingProposal.setStatus(
                    normalizeStatus(
                            updatedProposal.getStatus()
                    )
            );
        }

        return proposalRepository.save(existingProposal);
    }

    public void deleteProposal(Long id) {

        if (id == null || id <= 0) {
            throw new IllegalArgumentException(
                    "Invalid proposal ID"
            );
        }

        if (!proposalRepository.existsById(id)) {
            throw new IllegalArgumentException(
                    "Proposal not found"
            );
        }

        proposalRepository.deleteById(id);
    }

    private void validateProposal(Proposal proposal) {

        if (proposal == null) {
            throw new IllegalArgumentException(
                    "Proposal details are required"
            );
        }

        if (proposal.getProjectId() == null
                || proposal.getProjectId() <= 0) {
            throw new IllegalArgumentException(
                    "Project ID must be greater than 0"
            );
        }

        if (proposal.getFreelancerId() == null
                || proposal.getFreelancerId() <= 0) {
            throw new IllegalArgumentException(
                    "Freelancer ID must be greater than 0"
            );
        }

        if (proposal.getBidAmount() == null
                || proposal.getBidAmount() <= 0) {
            throw new IllegalArgumentException(
                    "Bid amount must be greater than 0"
            );
        }

        if (proposal.getEstimatedDays() == null
                || proposal.getEstimatedDays() <= 0) {
            throw new IllegalArgumentException(
                    "Estimated days must be greater than 0"
            );
        }

        if (proposal.getCoverLetter() == null
                || proposal.getCoverLetter().isBlank()) {
            throw new IllegalArgumentException(
                    "Cover letter is required"
            );
        }

        if (proposal.getCoverLetter().trim().length() < 20) {
            throw new IllegalArgumentException(
                    "Cover letter must be at least 20 characters"
            );
        }
    }

    private String normalizeStatus(String status) {

        String normalizedStatus =
                status.trim().toUpperCase();

        if (!normalizedStatus.equals("PENDING")
                && !normalizedStatus.equals("ACCEPTED")
                && !normalizedStatus.equals("REJECTED")
                && !normalizedStatus.equals("WITHDRAWN")) {

            throw new IllegalArgumentException(
                    "Status must be PENDING, ACCEPTED, REJECTED or WITHDRAWN"
            );
        }

        return normalizedStatus;
    }
}