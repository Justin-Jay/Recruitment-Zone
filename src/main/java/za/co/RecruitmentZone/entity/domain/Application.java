package za.co.RecruitmentZone.entity.domain;

import jakarta.persistence.*;
import za.co.RecruitmentZone.entity.Enums.ApplicationStatus;

@Entity
@Table(name = "application")
public class Application {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "applicationID")
    private Long applicationID;
    @Column(name = "date_received")
    private String date_received;
    @Column(name = "submissionDate")
    private String submissionDate;
    @Column(name = "Status")
    private String Status;
    @Column(name = "candidateID")
    private Long  candidateID;
    @Column(name = "vacancyID")
    private Long vacancyID;

    public Application() {
    }

    public Application(String date_received, String submissionDate, String status, Long candidateID, Long vacancyID) {
        this.date_received = date_received;
        this.submissionDate = submissionDate;
        Status = status;
        this.candidateID = candidateID;
        this.vacancyID = vacancyID;
    }

    public Long getApplicationID() {
        return applicationID;
    }

    public void setApplicationID(Long applicationID) {
        this.applicationID = applicationID;
    }

    public String getDate_received() {
        return date_received;
    }

    public void setDate_received(String date_received) {
        this.date_received = date_received;
    }

    public String getSubmissionDate() {
        return submissionDate;
    }

    public void setSubmissionDate(String submissionDate) {
        this.submissionDate = submissionDate;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public Long getCandidateID() {
        return candidateID;
    }

    public void setCandidateID(Long candidateID) {
        this.candidateID = candidateID;
    }

    public Long getVacancyID() {
        return vacancyID;
    }

    public void setVacancyID(Long vacancyID) {
        this.vacancyID = vacancyID;
    }

    @Override
    public String toString() {
        return "Application{" +
                "applicationID=" + applicationID +
                ", date_received='" + date_received + '\'' +
                ", submissionDate='" + submissionDate + '\'' +
                ", Status='" + Status + '\'' +
                ", candidateID=" + candidateID +
                ", vacancyID=" + vacancyID +
                '}';
    }
}


