package za.co.RecruitmentZone.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import za.co.RecruitmentZone.entity.Application;
import za.co.RecruitmentZone.entity.ApplicationUser;

import java.util.List;

@Repository
public interface ApplicationRepository extends CrudRepository<Application, Integer> {
    // You can add custom query methods here if needed

    @Query("SELECT a.vacancyID FROM application a")
    List<Application> findApplicationByAppliedForVacancy();

}