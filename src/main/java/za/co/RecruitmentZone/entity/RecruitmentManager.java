package za.co.RecruitmentZone.entity;

import jakarta.persistence.*;

@Entity
public class RecruitmentManager {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer recruitmentManagerID;
    private String firstName;
    private String lastName;
    private String password;
    private String role;  // This could be 'RECRUITMENT_MANAGER'
    private String userName;
    private String email;

    public RecruitmentManager() {
    }

    public RecruitmentManager(String firstName, String lastName, String password, String role, String userName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.role = role;
        this.userName = userName;
        this.email = email;
    }

    // Getters and setters...


    public Integer getRecruitmentManagerID() {
        return recruitmentManagerID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

