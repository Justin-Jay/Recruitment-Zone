package za.co.recruitmentzone.employee.events;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.threeten.bp.LocalDateTime;
import za.co.recruitmentzone.communication.entity.RegistrationMessage;
import za.co.recruitmentzone.communication.service.CommunicationService;
import za.co.recruitmentzone.employee.entity.Employee;
import za.co.recruitmentzone.employee.entity.EmployeeVerificationToken;
import za.co.recruitmentzone.employee.service.TokenGeneratorDecoder;
import za.co.recruitmentzone.employee.service.TokenVerificationService;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;

@Component
public class EmployeeEventListener {
    private final Logger log = LoggerFactory.getLogger(EmployeeEventListener.class);

    private final TokenVerificationService verificationTokenService;

    private final CommunicationService communicationService;

    @Value("${token.Secret.Key}")
    String SECRET_KEY;


    public EmployeeEventListener(TokenVerificationService verificationTokenService, CommunicationService communicationService) {
        this.verificationTokenService = verificationTokenService;
        this.communicationService = communicationService;
    }

    @EventListener
    public void onNewEmployeeEvent(NewEmployeeEvent event) {
        log.info("onNewEmployeeEvent started ");
        Employee newEmployee = event.getEmployee();
        String randomToken = generateRandomToken();
        System.out.println("Generated Token: " + randomToken);
        String plainToke = event.getEmployee().getName()+"%20Registration%20Token";
        EmployeeVerificationToken verificationToken = new EmployeeVerificationToken();
        TokenGeneratorDecoder tokenGeneratorDecoder = new TokenGeneratorDecoder();
        verificationToken.setToken(tokenGeneratorDecoder.encodeToken(randomToken,SECRET_KEY));
        verificationToken.setRandomVal(randomToken);
        verificationToken.setCreationTime(LocalDateTime.now());
        verificationToken.setExpirationTime(LocalDateTime.now().plusMinutes(15));
        verificationToken.setEmployee(newEmployee);
        EmployeeVerificationToken savedToken = verificationTokenService.saveToken(verificationToken);
        String encodedName = encodeParameter(event.getEmployee().getName());
        //String encodedName = URLEncoder.encode(event.getEmployee().getName(), StandardCharsets.UTF_8); // ? test this

        String url = event.getApplicationURL() + "/Employee/register/verifyEmail?token=" + plainToke + "&name=" + encodedName;

        log.info("URL LINK {}",url);
        log.info(" NewEmployeeEvent {}",event.toString());
        log.info(" NewEmployeeEvent  SOURCE {}",event.soureToString());
        communicationService.sendRegistrationEmail(new RegistrationMessage(event.getEmployee(),url));
        log.info("onNewEmployeeEvent finished ");
    }
    public static String encodeParameter(String parameter) {
        return URLEncoder.encode(parameter, StandardCharsets.UTF_8);
    }
    public static String generateRandomToken() {
        SecureRandom secureRandom = new SecureRandom();

        // Generate a random long value
        long randomLong = secureRandom.nextLong();

        // Convert the long value to a string
        StringBuilder token = new StringBuilder(String.valueOf(randomLong));

        // Ensure the token length is at least 75 characters
        while (token.length() < 75) {
            // Append additional random digits if needed
            token.append(secureRandom.nextInt(10));
        }

        // Trim the token to the desired length
        return token.substring(0, 75);
    }




}
