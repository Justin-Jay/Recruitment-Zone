package za.co.RecruitmentZone.Entity;

import jakarta.persistence.*;
import za.co.RecruitmentZone.util.Email;
import za.co.RecruitmentZone.util.PhoneNumber;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Candidate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer candidateID;

    String name;

    String last_name;

    String storedCVLocation;

    String address;
    String phoneNumber;

    String email_address;

    @ManyToMany
    @JoinTable(
            name = "applications",
            joinColumns = @JoinColumn(name = "candidateID"),
            inverseJoinColumns = @JoinColumn(name = "vacancyID")
    )

    private List<Candidate> appliedVacancies = new ArrayList<>();

}
