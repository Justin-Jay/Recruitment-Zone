package za.co.recruitmentzone.communication.service;


import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import za.co.recruitmentzone.application.entity.Application;
import za.co.recruitmentzone.communication.entity.AdminContactMessage;
import za.co.recruitmentzone.communication.entity.ContactMessage;
import za.co.recruitmentzone.communication.events.RegistrationMessageEvent;
import za.co.recruitmentzone.employee.entity.Employee;

import java.time.LocalDate;


@Slf4j
@Component
public class CommunicationService {


    @Value("${spring.mail.username}")
    String rzoneMailAddress;
    @Value("${rzone.to.address}")
    String rzoneToAddress;
    private final TemplateEngine templateEngine;

    private final JavaMailSenderImpl javaMailSender;

    public CommunicationService(TemplateEngine templateEngine, JavaMailSenderImpl javaMailSender) {
        this.templateEngine = templateEngine;
        this.javaMailSender = javaMailSender;
    }

    public void sendWebsiteQuery(ContactMessage message) {
        log.info("<---WEBSITE QUERY EMAIL --->");
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
        //log.info("<- to - subject - userName - userEmail - userQuery - >");
        log.info("< sendWebsiteQuery - {},{},{},{} - >", rzoneToAddress, message.getSubject(), message.getName(), message.getSubject());
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
            log.info("<--- sendWebsiteQuery MessagingException ---> \n",e);
            // Handle exception (log or throw custom exception)
        }
        catch (Exception e) {
            log.info("<--- sendWebsiteQuery Exception ---> \n",e);
        }
    }

    public void sendAutoResponse(String to, String subject, String userName, String userEmail, String userQuery) {
        log.info("<---AUTO EMAIL RESPONSE --->");
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
        //log.info("<- to - subject - userName - userEmail - userQuery - >");
        log.info("< sendAutoResponse - {}, {}, {},{},{}>", to, subject, userName, userEmail, userQuery);
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
            log.info("<--- sendAutoResponse MessagingException ---> \n",e);
        }
        catch (Exception e) {
            log.info("<--- sendAutoResponse Exception ---> \n",e);
        }
    }

    public boolean sendRegistrationEmail(RegistrationMessageEvent message) {
        log.info("<---SEND REGISTRATION EMAIL --->");
        log.info(" RegistrationMessage {}", message);
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
        log.info("<- to - subject - userName - userEmail - userQuery - >");
        log.info(" mimeMessage {}", mimeMessage);
        try {
            helper.setFrom(rzoneMailAddress);
            log.info(" mimeMessage {}", mimeMessage);
            log.info(" email we sending to {}", message.getEmployee().getEmail());
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
            log.info("<--- sendRegistrationEmail MessagingException ---> \n",e);
            return false;
        }
        catch (Exception e) {
            log.info("<--- sendWebsiteQuery Exception ---> \n",e);
            return false;
        }
    }

    public void sendAdminNotification(AdminContactMessage message) {
        log.info("<--- sendAdminNotification message ---> \n {} ", message.printAdminContactMessage());
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
        try {
            helper.setFrom(rzoneMailAddress);
//            helper.setTo(message.getEventToEmail());
            helper.setTo("admin.agent@kiunga.co.za");
            helper.setSubject(message.getSubject());
            helper.setText(message.getMessageBody());
            javaMailSender.send(mimeMessage);
            log.info("<--- sendAdminNotification Message Sent successfully --->");
        } catch (MessagingException e) {
            log.info("<--- sendAdminNotification MessagingException --->",e);
        }
        catch (Exception e) {
            log.info("<--- sendAdminNotification Exception ---> \n",e);

        }

    }


    public void sendApplicationAcknowledgement(Application application) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
        log.info("<---  sendApplicationAcknowledgement application ---> \n {} ", application.printApplication());
        try {
            helper.setFrom(rzoneMailAddress);
            helper.setTo(application.getCandidate().getEmail_address());
            helper.setSubject("Application Received");

            Context context = new Context();
            context.setVariable("vacancyName", application.getVacancy().getJobTitle());
            context.setVariable("applicantName", application.getCandidate().getFirst_name());
            context.setVariable("currentYear", LocalDate.now().getYear());


            String emailContent = templateEngine.process("emailTemplates/application-acknowledgment", context);
            helper.setText(emailContent, true);

            javaMailSender.send(mimeMessage);
            log.info("<--- sendApplicationAcknowledgement sent --->");

        }
        catch (MessagingException e) {
            log.info("<--- sendApplicationAcknowledgement MessagingException \n --->", e);
        }
        catch (Exception e) {
            log.info("<--- sendApplicationAcknowledgement Exception ---> \n",e);

        }
    }

    /*



      String mailContent = "<p> Hi, " + employee.getFirst_name() + ", </p>" +
                "<p><b>You recently requested to reset your password,</b>" + "\n" +
                "Please, follow the link below to complete the action.</p>" +
                "<a href=\"" + url + "\">Reset password</a>" +
                "<p> Users Registration Portal Service";

        MimeMessage message = mailSender.createMimeMessage();
        var messageHelper = new MimeMessageHelper(message);
        messageHelper.setFrom("localtestaccount@kiunga.co.za", senderName);
        messageHelper.setTo(theUser.getEmail());
        messageHelper.setSubject(subject);
        messageHelper.setText(mailContent, true);
        mailSender.send(message);










  */

 /*   public void sentPasswordResetRequestEmail(Employee employee, String url) {
        log.info("<---AUTO EMAIL RESPONSE --->");
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
        //log.info("<- to - subject - userName - userEmail - userQuery - >");
        log.info("< sendAutoResponse - {}, {}, {},{},{}>",to,subject,userName,userEmail,userQuery);
        try {
            helper.setFrom(rzoneMailAddress);
            helper.setTo(to);
            helper.setSubject(subject);

            Context context = new Context();
            context.setVariable("User", userName);
            context.setVariable("UserEmail", userEmail);



            context.setVariable("url", url);



            String emailContent = templateEngine.process("emailTemplates/password-reset", context);
            helper.setText(emailContent, true);

            javaMailSender.send(mimeMessage);
            log.info("<---AUTO EMAIL RESPONSE SUCCESS MESSAGE SENT--->");

        } catch (MessagingException e) {
            log.info("<---AUTO EMAIL RESPONSE FAILED--->");
            log.info(e.getMessage());
            // Handle exception (log or throw custom exception)
        }
    }*/

}
