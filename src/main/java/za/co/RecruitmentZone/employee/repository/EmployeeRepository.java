package za.co.RecruitmentZone.employee.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import za.co.RecruitmentZone.employee.entity.Employee;

import java.util.Optional;


public interface EmployeeRepository extends JpaRepository<Employee,Long> {
    Optional<Employee> findByUsername(String username);
}
