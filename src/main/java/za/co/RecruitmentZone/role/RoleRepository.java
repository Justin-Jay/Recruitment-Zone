package za.co.RecruitmentZone.role;


import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

@EnableJpaRepositories
public interface RoleRepository extends CrudRepository<Role,Integer> {
    Optional<Role> findRoleByAuthority(String authority);
}
