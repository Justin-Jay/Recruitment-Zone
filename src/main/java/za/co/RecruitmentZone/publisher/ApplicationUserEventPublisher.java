package za.co.RecruitmentZone.publisher;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import za.co.RecruitmentZone.entity.ApplicationUserDTO;
import za.co.RecruitmentZone.events.ApplicationUser.ApplicationUserCreateEvent;

import java.time.Clock;

@Component
public class ApplicationUserEventPublisher {

    private final ApplicationEventPublisher eventPublisher;
    private final Logger log = LoggerFactory.getLogger(ApplicationUserEventPublisher.class);

    public ApplicationUserEventPublisher(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    public boolean publishApplicationUserCreateEvent(ApplicationUserDTO userDTO){
        try {
            Clock baseClock = Clock.systemDefaultZone();
            ApplicationUserCreateEvent event = new ApplicationUserCreateEvent(userDTO,baseClock);
            eventPublisher.publishEvent(event);
            return true;
        } catch (Exception e) {
            log.info("");
            return false;
        }
    }


}
