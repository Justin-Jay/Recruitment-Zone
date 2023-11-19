/*
package za.co.RecruitmentZone.entity.domain.backupdomains;

import jakarta.persistence.*;

@Entity
@Table(name = "application")
public class Application {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long applicationID;
    @ManyToOne
    @JoinColumn(name = "candidateID")
    private Candidate candidate;
    public Application() {
    }

    public Application(Long applicationID, Candidate candidate) {
        this.applicationID = applicationID;
        this.candidate = candidate;
    }

    public Long getApplicationID() {
        return applicationID;
    }

    public void setApplicationID(Long applicationID) {
        this.applicationID = applicationID;
    }

    public Candidate getCandidate() {
        return candidate;
    }

    public void setCandidate(Candidate candidate) {
        this.candidate = candidate;
    }

    @Override
    public String toString() {
        return "Application{" +
                "applicationID=" + applicationID +
                ", candidate=" + candidate +
                '}';
    }
}


*/
