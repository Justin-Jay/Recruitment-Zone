package za.co.recruitmentzone.candidate.events;

import org.springframework.context.ApplicationEvent;
import za.co.recruitmentzone.application.entity.Application;

import java.time.Clock;

// Define a custom event for candidate application
public class CandidateAppliedEvent extends ApplicationEvent {

    private Integer applicationID;

    private Application application;

    public CandidateAppliedEvent(Object source, Clock clock) {
        super(source, clock);
    }

    public CandidateAppliedEvent(Object source, Integer applicationID) {
        super(source);
        this.applicationID = applicationID;
    }

    // Getters for candidate and resumeFilePath

    public Integer getApplicationID() {
        return applicationID;
    }

    public void setApplicationID(Integer applicationID) {
        this.applicationID = applicationID;
    }

    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }
}


