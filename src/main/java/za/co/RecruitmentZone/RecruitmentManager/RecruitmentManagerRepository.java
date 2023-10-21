package za.co.RecruitmentZone.RecruitmentManager;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import za.co.RecruitmentZone.Vacancy.Vacancy;

import java.util.List;

@Repository
public interface RecruitmentManagerRepository extends CrudRepository<RecruitmentManager, Long> {
}
