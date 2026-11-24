package com.skilllance.controller;

import com.skilllance.entity.Contract;
import com.skilllance.service.ContractService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contracts")
@CrossOrigin(origins = "*")
public class ContractController {

    private final ContractService contractService;

    public ContractController(ContractService contractService) {
        this.contractService = contractService;
    }

    @PostMapping
    public ResponseEntity<Contract> createContract(
            @Valid @RequestBody Contract contract
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(contractService.createContract(contract));
    }

    @GetMapping
    public ResponseEntity<List<Contract>> getAllContracts() {
        return ResponseEntity.ok(
                contractService.getAllContracts()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Contract> getContractById(
            @PathVariable Long id
    ) {
        try {
            return contractService.getContractById(id)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (IllegalArgumentException exception) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/proposal/{proposalId}")
    public ResponseEntity<Contract> getContractByProposalId(
            @PathVariable Long proposalId
    ) {
        try {
            return contractService.getContractByProposalId(proposalId)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (IllegalArgumentException exception) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/client/{clientId}")
    public ResponseEntity<List<Contract>> getContractsByClientId(
            @PathVariable Long clientId
    ) {
        return ResponseEntity.ok(
                contractService.getContractsByClientId(clientId)
        );
    }

    @GetMapping("/freelancer/{freelancerId}")
    public ResponseEntity<List<Contract>> getContractsByFreelancerId(
            @PathVariable Long freelancerId
    ) {
        return ResponseEntity.ok(
                contractService.getContractsByFreelancerId(clientId)
        );
    }

    @GetMapping("/project/{projectId}")
    public ResponseEntity<List<Contract>> getContractsByProjectId(
            @PathVariable Long projectId
    ) {
        return ResponseEntity.ok(
                contractService.getContractsByProjectId(projectId)
        );
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Contract>> getContractsByStatus(
            @PathVariable String status
    ) {
        return ResponseEntity.ok(
                contractService.getContractsByStatus(status)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<Contract> updateContract(
            @PathVariable Long id,
            @Valid @RequestBody Contract contract
    ) {
        try {
            return ResponseEntity.ok(
                    contractService.updateContract(id, contract)
            );
        } catch (IllegalArgumentException exception) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContract(
            @PathVariable Long id
    ) {
        try {
            contractService.deleteContract(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException exception) {
            return ResponseEntity.notFound().build();
        }
    }
}