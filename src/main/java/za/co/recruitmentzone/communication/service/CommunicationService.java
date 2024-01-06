package za.co.recruitmentzone.communication.service;


import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import za.co.recruitmentzone.communication.entity.ContactMessage;
import za.co.recruitmentzone.communication.entity.RegistrationMessage;


@Slf4j
@Component
public class CommunicationService {

    @Value("${spring.mail.username}")
    String rzoneMailAddress;

    @Value("${rzone.to.address}")
    String rzoneToAddress;
    @Value("${spring.mail.password}")
    String rzoneMailPassword;

    private final TemplateEngine templateEngine;
    private final JavaMailSender javaMailSender;
    public CommunicationService(TemplateEngine templateEngine, JavaMailSender javaMailSender) {
        this.templateEngine = templateEngine;
        this.javaMailSender = javaMailSender;
    }

    public void sendWebsiteQuery(ContactMessage message) {
        log.info("<---WEBSITE QUERY EMAIL --->");
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
        log.info("<- to - subject - userName - userEmail - userQuery - >");
        log.info("< {} - {} - {} - \n {} - >", rzoneToAddress,message.getSubject(),message.getName(),message.getSubject());
        try {
            helper.setFrom(rzoneMailAddress);
            helper.setTo(rzoneToAddress);
            helper.setSubject("Website Automated Notification");

            Context context = new Context();
            context.setVariable("User", message.getName());
            context.setVariable("UserEmail", message.getEmail());
            context.setVariable("UserQuery", message.getMessageBody());

            String emailContent = templateEngine.process("emailTemplates/website-query", context);
            helper.setText(emailContent, true);

            javaMailSender.send(mimeMessage);
            log.info("<---AUTO EMAIL RESPONSE SUCCESS MESSAGE SENT--->");

        } catch (MessagingException e) {
            log.info("<---AUTO EMAIL RESPONSE FAILED--->");
            log.info(e.getMessage());
            // Handle exception (log or throw custom exception)
        }
    }

    public void sendAutoResponse(String to, String subject, String userName, String userEmail, String userQuery) {
        log.info("<---AUTO EMAIL RESPONSE --->");
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
        log.info("<- to - subject - userName - userEmail - userQuery - >");
        log.info("< {} - {} - {} - {} - {} - >",to,subject,userName,userEmail,userQuery);
        try {
            helper.setFrom(rzoneMailAddress);
            helper.setTo(to);
            helper.setSubject(subject);

            Context context = new Context();
            context.setVariable("User", userName);
            context.setVariable("UserEmail", userEmail);
            context.setVariable("UserQuery", userQuery);

            String emailContent = templateEngine.process("emailTemplates/auto-response-template", context);
            helper.setText(emailContent, true);

            javaMailSender.send(mimeMessage);
            log.info("<---AUTO EMAIL RESPONSE SUCCESS MESSAGE SENT--->");

        } catch (MessagingException e) {
            log.info("<---AUTO EMAIL RESPONSE FAILED--->");
            log.info(e.getMessage());
            // Handle exception (log or throw custom exception)
        }
    }


    public boolean sendRegistrationEmail(RegistrationMessage message) {
        log.info("<---SEND REGISTRATION EMAIL --->");
        log.info(" RegistrationMessage {}",message);
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
        log.info("<- to - subject - userName - userEmail - userQuery - >");
        log.info(" mimeMessage {}",mimeMessage);
        try {
            helper.setFrom(rzoneMailAddress);
            log.info(" mimeMessage {}",mimeMessage);
            log.info(" email we sending to {}",message.getEmployee().getEmail());
            helper.setTo(message.getEmployee().getEmail());
            helper.setSubject("Recruitment Zone Registration");

            Context context = new Context();
            context.setVariable("registeredUser", message.getEmployee().getFirst_name());
            context.setVariable("registrationLink", message.getApplicationURL());

            String emailContent = templateEngine.process("emailTemplates/user-registered", context);
            helper.setText(emailContent, true);

            javaMailSender.send(mimeMessage);
            log.info("<---EMAIL MESSAGE SENT--->");

            return true;
        } catch (MessagingException e) {
            log.info("<---EMAIL RESPONSE FAILED--->");
            log.info(e.getMessage());
            // Handle exception (log or throw custom exception)
            return false;
        }
    }
}
