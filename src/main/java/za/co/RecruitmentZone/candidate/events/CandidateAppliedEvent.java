package za.co.RecruitmentZone.candidate.events;

import org.springframework.context.ApplicationEvent;
import za.co.RecruitmentZone.entity.Candidate;

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


