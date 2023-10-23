package za.co.RecruitmentZone.publisher;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import za.co.RecruitmentZone.entity.UserDTO;
import za.co.RecruitmentZone.events.ApplicationUser.ApplicationUserCreateEvent;
import java.time.Clock;

@Component
public class ApplicationUserEventPublisher {

    private final ApplicationEventPublisher eventPublisher;
    private final Logger log = LoggerFactory.getLogger(ApplicationUserEventPublisher.class);

    public ApplicationUserEventPublisher(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    @Bean
    public String publishApplicationUserCreateEvent(UserDTO newUser) {
        try {
            Clock baseClock = Clock.systemDefaultZone();
            ApplicationUserCreateEvent applicationUserCreateEvent = new ApplicationUserCreateEvent(newUser,baseClock);
            eventPublisher.publishEvent(applicationUserCreateEvent);
            return "SUCCESSFUL";
        } catch (Exception e) {
            log.info(e.getMessage());
        }

        return "FAILED";
    }


}
