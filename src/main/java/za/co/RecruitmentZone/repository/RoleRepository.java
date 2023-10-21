package za.co.RecruitmentZone.repository;


import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.CrudRepository;
import za.co.RecruitmentZone.Entity.Role;

import java.util.Optional;

@EnableJpaRepositories
public interface RoleRepository extends CrudRepository<Role,Integer> {
    Optional<Role> findRoleByAuthority(String authority);
}
