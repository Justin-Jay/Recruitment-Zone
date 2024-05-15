package za.co.recruitmentzone.communication.events.Listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import za.co.recruitmentzone.communication.entity.AdminContactMessage;
import za.co.recruitmentzone.communication.events.AdminMessageEvent;
import za.co.recruitmentzone.communication.events.WebsiteMessageEvent;
import za.co.recruitmentzone.communication.entity.ContactMessage;
import za.co.recruitmentzone.vacancy.repository.VacancyRepository;
import za.co.recruitmentzone.communication.service.CommunicationService;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Transactional
@Component
public class EmailEventListener {
    private final VacancyRepository vacancyRepository;

    private final CommunicationService communicationService;
    Logger log = LoggerFactory.getLogger(EmailEventListener.class);

    public EmailEventListener(VacancyRepository vacancyRepository,CommunicationService communicationService) {
        this.vacancyRepository = vacancyRepository;
       this.communicationService = communicationService;
    }


    @EventListener
    public void onWebsiteQueryReceived(WebsiteMessageEvent event) {
        log.info("Executing onWebsiteQueryReceived");
        ContactMessage m = event.getMessage();

        sendAutoResponse(m);

        sendWebsiteNotification(m);

        log.info("DONE Executing onWebsiteQueryReceived");
    }


    public void sendAutoResponse(ContactMessage m){
        try {
            ExecutorService executor = Executors.newSingleThreadExecutor();
            log.info("About to submit request");
            executor.submit(() -> {
                communicationService.sendAutoResponse( m.getEmail(), "Auto Response - Query Received", m.getName(), m.getEmail(), m.getMessageBody());
            });
            //communicationService.sendAutoResponse( m.getEmail(), "Auto Response - Query Received", m.getName(), m.getEmail(), m.getMessageBody());

            log.info("sendAutoResponse submitted");
        } catch (Exception e) {
            log.info("Failed to send Email Query");
        }
    }

    public void sendWebsiteNotification(ContactMessage m){
        try {
            ExecutorService executor = Executors.newSingleThreadExecutor();
            log.info("About to submit request");
            executor.submit(() -> {
                communicationService.sendWebsiteQuery(m);
            });
            //communicationService.sendWebsiteQuery(m);
            log.info("sendWebsiteNotification submitted");
        } catch (Exception e) {
            log.info("Failed to send Email Query");
        }
    }

    @EventListener
    public void onFileUpload(AdminMessageEvent event) {
        log.info("Executing onFileUpload");
        AdminContactMessage m = event.getMessage();

        ExecutorService executor = Executors.newSingleThreadExecutor();
        log.info("About to submit request");
        executor.submit(() -> {
            communicationService.sendAdminNotification(m);
        });

        log.info("DONE Executing onFileUpload");
    }


}
