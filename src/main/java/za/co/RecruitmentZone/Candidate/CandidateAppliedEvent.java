package za.co.RecruitmentZone.Candidate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import za.co.RecruitmentZone.Candidate.Candidate;
import za.co.RecruitmentZone.Candidate.CandidateService;

// Define a custom event for candidate application
class CandidateAppliedEvent extends ApplicationEvent {
    private Candidate candidate;
    private String resumeFilePath;

    public CandidateAppliedEvent(Object source, Candidate candidate, String resumeFilePath) {
        super(source);
        this.candidate = candidate;
        this.resumeFilePath = resumeFilePath;
    }

    // Getters for candidate and resumeFilePath


    public Candidate getCandidate() {
        return candidate;
    }

    public void setCandidate(Candidate candidate) {
        this.candidate = candidate;
    }

    public String getResumeFilePath() {
        return resumeFilePath;
    }

    public void setResumeFilePath(String resumeFilePath) {
        this.resumeFilePath = resumeFilePath;
    }
}


