package za.co.RecruitmentZone.application.entity;

import jakarta.persistence.*;
import za.co.RecruitmentZone.util.Enums.ApplicationStatus;
import za.co.RecruitmentZone.candidate.entity.Candidate;
import za.co.RecruitmentZone.vacancy.entity.Vacancy;

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
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "candidateID")
    private Candidate candidate;

    @ManyToOne(cascade = {
            CascadeType.PERSIST,CascadeType.MERGE,CascadeType.DETACH,CascadeType.REFRESH
    })
    @JoinColumn(name="vacancyID")
    private Vacancy vacancy;

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

    public Candidate getCandidate() {
        return candidate;
    }

    public void setCandidate(Candidate candidate) {
        this.candidate = candidate;
    }

    public Vacancy getVacancy() {
        return vacancy;
    }

    public void setVacancy(Vacancy vacancy) {
        this.vacancy = vacancy;
    }

    @Override
    public String toString() {
        return "Application{" +
                "applicationID=" + applicationID +
                ", date_received='" + date_received + '\'' +
                ", submission_date='" + submission_date + '\'' +
                ", status=" + status +
                ", candidate=" + candidate +
                ", vacancy=" + vacancy +
                '}';
    }
}


