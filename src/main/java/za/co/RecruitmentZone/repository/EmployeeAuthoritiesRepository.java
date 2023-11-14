package za.co.RecruitmentZone.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import za.co.RecruitmentZone.entity.domain.EmployeeAuthorities;

public interface EmployeeAuthoritiesRepository extends JpaRepository<EmployeeAuthorities, Long> {
}
