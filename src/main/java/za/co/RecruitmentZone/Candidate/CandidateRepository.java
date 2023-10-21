package za.co.RecruitmentZone.Candidate;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
interface CandidateRepository extends CrudRepository<Candidate, Long> {
}
