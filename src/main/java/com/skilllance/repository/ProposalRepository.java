package com.skilllance.repository;

import com.skilllance.entity.Proposal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProposalRepository extends JpaRepository<Proposal, Long> {

    List<Proposal> findByProjectId(Long projectId);

    List<Proposal> findByFreelancerId(Long freelancerId);

    List<Proposal> findByStatus(String status);

    Optional<Proposal> findByProjectIdAndFreelancerId(
            Long projectId,
            Long freelancerId
    );

    boolean existsByProjectIdAndFreelancerId(
            Long projectId,
            Long freelancerId
    );
}
