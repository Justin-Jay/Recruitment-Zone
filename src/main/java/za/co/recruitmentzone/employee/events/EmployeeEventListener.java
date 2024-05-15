package za.co.recruitmentzone.employee.events;


import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import za.co.recruitmentzone.communication.events.RegistrationMessageEvent;
import za.co.recruitmentzone.communication.service.CommunicationService;
import za.co.recruitmentzone.employee.entity.Employee;
import za.co.recruitmentzone.employee.entity.EmployeeVerificationToken;
import za.co.recruitmentzone.employee.service.TokenVerificationService;


import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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


        //2. generate a token for the user
        String randomToken = UUID.randomUUID().toString();

        log.info("Generated Token: {} ", randomToken);
        String plainToke = event.getEmployee().getName()+"%20Registration%20Token";
        EmployeeVerificationToken verificationToken = new EmployeeVerificationToken();

        verificationToken.setToken(randomToken);
        verificationToken.setCreationTime(LocalDateTime.now());
        verificationToken.setExpirationTime(LocalDateTime.now().plusMinutes(15));
        verificationToken.setEmployee(newEmployee);
        EmployeeVerificationToken savedToken = verificationTokenService.saveToken(verificationToken);



        String url = event.getApplicationURL() + "/Employee/register/verifyEmail?token=" + randomToken;

        log.info("URL LINK {}",url);
        log.info(" NewEmployeeEvent {}",event.toString());
        //log.info(" NewEmployeeEvent  SOURCE {}",event.soureToString());

        ExecutorService executor = Executors.newSingleThreadExecutor();

        log.info("About to sendRegistrationEmail");

        executor.submit(() -> {
            communicationService.sendRegistrationEmail(new RegistrationMessageEvent(event.getEmployee(),url));
        });


        log.info("onNewEmployeeEvent complted ");
    }















}
