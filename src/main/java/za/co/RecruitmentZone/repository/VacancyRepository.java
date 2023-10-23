package za.co.RecruitmentZone.repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import za.co.RecruitmentZone.entity.Vacancy;

import java.util.List;

@Repository
public interface VacancyRepository extends CrudRepository<Vacancy, Integer> {
    // You can add custom query methods here if needed
    List<Vacancy> findByActiveTrue();

    @Query("SELECT v.vacancyID FROM VACANCY v")
    List<Vacancy> findAllVacancies();


}