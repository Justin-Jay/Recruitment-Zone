package za.co.recruitmentzone.communication.events.publisher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import za.co.recruitmentzone.communication.entity.AdminContactMessage;
import za.co.recruitmentzone.communication.entity.ContactMessage;
import za.co.recruitmentzone.communication.events.AdminMessageEvent;
import za.co.recruitmentzone.communication.events.WebsiteMessageEvent;

@Component
public class EmailEventPublisher {
    private final ApplicationEventPublisher eventPublisher;
    private final Logger log = LoggerFactory.getLogger(EmailEventPublisher.class);

    public EmailEventPublisher(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }


    public boolean publishWebsiteQueryReceivedEvent(ContactMessage message) {
        WebsiteMessageEvent wc = new WebsiteMessageEvent(message);
        log.info("Executing publishWebsiteQueryReceivedEvent");
        try {
            eventPublisher.publishEvent(wc);
            log.info("EVENT publishWebsiteQueryReceivedEvent POSTED");
            return true;
        } catch (Exception e) {
            log.info("Unable to post event");
            return false;
        }
    }

    public boolean publishFileUpload(AdminContactMessage message) {
        AdminMessageEvent wc = new AdminMessageEvent(message);
        log.info("Executing publishFileUpload Admin Message \n {} ",wc.getMessage().printAdminContactMessage());
        try {
            eventPublisher.publishEvent(wc);
            log.info("EVENT publishFileUpload POSTED");
            return true;
        } catch (Exception e) {
            log.info("Unable to post event {}", e.getMessage());
            return false;
        }

    }




}
