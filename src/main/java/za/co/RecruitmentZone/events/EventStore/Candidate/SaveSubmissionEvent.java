package za.co.RecruitmentZone.events.EventStore.Candidate;

import org.springframework.context.ApplicationEvent;
import za.co.RecruitmentZone.entity.domain.Application;
import za.co.RecruitmentZone.entity.domain.Candidate;
import za.co.RecruitmentZone.entity.domain.CandidateApplication;

import java.time.Clock;

// Define a custom event for candidate application
public class SaveSubmissionEvent extends ApplicationEvent {

    private Candidate candidate;

    private Application application;

    private CandidateApplication candidateApplication;

    Long vacancyID;

    public SaveSubmissionEvent(Candidate candidate,Long vacancyID) {
        super(candidate);
        this.vacancyID=vacancyID;
    }

    // Getters for candidate and resumeFilePath


    public Long getVacancyID() {
        return vacancyID;
    }

    public void setVacancyID(Long vacancyID) {
        this.vacancyID = vacancyID;
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

    public CandidateApplication getCandidateApplication() {
        return candidateApplication;
    }

    public void setCandidateApplication(CandidateApplication candidateApplication) {
        this.candidateApplication = candidateApplication;
    }
}


