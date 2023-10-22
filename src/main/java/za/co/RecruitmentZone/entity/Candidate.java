package za.co.RecruitmentZone.entity;
import za.co.RecruitmentZone.vacancy.util.Vacancy;
import za.co.RecruitmentZone.util.Email;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
@Entity
public class Candidate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Email email;
    private String FirstName;
    private String LastName;
    private String IDNumber;
    private String PhoneNumber;
    private String Province;
    private String cvFilePath; // Store the path to the CV file

    // Relationship: Many candidates can apply for many vacancies
    @ManyToMany
    @JoinTable(
            name = "applications",
            joinColumns = @JoinColumn(name = "candidate_id"),
            inverseJoinColumns = @JoinColumn(name = "vacancy_id")
    )
    private List<Vacancy> appliedVacancies = new ArrayList<>();

    // Constructors, getters, and setters


    public Candidate() {
    }

    public Candidate(Long id, Email email, String firstName, String lastName, String IDNumber, String phoneNumber, String province, String cvFilePath, List<Vacancy> appliedVacancies) {
        this.id = id;
        this.email = email;
        FirstName = firstName;
        LastName = lastName;
        this.IDNumber = IDNumber;
        PhoneNumber = phoneNumber;
        Province = province;
        this.cvFilePath = cvFilePath;
        this.appliedVacancies = appliedVacancies;
    }

    public Email getEmail() {
        return email;
    }

    public void setEmail(Email email) {
        this.email = email;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getIDNumber() {
        return IDNumber;
    }

    public void setIDNumber(String IDNumber) {
        this.IDNumber = IDNumber;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public String getProvince() {
        return Province;
    }

    public void setProvince(String province) {
        Province = province;
    }

    public String getCvFilePath() {
        return cvFilePath;
    }

    public void setCvFilePath(String cvFilePath) {
        this.cvFilePath = cvFilePath;
    }

    public List<Vacancy> getAppliedVacancies() {
        return appliedVacancies;
    }

    public void setAppliedVacancies(List<Vacancy> appliedVacancies) {
        this.appliedVacancies = appliedVacancies;
    }
}
