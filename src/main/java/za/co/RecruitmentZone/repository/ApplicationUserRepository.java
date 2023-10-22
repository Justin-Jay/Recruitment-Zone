package za.co.RecruitmentZone.repository;


import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.CrudRepository;
import za.co.RecruitmentZone.entity.ApplicationUser;

import java.util.Optional;

@EnableJpaRepositories
public interface ApplicationUserRepository extends CrudRepository<ApplicationUser, Integer> {
    Optional<ApplicationUser> findApplicationUserByUsername(String username);
}
