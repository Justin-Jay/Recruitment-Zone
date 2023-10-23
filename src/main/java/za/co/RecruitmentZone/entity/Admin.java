package za.co.RecruitmentZone.entity;

import jakarta.persistence.*;
import za.co.RecruitmentZone.util.Email;

@Entity
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String FirstName;
    private String LastName;
    private String password;
    private  String role;
    private String UserName;
    private Email email;

    // Relationship: A recruitment manager can add/remove multiple recruiters
    // Constructors, getters, and setters

    public Admin() {
    }

    public Admin(Long id, String firstName, String lastName, String password, String role, String userName, Email email) {
        this.id = id;
        FirstName = firstName;
        LastName = lastName;
        this.password = password;
        this.role = role;
        UserName = userName;
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

}
