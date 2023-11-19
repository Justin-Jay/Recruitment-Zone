package za.co.RecruitmentZone.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import za.co.RecruitmentZone.entity.domain.Employee;

import java.util.Optional;


public interface EmployeeRepository extends JpaRepository<Employee,Long> {
    Optional<Employee> findByUsername(String username);
}
