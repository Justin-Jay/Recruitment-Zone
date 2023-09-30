package za.co.RecruitmentZone.Vacancy;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VacancyRepository extends CrudRepository<Vacancy, Long> {
    // You can add custom query methods here if needed
    List<Vacancy> findByActiveTrue();
}