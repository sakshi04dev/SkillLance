package com.skilllance.controller;

import com.skilllance.entity.Proposal;
import com.skilllance.service.ProposalService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/proposals")
@CrossOrigin(origins = "*")
public class ProposalController {

    private final ProposalService proposalService;

    public ProposalController(ProposalService proposalService) {
        this.proposalService = proposalService;
    }

    @PostMapping
    public ResponseEntity<Proposal> createProposal(
            @RequestBody Proposal proposal) {

        try {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(proposalService.createProposal(proposal));
        } catch (IllegalArgumentException exception) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Proposal>> getAllProposals() {
        return ResponseEntity.ok(proposalService.getAllProposals());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Proposal> getProposalById(
            @PathVariable Long id) {

        try {
            return ResponseEntity.ok(
                    proposalService.getProposalById(id)
            );
        } catch (IllegalArgumentException exception) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/project/{projectId}")
    public ResponseEntity<List<Proposal>> getProposalsByProject(
            @PathVariable Long projectId) {

        return ResponseEntity.ok(
                proposalService.getProposalsByProject(projectId)
        );
    }

    @GetMapping("/freelancer/{freelancerId}")
    public ResponseEntity<List<Proposal>> getProposalsByFreelancer(
            @PathVariable Long freelancerId) {

        return ResponseEntity.ok(
                proposalService.getProposalsByFreelancer(freelancerId)
        );
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Proposal>> getProposalsByStatus(
            @PathVariable String status) {

        return ResponseEntity.ok(
                proposalService.getProposalsByStatus(status)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<Proposal> updateProposal(
            @PathVariable Long id,
            @RequestBody Proposal proposal) {

        try {
            return ResponseEntity.ok(
                    proposalService.updateProposal(id, proposal)
            );
        } catch (IllegalArgumentException exception) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProposal(
            @PathVariable Long id) {

        try {
            proposalService.deleteProposal(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException exception) {
            return ResponseEntity.notFound().build();
        }
    }
}
