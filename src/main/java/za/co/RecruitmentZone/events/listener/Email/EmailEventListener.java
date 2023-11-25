package za.co.RecruitmentZone.events.listener.Email;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import za.co.RecruitmentZone.entity.domain.ContactMessage;
import za.co.RecruitmentZone.events.EventStore.communication.WebsiteQueryReceivedEvent;
import za.co.RecruitmentZone.repository.VacancyRepository;
import za.co.RecruitmentZone.service.domainServices.CommunicationService;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Transactional
@Component
public class EmailEventListener {
    private final VacancyRepository vacancyRepository;

    private final CommunicationService communicationService;
    Logger log = LoggerFactory.getLogger(EmailEventListener.class);

    public EmailEventListener(VacancyRepository vacancyRepository, CommunicationService communicationService) {
        this.vacancyRepository = vacancyRepository;
        this.communicationService = communicationService;
    }

    @EventListener
    public void onWebsiteQueryReceived(WebsiteQueryReceivedEvent event) {
        log.info("Executing onWebsiteQueryReceived");
        ContactMessage m = event.getMessage();

        try {
            ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor();

            executor.submit(() -> {
                // Perform repo IO operation
                communicationService.sendSimpleEmail(m);
            });
        } catch (Exception e) {
            log.info("Failed to send Email Query");
        }
        log.info("DONE Executing onWebsiteQueryReceived");
    }

    // AMEND VACANCY


}
