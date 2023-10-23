package za.co.RecruitmentZone.entity;

import jakarta.persistence.*;
import za.co.RecruitmentZone.util.Email;

@Entity
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String first_name;
    private String last_name;
    private String password;
    private  String role;
    private String email_address;

    // Relationship: A recruitment manager can add/remove multiple recruiters
    // Constructors, getters, and setters

    public Admin() {
    }

    public Admin(String first_name, String last_name, String password, String role, String email_address) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.password = password;
        this.role = role;
        this.email_address = email_address;
    }

    public Integer getId() {
        return id;
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
