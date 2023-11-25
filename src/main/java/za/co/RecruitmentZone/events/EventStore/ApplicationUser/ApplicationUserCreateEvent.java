package za.co.RecruitmentZone.events.EventStore.ApplicationUser;

import org.springframework.context.ApplicationEvent;

import java.time.Clock;

public class ApplicationUserCreateEvent extends ApplicationEvent {
    private Integer userID;
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

}
