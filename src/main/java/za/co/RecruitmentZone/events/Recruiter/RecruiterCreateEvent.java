package za.co.RecruitmentZone.events.Recruiter;

import org.springframework.context.ApplicationEvent;
import za.co.RecruitmentZone.entity.ApplicationUser;
import za.co.RecruitmentZone.entity.Recruiter;

import java.time.Clock;

public class RecruiterCreateEvent extends ApplicationEvent {
    private Integer recruiterID;

    private Recruiter recruiter;


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

    public Recruiter getRecruiter() {
        return recruiter;
    }

    public void setRecruiter(Recruiter recruiter) {
        this.recruiter = recruiter;
    }
}

