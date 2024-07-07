package za.co.recruitmentzone.communication.events.Listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import za.co.recruitmentzone.application.entity.Application;
import za.co.recruitmentzone.candidate.events.CandidateAppliedEvent;
import za.co.recruitmentzone.communication.entity.AdminContactMessage;
import za.co.recruitmentzone.communication.events.AdminMessageEvent;

import za.co.recruitmentzone.communication.events.WebsiteMessageEvent;
import za.co.recruitmentzone.communication.entity.ContactMessage;
import za.co.recruitmentzone.communication.service.CommunicationService;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Transactional
@Component
public class EmailEventListener {

    private final CommunicationService communicationService;
    Logger log = LoggerFactory.getLogger(EmailEventListener.class);

    public EmailEventListener(CommunicationService communicationService) {
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



    public void sendAutoResponse(ContactMessage m) {
        try (ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()) {
            //  ExecutorService executor = Executors.newSingleThreadExecutor();
            log.info("About to submit request");
            executor.submit(() -> {
                communicationService.sendAutoResponse(m.getEmail(), "Auto Response - Query Received", m.getName(), m.getEmail(), m.getMessageBody());
            });

            log.info("sendAutoResponse submitted");
        } catch (Exception e) {
            log.info("<--- sendAutoResponse Exception ---> \n ",e);
        }
    }

    public void sendWebsiteNotification(ContactMessage m) {
        try (ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()) {
            //  ExecutorService executor = Executors.newSingleThreadExecutor();
            log.info("About to submit request");
            executor.submit(() -> {
                communicationService.sendWebsiteQuery(m);
            });
            log.info("sendWebsiteNotification submitted");
        } catch (Exception e) {
            log.info("<--- sendWebsiteNotification Exception  ---> \n ",e);
        }
    }

    @EventListener
    public void onFileUpload(AdminMessageEvent event) {
        log.info("Executing onFileUpload");

        try (ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()) {
            AdminContactMessage m = event.getMessage();

            log.info("About to submit request");
            executor.submit(() -> {
                communicationService.sendAdminNotification(m);
            });
           // communicationService.sendAdminNotification(m);
            log.info("sendWebsiteNotification submitted");
        } catch (Exception e) {
            log.info("<--- onFileUpload Exception  ---> \n ",e);
        }
    }


    // CandidateAppliedEvent
    @EventListener
    public void onCandidateAppliedEvent(CandidateAppliedEvent candidateAppliedEvent) {
        log.info("Executing onCandidateAppliedEvent");
        sendVacancyAcknowledgementMessage(candidateAppliedEvent.getApplication());
        sendAdminContactMessage(candidateAppliedEvent.getAdminContactMessage());
    }

    public void sendVacancyAcknowledgementMessage(Application application) {
        log.info("sendVacancyAcknowledgementMessage application \n {}", application.printApplication());
        try (ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()) {
            //  ExecutorService executor = Executors.newSingleThreadExecutor();
            executor.submit(() -> {
                communicationService.sendApplicationAcknowledgement(application);
            });
           // communicationService.sendApplicationAcknowledgement(application);

            log.info("sendVacancyAcknowledgementMessage Message Request submitted");
        } catch (Exception e) {
            log.info("<--- sendVacancyAcknowledgementMessage Exception ---> \n", e);

        }
    }


    public void sendAdminContactMessage(AdminContactMessage adminContactMessage) {
        log.info("sendAdminContactMessage application \n {}", adminContactMessage.printAdminContactMessage());
        try (ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()) {
            //  ExecutorService executor = Executors.newSingleThreadExecutor();
            log.info("About to submit request");
            executor.submit(() -> {
                communicationService.sendAdminNotification(adminContactMessage);
            });
           // communicationService.sendAdminNotification(adminContactMessage);
            log.info("sendAdminContactMessage Message Request submitted");
        } catch (Exception e) {
            log.info("<--- sendAdminContactMessage Exception ---> \n", e);
        }
    }


}
