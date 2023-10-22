package za.co.RecruitmentZone.recruitmentManager.service;
import za.co.RecruitmentZone.entity.Recruiter;
import za.co.RecruitmentZone.util.Email;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
@Entity
public class RecruitmentManager {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String FirstName;
    private String LastName;
    private String password;
    private  String role;
    private String UserName;
    private Email email;

    // Relationship: A recruitment manager can add/remove multiple recruiters
    @OneToMany(mappedBy = "recruitmentManager")
    private List<Recruiter> recruiters = new ArrayList<>();

    // Constructors, getters, and setters

    public RecruitmentManager() {
    }

    public RecruitmentManager(Long id, String firstName, String lastName, String password, String role, String userName, Email email, List<Recruiter> recruiters) {
        this.id = id;
        FirstName = firstName;
        LastName = lastName;
        this.password = password;
        this.role = role;
        UserName = userName;
        this.email = email;
        this.recruiters = recruiters;
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

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public Email getEmail() {
        return email;
    }

    public void setEmail(Email email) {
        this.email = email;
    }

    public List<Recruiter> getRecruiters() {
        return recruiters;
    }

    public void setRecruiters(List<Recruiter> recruiters) {
        this.recruiters = recruiters;
    }
}
