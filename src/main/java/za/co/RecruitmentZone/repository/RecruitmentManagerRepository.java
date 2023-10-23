package za.co.RecruitmentZone.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import za.co.RecruitmentZone.entity.RecruitmentManager;

@Repository
public interface RecruitmentManagerRepository extends CrudRepository<RecruitmentManager, Long> {
}
