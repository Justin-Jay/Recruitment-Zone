package za.co.RecruitmentZone.Application;
import za.co.RecruitmentZone.Candidate.Candidate;
import za.co.RecruitmentZone.Vacancy.Vacancy;
import za.co.RecruitmentZone.util.Email;

import javax.persistence.*;

@Entity
@Table(name="application")
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Email EmailAddress;
    private String FirstName;
    private String LastName;
    private String IDNumber;
    private String PhoneNumber;
    private String Province;
    private String cvFilePath; // Store the path to the CV file

    // Other application fields (e.g., name, email, resume, etc.)

    @ManyToOne
    @JoinColumn(name = "candidate_id")
    private Candidate candidate;  // Add this field for the candidate relationship

    @ManyToOne
    @JoinColumn(name = "vacancy_id")
    private Vacancy appliedForVacancy;

    // Constructors, getters, setters, and other methods

    public Application() {
    }

    public Application(Long id, Email emailAddress, String firstName, String lastName, String IDNumber, String phoneNumber, String province, String cvFilePath, Candidate candidate, Vacancy appliedForVacancy) {
        this.id = id;
        EmailAddress = emailAddress;
        FirstName = firstName;
        LastName = lastName;
        this.IDNumber = IDNumber;
        PhoneNumber = phoneNumber;
        Province = province;
        this.cvFilePath = cvFilePath;
        this.candidate = candidate;
        this.appliedForVacancy = appliedForVacancy;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    // Other getters and setters for application fields

    public Email getEmailAddress() {
        return EmailAddress;
    }

    public void setEmailAddress(Email emailAddress) {
        EmailAddress = emailAddress;
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

    public Candidate getCandidate() {
        return candidate;
    }

    public void setCandidate(Candidate candidate) {
        this.candidate = candidate;
    }

    public Vacancy getAppliedForVacancy() {
        return appliedForVacancy;
    }

    public void setAppliedForVacancy(Vacancy appliedForVacancy) {
        this.appliedForVacancy = appliedForVacancy;
    }
}


