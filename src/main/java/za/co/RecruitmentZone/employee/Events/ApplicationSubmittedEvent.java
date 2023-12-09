/*
package za.co.RecruitmentZone.events.EventStore.ApplicationUser;

import org.springframework.context.ApplicationEvent;
import za.co.RecruitmentZone.application.entity.Application;
import za.co.RecruitmentZone.candidate.entity.Candidate;
import za.co.RecruitmentZone.entity.domain.CandidateApplicationDTO;

import java.time.Clock;

public class ApplicationSubmittedEvent extends ApplicationEvent {

    private Application application;
    private Candidate candidate;

    public ApplicationSubmittedEvent(CandidateApplicationDTO dto) {
        super(dto);
        this.application=dto.getApplication();
        this.candidate=dto.getCandidate();
    }

    public Candidate getCandidate() {
        return candidate;
    }

    public void setCandidate(Candidate candidate) {
        this.candidate = candidate;
    }

    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }
}
*/
