package za.co.RecruitmentZone.employee.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import za.co.RecruitmentZone.employee.entity.Employee;
import za.co.RecruitmentZone.employee.entity.EmployeeVerificationToken;
import za.co.RecruitmentZone.vacancy.entity.Vacancy;

import java.util.List;
import java.util.Optional;

public interface EmployeeVerificationTokenRepository extends JpaRepository<EmployeeVerificationToken, Long> {
    //    Optional<EmployeeVerificationToken> findEmployeeVerificationTokenByEmployeeName(String name);

    @Query("SELECT t FROM EmployeeVerificationToken t JOIN t.employee e WHERE e.employeeID = :employeeId")
    EmployeeVerificationToken findEmployeeVerificationTokenByEmployeeID(@Param("employeeId") Long employeeId);

    @Query("DELETE FROM EmployeeVerificationToken t WHERE t.employee.employeeID = :employeeId")
    int deleteEmployeeVerificationTokenById(@Param("employeeId") Long employeeId);


}
