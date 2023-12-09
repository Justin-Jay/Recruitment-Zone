package za.co.RecruitmentZone.communication.service;


import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import za.co.RecruitmentZone.communication.entity.ContactMessage;



@Slf4j
@Component
public class CommunicationService {

    @Value("${spring.mail.username}")
    String kiungaMailAddress;

    @Value("${kiunga.to.address}")
    String kiungaToAddress;
    @Value("${spring.mail.password}")
    String kiungaMailPassword;

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
        log.info("< {} - {} - {} - \n {} - >",kiungaToAddress,message.getSubject(),message.getName(),message.getSubject());
        try {
            helper.setFrom(kiungaMailAddress);
            helper.setTo(kiungaToAddress);
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
            helper.setFrom(kiungaMailAddress);
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

}
