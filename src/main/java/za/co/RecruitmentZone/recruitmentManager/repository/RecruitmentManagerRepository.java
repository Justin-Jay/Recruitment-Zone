package za.co.RecruitmentZone.recruitmentManager.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import za.co.RecruitmentZone.recruitmentManager.service.RecruitmentManager;

@Repository
public interface RecruitmentManagerRepository extends CrudRepository<RecruitmentManager, Long> {
}
