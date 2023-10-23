package za.co.RecruitmentZone.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import za.co.RecruitmentZone.entity.Candidate;

@Repository
public interface CandidateRepository extends CrudRepository<Candidate, Long> {
}
