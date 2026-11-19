package com.skilllance.service;

import com.skilllance.entity.Contract;
import com.skilllance.repository.ContractRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContractService {

    private final ContractRepository contractRepository;

    public ContractService(ContractRepository contractRepository) {
        this.contractRepository = contractRepository;
    }

    public Contract createContract(Contract contract) {
        if (contract.getProposalId() != null
                && contractRepository.existsByProposalId(contract.getProposalId())) {
            throw new IllegalArgumentException(
                    "A contract already exists for this proposal"
            );
        }

        return contractRepository.save(contract);
    }

    public List<Contract> getAllContracts() {
        return contractRepository.findAll();
    }

    public Optional<Contract> getContractById(Long id) {
        return contractRepository.findById(id);
    }

    public Optional<Contract> getContractByProposalId(Long proposalId) {
        return contractRepository.findByProposalId(proposalId);
    }

    public List<Contract> getContractsByClientId(Long clientId) {
        return contractRepository.findByClientId(clientId);
    }

    public List<Contract> getContractsByFreelancerId(Long freelancerId) {
        return contractRepository.findByFreelancerId(freelancerId);
    }

    public List<Contract> getContractsByProjectId(Long projectId) {
        return contractRepository.findByProjectId(projectId);
    }

    public List<Contract> getContractsByStatus(String status) {
        return contractRepository.findByStatus(status);
    }

    public Contract updateContract(Long id, Contract updatedContract) {
        Contract existingContract = contractRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Contract not found"
                ));

        existingContract.setStatus(updatedContract.getStatus());
        existingContract.setStartDate(updatedContract.getStartDate());
        existingContract.setEndDate(updatedContract.getEndDate());
        existingContract.setAgreedAmount(updatedContract.getAgreedAmount());

        return contractRepository.save(existingContract);
    }

    public void deleteContract(Long id) {
        if (!contractRepository.existsById(id)) {
            throw new IllegalArgumentException("Contract not found");
        }

        contractRepository.deleteById(id);
    }
}
