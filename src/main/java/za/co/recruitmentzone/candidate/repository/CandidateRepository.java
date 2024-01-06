package za.co.recruitmentzone.candidate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.co.recruitmentzone.candidate.entity.Candidate;

@Repository
public interface CandidateRepository extends JpaRepository<Candidate, Long> {

}
