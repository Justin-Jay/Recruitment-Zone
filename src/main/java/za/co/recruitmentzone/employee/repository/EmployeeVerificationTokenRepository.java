package za.co.recruitmentzone.employee.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import za.co.recruitmentzone.employee.entity.EmployeeVerificationToken;

public interface EmployeeVerificationTokenRepository extends JpaRepository<EmployeeVerificationToken, Long> {
    //    Optional<EmployeeVerificationToken> findEmployeeVerificationTokenByEmployeeName(String name);

    @Query("SELECT t FROM EmployeeVerificationToken t JOIN t.employee e WHERE e.employeeID = :employeeId")
    EmployeeVerificationToken findEmployeeVerificationTokenByEmployeeID(@Param("employeeId") Long employeeId);

    @Query("DELETE FROM EmployeeVerificationToken t WHERE t.employee.employeeID = :employeeId")
    int deleteEmployeeVerificationTokenById(@Param("employeeId") Long employeeId);


}
