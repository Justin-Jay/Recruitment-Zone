package za.co.recruitmentzone.employee.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import za.co.recruitmentzone.employee.entity.EmployeeVerificationToken;

import java.util.Optional;

public interface EmployeeVerificationTokenRepository extends JpaRepository<EmployeeVerificationToken, Long> {
    //    Optional<EmployeeVerificationToken> findEmployeeVerificationTokenByEmployeeName(String name);

    @Query("SELECT t FROM EmployeeVerificationToken t JOIN t.employee e WHERE e.username = :username")
    EmployeeVerificationToken findEmployeeVerificationTokenByEmployeeID(@Param("username") String username);

    Optional<EmployeeVerificationToken> findEmployeeVerificationTokenByToken(String token);

    @Override
    void delete(EmployeeVerificationToken entity);



}
