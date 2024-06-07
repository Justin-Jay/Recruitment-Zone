package za.co.recruitmentzone.application.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import za.co.recruitmentzone.application.entity.Application;
import za.co.recruitmentzone.vacancy.entity.Vacancy;


import java.util.List;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {
    // You can add custom query methods here if needed
    @Query("SELECT a FROM Application a WHERE a.vacancy = :vacancy")
    Page<Application> findAllByVacancy(Pageable pageable, @Param("vacancy") Vacancy vacancy);


}