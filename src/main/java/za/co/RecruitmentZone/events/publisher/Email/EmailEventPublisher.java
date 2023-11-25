package za.co.RecruitmentZone.events.publisher.Email;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import za.co.RecruitmentZone.entity.domain.ContactMessage;
import za.co.RecruitmentZone.entity.domain.Vacancy;
import za.co.RecruitmentZone.events.EventStore.Vacancy.VacancyCreateEvent;
import za.co.RecruitmentZone.events.EventStore.communication.WebsiteQueryReceivedEvent;

import java.time.Clock;

@Component
public class EmailEventPublisher {
    private final ApplicationEventPublisher eventPublisher;
    private final Logger log = LoggerFactory.getLogger(EmailEventPublisher.class);

    public EmailEventPublisher(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }


    public boolean publishWebsiteQueryReceivedEvent(ContactMessage message) {
        WebsiteQueryReceivedEvent wc = new WebsiteQueryReceivedEvent(message);
        log.info("Executing publishWebsiteQueryReceivedEvent");
        try {
            eventPublisher.publishEvent(wc);
            log.info("EVENT  publishWebsiteQueryReceivedEvent POSTED");
            return true;
        } catch (Exception e) {
            log.info("Unable to post event");
            return false;
        }

    }









}
