package za.co.recruitmentzone.employee.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import za.co.recruitmentzone.employee.entity.PasswordResetToken;

import java.util.Optional;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {

    Optional<PasswordResetToken> findByToken(String theToken);
}
