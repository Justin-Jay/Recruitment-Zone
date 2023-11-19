package za.co.RecruitmentZone.entity.domain;
import jakarta.persistence.*;
import za.co.RecruitmentZone.entity.Enums.ApplicationStatus;
import za.co.RecruitmentZone.entity.Enums.EducationLevel;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name ="candidate")
public class Candidate {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long candidateID;
    private String first_name;
    public Candidate() {
        // received
    }

    public Candidate(String first_name) {
        this.first_name = first_name;
    }

    public Long getCandidateID() {
        return candidateID;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    @Override
    public String toString() {
        return "Candidate{" +
                "first_name='" + first_name + '\'' +
                '}';
    }
}
