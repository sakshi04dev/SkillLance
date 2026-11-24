package com.skilllance.service;

import com.skilllance.entity.Contract;
import com.skilllance.repository.ContractRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ContractService {

    private final ContractRepository contractRepository;

    public ContractService(ContractRepository contractRepository) {
        this.contractRepository = contractRepository;
    }

    public Contract createContract(Contract contract) {

        validateContract(contract);

        if (contractRepository.existsByProposalId(
                contract.getProposalId()
        )) {
            throw new IllegalArgumentException(
                    "A contract already exists for this proposal"
            );
        }

        contract.setStatus(
                normalizeStatus(contract.getStatus())
        );

        return contractRepository.save(contract);
    }

    public List<Contract> getAllContracts() {
        return contractRepository.findAll();
    }

    public Optional<Contract> getContractById(Long id) {

        if (id == null || id <= 0) {
            throw new IllegalArgumentException(
                    "Invalid contract ID"
            );
        }

        return contractRepository.findById(id);
    }

    public Optional<Contract> getContractByProposalId(
            Long proposalId
    ) {

        if (proposalId == null || proposalId <= 0) {
            throw new IllegalArgumentException(
                    "Invalid proposal ID"
            );
        }

        return contractRepository.findByProposalId(
                proposalId
        );
    }

    public List<Contract> getContractsByClientId(
            Long clientId
    ) {

        if (clientId == null || clientId <= 0) {
            throw new IllegalArgumentException(
                    "Invalid client ID"
            );
        }

        return contractRepository.findByClientId(clientId);
    }

    public List<Contract> getContractsByFreelancerId(
            Long freelancerId
    ) {

        if (freelancerId == null || freelancerId <= 0) {
            throw new IllegalArgumentException(
                    "Invalid freelancer ID"
            );
        }

        return contractRepository.findByFreelancerId(
                freelancerId
        );
    }

    public List<Contract> getContractsByProjectId(
            Long projectId
    ) {

        if (projectId == null || projectId <= 0) {
            throw new IllegalArgumentException(
                    "Invalid project ID"
            );
        }

        return contractRepository.findByProjectId(projectId);
    }

    public List<Contract> getContractsByStatus(
            String status
    ) {

        if (status == null || status.isBlank()) {
            throw new IllegalArgumentException(
                    "Status is required"
            );
        }

        return contractRepository.findByStatus(
                normalizeStatus(status)
        );
    }

    public Contract updateContract(
            Long id,
            Contract updatedContract
    ) {

        if (id == null || id <= 0) {
            throw new IllegalArgumentException(
                    "Invalid contract ID"
            );
        }

        if (updatedContract == null) {
            throw new IllegalArgumentException(
                    "Contract details are required"
            );
        }

        Contract existingContract = contractRepository
                .findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Contract not found"
                        )
                );

        if (updatedContract.getAgreedAmount() == null
                || updatedContract.getAgreedAmount() <= 0) {
            throw new IllegalArgumentException(
                    "Agreed amount must be greater than 0"
            );
        }

        if (updatedContract.getStartDate() == null) {
            throw new IllegalArgumentException(
                    "Start date is required"
            );
        }

        if (updatedContract.getEndDate() == null) {
            throw new IllegalArgumentException(
                    "End date is required"
            );
        }

        if (updatedContract.getEndDate()
                .isBefore(updatedContract.getStartDate())) {
            throw new IllegalArgumentException(
                    "End date cannot be before start date"
            );
        }

        existingContract.setAgreedAmount(
                updatedContract.getAgreedAmount()
        );

        existingContract.setStartDate(
                updatedContract.getStartDate()
        );

        existingContract.setEndDate(
                updatedContract.getEndDate()
        );

        existingContract.setStatus(
                normalizeStatus(
                        updatedContract.getStatus()
                )
        );

        return contractRepository.save(existingContract);
    }

    public void deleteContract(Long id) {

        if (id == null || id <= 0) {
            throw new IllegalArgumentException(
                    "Invalid contract ID"
            );
        }

        if (!contractRepository.existsById(id)) {
            throw new IllegalArgumentException(
                    "Contract not found"
            );
        }

        contractRepository.deleteById(id);
    }

    private void validateContract(Contract contract) {

        if (contract == null) {
            throw new IllegalArgumentException(
                    "Contract details are required"
            );
        }

        if (contract.getProposalId() == null
                || contract.getProposalId() <= 0) {
            throw new IllegalArgumentException(
                    "Proposal ID must be greater than 0"
            );
        }

        if (contract.getProjectId() == null
                || contract.getProjectId() <= 0) {
            throw new IllegalArgumentException(
                    "Project ID must be greater than 0"
            );
        }

        if (contract.getClientId() == null
                || contract.getClientId() <= 0) {
            throw new IllegalArgumentException(
                    "Client ID must be greater than 0"
            );
        }

        if (contract.getFreelancerId() == null
                || contract.getFreelancerId() <= 0) {
            throw new IllegalArgumentException(
                    "Freelancer ID must be greater than 0"
            );
        }

        if (contract.getAgreedAmount() == null
                || contract.getAgreedAmount() <= 0) {
            throw new IllegalArgumentException(
                    "Agreed amount must be greater than 0"
            );
        }

        if (contract.getStartDate() == null) {
            throw new IllegalArgumentException(
                    "Start date is required"
            );
        }

        if (contract.getEndDate() == null) {
            throw new IllegalArgumentException(
                    "End date is required"
            );
        }

        if (contract.getEndDate()
                .isBefore(contract.getStartDate())) {
            throw new IllegalArgumentException(
                    "End date cannot be before start date"
            );
        }

        if (contract.getStatus() == null
                || contract.getStatus().isBlank()) {
            contract.setStatus("ACTIVE");
        }
    }

    private String normalizeStatus(String status) {

        if (status == null || status.isBlank()) {
            return "ACTIVE";
        }

        String normalizedStatus =
                status.trim().toUpperCase();

        if (!normalizedStatus.equals("ACTIVE")
                && !normalizedStatus.equals("COMPLETED")
                && !normalizedStatus.equals("CANCELLED")) {

            throw new IllegalArgumentException(
                    "Status must be ACTIVE, COMPLETED or CANCELLED"
            );
        }

        return normalizedStatus;
    }
}