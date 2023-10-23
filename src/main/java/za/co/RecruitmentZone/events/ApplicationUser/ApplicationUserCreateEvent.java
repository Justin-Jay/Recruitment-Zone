package za.co.RecruitmentZone.events.ApplicationUser;

import org.springframework.context.ApplicationEvent;
import za.co.RecruitmentZone.entity.UserDTO;

import java.time.Clock;

public class ApplicationUserCreateEvent extends ApplicationEvent {
    private Integer userID;
    private UserDTO applicationUserDTO;

    public ApplicationUserCreateEvent(Object source, Clock clock) {
        super(source, clock);
    }

    public ApplicationUserCreateEvent(Object source, Integer userID) {
        super(source);
        this.userID = userID;
    }

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public UserDTO getApplicationUserDTO() {
        return applicationUserDTO;
    }

    public void setApplicationUserDTO(UserDTO applicationUserDTO) {
        this.applicationUserDTO = applicationUserDTO;
    }
}
