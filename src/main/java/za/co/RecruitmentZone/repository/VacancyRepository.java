package za.co.RecruitmentZone.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import za.co.RecruitmentZone.entity.domain.Vacancy;

import java.util.List;


public interface VacancyRepository extends JpaRepository<Vacancy, Integer> {
    // You can add custom query methods here if needed
    List<Vacancy> findByActiveTrue();

    @Query(value = """
            SELECT v FROM Vacancy v
            """)
    List<Vacancy> findAllVacancies();

    @Query(value = """
            SELECT v FROM Vacancy v WHERE v.employeeID = :id
            """)
    List<Vacancy> findByEmployeeID(Integer id);




}