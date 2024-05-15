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

import java.util.Calendar;
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

    public String verifyToken(String token) throws TokenTimeOutException {

        String returnString = "";

        Optional<EmployeeVerificationToken> theToken = tokenRepository.findEmployeeVerificationTokenByToken(token);

       if (theToken.isPresent() ) {
           log.info("Employee Verification for employee {}", theToken.get().getEmployee().getUsername());

           //ov = tokenRepository.findEmployeeVerificationTokenByEmployeeID(emp.getUsername());
           if (!theToken.get().isExpired()) {
               log.info("Token is not expired");

               log.info("Employee Email Verified!");
               theToken.get().getEmployee().setEnabled(true);

               employeeRepository.save(theToken.get().getEmployee());

               returnString = """
                    Thank you for verifying your E-Mail Address.
                    You can now proceed to log in.
                    """;

               // delete used token

/*               int rowCount = tokenRepository.deleteEmployeeVerificationTokenById(theToken.get().getEmployee().getUsername());
               log.info("Employee Token Has Been Deleted \n {} token deleted", rowCount);*/


             /*  try {
                   tokenRepository.delete(theToken.get());
                   log.info("Employee Token Has Been Deleted ");
               }
               catch (Exception e) {
                   log.info("Failed to delete employee token");
               }
              */



           } else {
               returnString = "Token is not expired";
           }
       }
       else if (theToken.get().getEmployee() == null )  {
           returnString = "Employee not found";
       }
       else {
           log.info("  theToken is null ");

           returnString = "Invalid Token";
       }


        return returnString;
    }


}
