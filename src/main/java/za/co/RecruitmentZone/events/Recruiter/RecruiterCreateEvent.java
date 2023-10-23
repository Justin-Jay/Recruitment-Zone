package za.co.RecruitmentZone.events.Recruiter;

import org.springframework.context.ApplicationEvent;
import za.co.RecruitmentZone.entity.ApplicationUser;

import java.time.Clock;

public class RecruiterCreateEvent extends ApplicationEvent {
    private Integer recruiterID;

    private ApplicationUser user;


    public RecruiterCreateEvent(Object source, Clock clock) {
        super(source, clock);
    }

    public RecruiterCreateEvent(Object source, Integer recruiterID) {
        super(source);
        this.recruiterID = recruiterID;
    }

    public Integer getRecruiterID() {
        return recruiterID;
    }

    public void setRecruiterID(Integer recruiterID) {
        this.recruiterID = recruiterID;
    }

    public ApplicationUser getUser() {
        return user;
    }

    public void setUser(ApplicationUser user) {
        this.user = user;
    }
}

