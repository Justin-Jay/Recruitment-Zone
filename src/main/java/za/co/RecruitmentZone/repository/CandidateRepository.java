package za.co.RecruitmentZone.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.co.RecruitmentZone.entity.domain.Candidate;

@Repository
public interface CandidateRepository extends JpaRepository<Candidate, Long> {

}
