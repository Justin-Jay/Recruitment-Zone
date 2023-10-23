package za.co.RecruitmentZone.entity;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class ApplicationUserDTO {
    private String first_name;
    private String last_name;
    private String email_address;
    private String role;

    public ApplicationUserDTO() {
    }

    public ApplicationUserDTO(String first_name, String last_name, String email_address, String role) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.email_address = email_address;
        this.role = role;
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

    public String getEmail_address() {
        return email_address;
    }

    public void setEmail_address(String email_address) {
        this.email_address = email_address;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
