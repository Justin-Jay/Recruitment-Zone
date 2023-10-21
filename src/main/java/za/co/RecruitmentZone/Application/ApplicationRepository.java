package za.co.RecruitmentZone.Application;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicationRepository extends CrudRepository<Application, Long> {
    // You can add custom query methods here if needed
}