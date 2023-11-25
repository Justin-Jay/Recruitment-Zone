/*
package za.co.RecruitmentZone.events.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import za.co.RecruitmentZone.entity.UserDTO;
import za.co.RecruitmentZone.events.EventStore.ApplicationUser.ApplicationUserCreateEvent;
import za.co.RecruitmentZone.repository.ApplicationUserRepository;


@Transactional
@Component
public class UserEventListener {

    private final ApplicationUserRepository applicationUserRepository;

    Logger log = LoggerFactory.getLogger(UserEventListener.class);

    public UserEventListener(ApplicationUserRepository applicationUserRepository) {
        this.applicationUserRepository = applicationUserRepository;
    }

    @EventListener
    public void onApplicationUserCreateEvent(ApplicationUserCreateEvent event) {
        // Perform actions when a vacancy expires
        try {
            UserDTO newUser = event.getApplicationUserDTO();
            String emailAddress = newUser.getEmail_address();
            sendSignUpLink(emailAddress);
        } catch (Exception e) {
            log.error("FAILED {}", e.getMessage());
        }
    }

    public boolean sendSignUpLink(String emailAddress){
//        sendEmail(emailAddress);
        return false;
    }

    // send email ?

}
*/
