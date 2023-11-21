package za.co.RecruitmentZone.entity.domain;

import jakarta.persistence.*;

@Entity
@Table(name="candidate_application")
public class CandidateApplication {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @Column(name = "applicationID")
    private Long applicationID;
    @Column(name = "candidateID")
    private Long candidateID;

    public CandidateApplication() {
    }

    public CandidateApplication(Long applicationID, Long candidateID) {
        this.applicationID = applicationID;
        this.candidateID = candidateID;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
                "id=" + id +
                ", applicationID=" + applicationID +
                ", candidateID=" + candidateID +
                '}';
    }
}
