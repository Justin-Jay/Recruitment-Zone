package za.co.RecruitmentZone.candidate.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import za.co.RecruitmentZone.entity.Candidate;

@Repository
interface CandidateRepository extends CrudRepository<Candidate, Long> {
}
