package za.co.recruitmentzone.vacancy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import za.co.recruitmentzone.client.entity.Client;
import za.co.recruitmentzone.util.enums.VacancyStatus;
import za.co.recruitmentzone.vacancy.entity.Vacancy;

import java.util.List;

@Repository
public interface VacancyRepository extends JpaRepository<Vacancy, Long> {
    // You can add custom query methods here if needed

    @Query("SELECT v FROM Vacancy v WHERE LOWER(v.jobTitle) LIKE LOWER(CONCAT('%', :title, '%'))")
    List<Vacancy> findVacanciesByJobTitle(@Param("title") String title);

    List<Vacancy> findVacanciesByStatus(VacancyStatus status);

    @Query("SELECT v FROM Vacancy v")
    List<Vacancy> findAllVacancies();

    List<Vacancy> findByClient(Client client);
}