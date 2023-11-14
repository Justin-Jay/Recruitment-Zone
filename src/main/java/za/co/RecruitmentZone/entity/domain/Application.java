package za.co.RecruitmentZone.entity.domain;

import jakarta.persistence.*;
import za.co.RecruitmentZone.entity.Enums.ApplicationStatus;

@Entity
@Table(name = "application")
public class Application {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long applicationID;
    private String submissionDate;
    @Enumerated(EnumType.STRING)
    private ApplicationStatus status;
    @ManyToOne
    @JoinColumn(name = "candidateID")
    private Candidate candidate;
    @Column(name = "jobID")
    private Long jobID;
    public Application() {
    }

    public Application(String submissionDate, ApplicationStatus status, Long jobID) {
        this.submissionDate = submissionDate;
        this.status = status;
        this.jobID = jobID;
    }

    public Long getApplicationID() {
        return applicationID;
    }

    public String getSubmissionDate() {
        return submissionDate;
    }

    public void setSubmissionDate(String submissionDate) {
        this.submissionDate = submissionDate;
    }

    public ApplicationStatus getStatus() {
        return status;
    }

    public void setStatus(ApplicationStatus status) {
        this.status = status;
    }

    public Candidate getCandidate() {
        return candidate;
    }

    public void setCandidate(Candidate candidate) {
        this.candidate = candidate;
    }

    public Long getJobID() {
        return jobID;
    }

    public void setJobID(Long jobID) {
        this.jobID = jobID;
    }

    @Override
    public String toString() {
        return "Application{" +
                "applicationID=" + applicationID +
                ", submissionDate='" + submissionDate + '\'' +
                ", status=" + status +
                ", jobID=" + jobID +
                '}';
    }
}


