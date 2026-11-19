package com.skilllance.repository;

import com.skilllance.entity.Contract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ContractRepository extends JpaRepository<Contract, Long> {

    Optional<Contract> findByProposalId(Long proposalId);

    List<Contract> findByClientId(Long clientId);

    List<Contract> findByFreelancerId(Long freelancerId);

    List<Contract> findByProjectId(Long projectId);

    List<Contract> findByStatus(String status);

    boolean existsByProposalId(Long proposalId);
}
