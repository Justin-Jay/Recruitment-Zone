package za.co.RecruitmentZone.entity;

import jakarta.persistence.*;

@Entity
@Table(name="application")
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer applicationID;
    private String EmailAddress;
    private String FirstName;
    private String LastName;
    private String IDNumber;
    private String PhoneNumber;
    private String Province;
    private String cvFilePath; // Store the path to the CV file
    @Column(name= "candidateID")
    private Integer candidateID;  // Add this field for the candidate relationship
    @Column(name= "vacancyID")
    private Integer applicationVacancyID;
    // Other application fields (e.g., name, email, resume, etc.)


    // Constructors, getters, setters, and other methods

    public Application() {
    }

    public Application(String emailAddress, String firstName, String lastName, String IDNumber, String phoneNumber, String province, String cvFilePath, Integer candidateID, Integer applicationVacancyID) {
        EmailAddress = emailAddress;
        FirstName = firstName;
        LastName = lastName;
        this.IDNumber = IDNumber;
        PhoneNumber = phoneNumber;
        Province = province;
        this.cvFilePath = cvFilePath;
        this.candidateID = candidateID;
        this.applicationVacancyID = applicationVacancyID;
    }


// Other getters and setters for application fields


    public Integer getApplicationID() {
        return applicationID;
    }

    public String getEmailAddress() {
        return EmailAddress;
    }

    public void setEmailAddress(String emailAddress) {
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

    public Integer getCandidateID() {
        return candidateID;
    }

    public void setCandidateID(Integer candidateID) {
        this.candidateID = candidateID;
    }

    public Integer getApplicationVacancyID() {
        return applicationVacancyID;
    }

    public void setApplicationVacancyID(Integer applicationVacancyID) {
        this.applicationVacancyID = applicationVacancyID;
    }
}


