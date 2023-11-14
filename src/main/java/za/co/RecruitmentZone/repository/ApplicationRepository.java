package za.co.RecruitmentZone.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.co.RecruitmentZone.entity.domain.Application;

import java.util.List;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Integer> {
    // You can add custom query methods here if needed

    @Query("SELECT a FROM Application a")
    List<Application> findApplicationByAppliedForVacancy();

}