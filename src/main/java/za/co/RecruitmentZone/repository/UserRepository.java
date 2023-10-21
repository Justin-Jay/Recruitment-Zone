package za.co.RecruitmentZone.repository;


import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.CrudRepository;
import za.co.RecruitmentZone.Entity.ApplicationUser;

import java.util.Optional;

@EnableJpaRepositories
public interface UserRepository extends CrudRepository<ApplicationUser, Integer> {
    Optional<ApplicationUser> findApplicationUserByUsername(String username);
}
