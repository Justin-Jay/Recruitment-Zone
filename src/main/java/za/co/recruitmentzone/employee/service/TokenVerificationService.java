package za.co.recruitmentzone.employee.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import za.co.recruitmentzone.employee.entity.Employee;
import za.co.recruitmentzone.employee.entity.EmployeeVerificationToken;
import za.co.recruitmentzone.employee.exception.TokenTimeOutException;
import za.co.recruitmentzone.employee.repository.EmployeeRepository;
import za.co.recruitmentzone.employee.repository.EmployeeVerificationTokenRepository;

import java.util.Optional;



@Service
public class TokenVerificationService {
    private static final Logger log = LoggerFactory.getLogger(TokenVerificationService.class);
    private final EmployeeRepository employeeRepository;
    @Value("${token.Secret.Key}")
    String SECRET_KEY;
    private final EmployeeVerificationTokenRepository tokenRepository;

    public TokenVerificationService(EmployeeRepository employeeRepository, EmployeeVerificationTokenRepository tokenRepository) {
        this.employeeRepository = employeeRepository;
        this.tokenRepository = tokenRepository;
    }

    public EmployeeVerificationToken saveToken(EmployeeVerificationToken employeeVerificationToken) {
        return tokenRepository.save(employeeVerificationToken);
    }

    public String verifyToken(String username, String token) throws TokenTimeOutException {
        String returnString = "";
        log.info("Employee Verification for employee {}", username);
        Optional<Employee> optionalEmployee = employeeRepository.findEmployeeByEmailIgnoreCase(username);
        EmployeeVerificationToken ov;
        if (optionalEmployee.isPresent()) {
            Employee emp = optionalEmployee.get();
            ov = tokenRepository.findEmployeeVerificationTokenByEmployeeID(emp.getEmployeeID());
            if (ov != null && !ov.isExpired()) {
                log.info("Token is not expired");
                // set account to Enabled

                boolean verified = verifyEncryptedToken(ov.getRandomVal(), token,SECRET_KEY);
                if (verified) {
                    log.info("Employee Email Verified!");
                    emp.setEnabled(true);
                    employeeRepository.save(emp);
                    returnString = """
                            Thank you for verifying your E-Mail Address.
                            You can now proceed start using the system.
                             """;
                    // delete used token
                    int rowCount = tokenRepository.deleteEmployeeVerificationTokenById(ov.getId());
                    log.info("Employee Token Has Been Deleted \n {} token deleted",rowCount);
                }

            } else {
                log.info("TOKEN TIMED OUT");
                throw new TokenTimeOutException("TOKEN TIMED OUT");
            }
        }

        return returnString;
    }

    public static boolean verifyEncryptedToken(String randomToken, String token,String key) {
        TokenGeneratorDecoder tokenGeneratorDecoder = new TokenGeneratorDecoder();
        boolean isValid = tokenGeneratorDecoder.validateToken(randomToken, token,key);
        System.out.println("Is Token Valid? " + isValid);
        return isValid;
    }

    // Validate the token


}
