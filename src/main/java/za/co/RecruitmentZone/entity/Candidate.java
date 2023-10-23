package za.co.RecruitmentZone.entity;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Candidate {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer candidateID;
    private String email_address;
    private String first_name;
    private String last_name;
    private String id_number;
    private String phone_number;
    private String province;
    private String cvFilePath; // Store the path to the CV file

    // Replace object references with IDs
    @ElementCollection
    @CollectionTable(name = "applications", joinColumns = @JoinColumn(name = "candidateID"))
    @Column(name = "vacancy_id")
    private List<Integer> candidateVacancyIDs = new ArrayList<>();

    // Constructors, getters, and setters

    public Candidate() {
    }

    public Candidate(String email_address, String firstname, String lastname, String id_number, String phonenumber, String province, String cvFilePath, List<Integer> candidateVacancyIDs) {
        this.email_address = email_address;
        first_name = firstname;
        last_name = lastname;
        this.id_number = id_number;
        phone_number = phonenumber;
        this.province = province;
        this.cvFilePath = cvFilePath;
        this.candidateVacancyIDs = candidateVacancyIDs;
    }

    // Other getters and setters

    // Getters and setters for appliedVacancyIds

    public List<Integer> getcandidateVacancyIDs() {
        return candidateVacancyIDs;
    }

    public void setAppliedVacancyIds(List<Integer> candidateVacancyIDs) {
        this.candidateVacancyIDs = candidateVacancyIDs;
    }

    public String getEmail_address() {
        return email_address;
    }

    public void setEmail_address(String email_address) {
        this.email_address = email_address;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getId_number() {
        return id_number;
    }

    public void setId_number(String id_number) {
        this.id_number = id_number;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }


    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCvFilePath() {
        return cvFilePath;
    }

    public void setCvFilePath(String cvFilePath) {
        this.cvFilePath = cvFilePath;
    }

    public List<Integer> getCandidateVacancyIDs() {
        return candidateVacancyIDs;
    }

    public void setCandidateVacancyIDs(List<Integer> candidateVacancyIDs) {
        this.candidateVacancyIDs = candidateVacancyIDs;
    }
}
