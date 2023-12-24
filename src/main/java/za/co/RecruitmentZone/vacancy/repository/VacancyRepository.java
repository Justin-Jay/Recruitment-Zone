package za.co.RecruitmentZone.vacancy.repository;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import za.co.RecruitmentZone.util.Enums.VacancyStatus;
import za.co.RecruitmentZone.vacancy.entity.Vacancy;

import java.util.List;


public interface VacancyRepository extends JpaRepository<Vacancy, Long> {
    // You can add custom query methods here if needed

    @Query("SELECT v FROM Vacancy v WHERE LOWER(v.job_title) LIKE LOWER(CONCAT('%', :title, '%'))")
    List<Vacancy> findVacanciesByJobTitle(@Param("title") String title);

    List<Vacancy> findVacanciesByStatus(VacancyStatus status);

    @Query("SELECT v FROM Vacancy v")
    List<Vacancy> findAllVacancies();


}