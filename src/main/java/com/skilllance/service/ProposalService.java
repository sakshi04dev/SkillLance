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
        if (proposalRepository.existsByProjectIdAndFreelancerId(
                proposal.getProjectId(),
                proposal.getFreelancerId())) {
            throw new IllegalArgumentException(
                    "Freelancer has already submitted a proposal for this project"
            );
        }

        if (proposal.getStatus() == null || proposal.getStatus().isBlank()) {
            proposal.setStatus("PENDING");
        }

        return proposalRepository.save(proposal);
    }

    public List<Proposal> getAllProposals() {
        return proposalRepository.findAll();
    }

    public Proposal getProposalById(Long id) {
        return proposalRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("Proposal not found"));
    }

    public List<Proposal> getProposalsByProject(Long projectId) {
        return proposalRepository.findByProjectId(projectId);
    }

    public List<Proposal> getProposalsByFreelancer(Long freelancerId) {
        return proposalRepository.findByFreelancerId(freelancerId);
    }

    public List<Proposal> getProposalsByStatus(String status) {
        return proposalRepository.findByStatus(status);
    }

    public Proposal updateProposal(Long id, Proposal updatedProposal) {
        Proposal existingProposal = getProposalById(id);

        existingProposal.setBidAmount(updatedProposal.getBidAmount());
        existingProposal.setEstimatedDays(updatedProposal.getEstimatedDays());
        existingProposal.setCoverLetter(updatedProposal.getCoverLetter());
        existingProposal.setStatus(updatedProposal.getStatus());

        return proposalRepository.save(existingProposal);
    }

    public void deleteProposal(Long id) {
        if (!proposalRepository.existsById(id)) {
            throw new IllegalArgumentException("Proposal not found");
        }

        proposalRepository.deleteById(id);
    }
}
