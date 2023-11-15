package za.co.RecruitmentZone.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import za.co.RecruitmentZone.entity.domain.Job;

public interface JobRepository extends JpaRepository<Job, Long> {


}
