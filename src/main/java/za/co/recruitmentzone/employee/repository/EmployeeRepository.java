package za.co.recruitmentzone.employee.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import za.co.recruitmentzone.employee.entity.Employee;

import java.util.Optional;


public interface EmployeeRepository extends JpaRepository<Employee, Long> {


    @Query("SELECT e FROM Employee e WHERE LOWER(e.email) = LOWER(:email)")
    Optional<Employee> findEmployeeByEmailIgnoreCase(@Param("email") String email);

    @Query("SELECT e FROM Employee e WHERE LOWER(e.first_name) = LOWER(:name)")
    Optional<Employee> findEmployeeByName(@Param("name") String name);

}
