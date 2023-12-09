package za.co.RecruitmentZone.candidate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.co.RecruitmentZone.candidate.entity.Candidate;

@Repository
public interface CandidateRepository extends JpaRepository<Candidate, Long> {

}
