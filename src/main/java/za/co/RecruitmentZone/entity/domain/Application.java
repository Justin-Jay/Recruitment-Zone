package za.co.RecruitmentZone.entity.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "application")
public class Application {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "applicationID")
    private Long applicationID;

    private String date_received;

    private String submission_date;

    private Integer status;

    private Long  candidateID;

    private Long vacancyID;

    public Application() {
    }

    public Application(String date_received, String submission_date, Integer status, Long candidateID, Long vacancyID) {
        this.date_received = date_received;
        this.submission_date = submission_date;
        this.status = status;
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

    public String getSubmission_date() {
        return submission_date;
    }

    public void setSubmission_date(String submission_date) {
        this.submission_date = submission_date;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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
}


