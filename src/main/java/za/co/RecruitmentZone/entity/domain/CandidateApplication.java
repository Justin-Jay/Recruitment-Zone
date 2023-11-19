package za.co.RecruitmentZone.entity.domain;

import jakarta.persistence.*;

@Entity
@Table(name="candidate_application")
public class CandidateApplication {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long applicationID;
    private Long candidateID;

    public CandidateApplication(Long applicationID, Long candidateID) {
        this.applicationID = applicationID;
        this.candidateID = candidateID;
    }

    public CandidateApplication() {

    }

    public Long getId() {
        return id;
    }


    public Long getApplicationID() {
        return applicationID;
    }

    public void setApplicationID(Long applicationID) {
        this.applicationID = applicationID;
    }

    public Long getCandidateID() {
        return candidateID;
    }

    public void setCandidateID(Long candidateID) {
        this.candidateID = candidateID;
    }

    @Override
    public String toString() {
        return "CandidateApplication{" +
                "applicationID=" + applicationID +
                ", candidateID=" + candidateID +
                '}';
    }
}
