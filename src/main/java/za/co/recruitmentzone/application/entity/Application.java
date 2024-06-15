package za.co.recruitmentzone.application.entity;

import jakarta.persistence.*;
import za.co.recruitmentzone.util.enums.ApplicationStatus;
import za.co.recruitmentzone.candidate.entity.Candidate;
import za.co.recruitmentzone.vacancy.entity.Vacancy;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "APPLICATION")
public class Application implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "applicationID")
    private Long applicationID;
    @Column(name="created")
    private LocalDateTime created;
    @Column(name="date_received")
    private LocalDateTime date_received;
    @Enumerated(EnumType.STRING)
    private ApplicationStatus status;
    @Column(name = "submission_date")
    private LocalDateTime submission_date;

    @ManyToOne( )
    @JoinColumn(name = "candidateID")
    private Candidate candidate;

    @ManyToOne( )
    @JoinColumn(name="vacancyID")
    private Vacancy vacancy;

    public Application() {
        this.created = LocalDateTime.now();
        this.status=ApplicationStatus.PENDING;
    }

    public LocalDateTime getCreated() {
        return created;
    }


    public void setDate_received(LocalDateTime date_received) {
        this.date_received = date_received;
    }

    public LocalDateTime getSubmission_date() {
        return submission_date;
    }

    public void setSubmission_date(LocalDateTime submission_date) {
        this.submission_date = submission_date;
    }

    public Long getApplicationID() {
        return applicationID;
    }

    public void setApplicationID(Long applicationID) {
        this.applicationID = applicationID;
    }

    public LocalDateTime getDate_received() {
        return date_received;
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

    public String printApplication() {
        return "Application{" +
                "applicationID=" + applicationID +
                ", date_received='" + date_received + '\'' +
                ", status=" + status +
                ", candidate=" + candidate.getCandidateID() +
                ", vacancy=" + vacancy.getJobTitle() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Application that = (Application) o;
        return Objects.equals(applicationID, that.applicationID) && Objects.equals(date_received, that.date_received) && status == that.status && Objects.equals(candidate, that.candidate) && Objects.equals(vacancy, that.vacancy);
    }

    @Override
    public int hashCode() {
        return Objects.hash(applicationID, date_received, status, candidate, vacancy);
    }
}


