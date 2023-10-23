package za.co.RecruitmentZone.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import za.co.RecruitmentZone.entity.Recruiter;

@Repository
public interface RecruiterRepository extends CrudRepository<Recruiter, Long> {
}
