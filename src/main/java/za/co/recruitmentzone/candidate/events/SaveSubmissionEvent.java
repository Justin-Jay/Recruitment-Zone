package za.co.recruitmentzone.candidate.events;

import org.springframework.context.ApplicationEvent;
import za.co.recruitmentzone.application.entity.Application;
import za.co.recruitmentzone.candidate.entity.Candidate;
import za.co.recruitmentzone.vacancy.entity.Vacancy;

// Define a custom event for candidate application
public class SaveSubmissionEvent extends ApplicationEvent {

    Vacancy vacancy;
    private Candidate candidate;

    private Application application;


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


}


