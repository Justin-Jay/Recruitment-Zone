package za.co.RecruitmentZone.entity.domain;

import jakarta.persistence.*;
import za.co.RecruitmentZone.entity.Enums.ApplicationStatus;

import java.util.List;

@Entity
@Table(name = "application")
public class Application {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "applicationID")
    private Long applicationID;
    private String date_received;

    private String submission_date;

    @Enumerated(EnumType.STRING)
    private ApplicationStatus status;
 /*   @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "")*/
    private Long candidateID;


  /*  @ManyToOne(cascade = {
            CascadeType.PERSIST,CascadeType.MERGE,CascadeType.DETACH,CascadeType.REFRESH
    })*/
    private Long vacancyID;

    public Application() {
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

    public ApplicationStatus getStatus() {
        return status;
    }

    public void setStatus(ApplicationStatus status) {
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

    @Override
    public String toString() {
        return "Application{" +
                "applicationID=" + applicationID +
                ", date_received='" + date_received + '\'' +
                ", submission_date='" + submission_date + '\'' +
                ", status=" + status +
                ", candidateID=" + candidateID +
                ", vacancyID=" + vacancyID +
                '}';
    }
}


