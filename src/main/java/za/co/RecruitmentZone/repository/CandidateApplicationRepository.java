package za.co.RecruitmentZone.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import za.co.RecruitmentZone.entity.domain.CandidateApplication;
public interface CandidateApplicationRepository extends JpaRepository<CandidateApplication, Long> {
}
