package za.co.RecruitmentZone.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import za.co.RecruitmentZone.entity.domain.Vacancy;

import java.util.List;


public interface VacancyRepository extends JpaRepository<Vacancy, Long> {
    // You can add custom query methods here if needed

}