package za.co.RecruitmentZone.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Recruiter {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer recruiterID;
    private String first_name;
    private String last_name;
    private String password;
    private String role;
    private String email_address;

    // Replace object references with IDs
    @ElementCollection
    @CollectionTable(name = "recruiter_vacancies", joinColumns = @JoinColumn(name = "recruiterID"))
    @Column(name = "vacancyID")
    private List<Integer> vacancyIDs = new ArrayList<>();

    public Recruiter() {
    }

    public Recruiter(String first_name, String last_name, String password, String role, String email_address, List<Integer> vacancyIDs) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.password = password;
        this.role = role;
        this.email_address = email_address;
        this.vacancyIDs = vacancyIDs;
    }

    // Other getters and setters

    // Getters and setters for vacancyIds

    public List<Integer> getVacancyIDs() {
        return vacancyIDs;
    }

    public void setVacancyIDs(List<Integer> vacancyIDs) {
        this.vacancyIDs = vacancyIDs;
    }

    public Integer getRecruiterID() {
        return recruiterID;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getEmail_address() {
        return email_address;
    }

    public void setEmail_address(String email_address) {
        this.email_address = email_address;
    }
}
