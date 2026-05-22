package com.example.lession9_1.Repository;

import com.example.lession9_1.Model.Entity.Candidate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CandidateRepository extends JpaRepository<Candidate, Long> {
    boolean existsByEmail(String email);
}
