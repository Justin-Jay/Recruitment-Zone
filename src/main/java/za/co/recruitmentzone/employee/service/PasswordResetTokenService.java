package za.co.recruitmentzone.employee.service;


import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import za.co.recruitmentzone.employee.entity.Employee;
import za.co.recruitmentzone.employee.entity.PasswordResetToken;
import za.co.recruitmentzone.employee.repository.EmployeeRepository;
import za.co.recruitmentzone.employee.repository.PasswordResetTokenRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PasswordResetTokenService {

    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;


    public String validatePasswordResetToken(String theToken) {
        Optional<PasswordResetToken> passwordResetToken = passwordResetTokenRepository.findByToken(theToken);
        if (passwordResetToken.isEmpty()){
            return "invalid";
        }

        if (passwordResetToken.get().isExpired()){
            return "expired";
        }
        else {
            return "valid";
        }

    }

    public Optional<PasswordResetToken> findUserByPasswordResetToken(String theToken) {
       return passwordResetTokenRepository.findByToken(theToken);
    }

    public void resetPassword(Employee employee, String newPassword) {
        employee.setPassword(passwordEncoder.encode(newPassword));
        employeeRepository.save(employee);
    }

    public void createPasswordResetTokenForUser(Employee employee, String passwordResetToken) {
        PasswordResetToken resetToken = new PasswordResetToken(passwordResetToken, employee);
        passwordResetTokenRepository.save(resetToken);
    }
}
