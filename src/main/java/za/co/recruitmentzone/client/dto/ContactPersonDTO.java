package za.co.recruitmentzone.client.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.io.Serializable;


public class ContactPersonDTO implements Serializable {
    private String first_name;
    @NotEmpty(message = "Last name must not be empty")
    private String last_name;
    private String email_address;
    @Size(min=10, max=10)
    private String land_line;
    @Size(min=10, max=10)
    private String cell_phone;

    private String designation;

    private Long clientID;

    private Long contactPersonID;



    public Long getClientID() {
        return clientID;
    }

    public Long getContactPersonID() {
        return contactPersonID;
    }

    public void setContactPersonID(Long contactPersonID) {
        this.contactPersonID = contactPersonID;
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

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getEmail_address() {
        return email_address;
    }

    public void setEmail_address(String email_address) {
        this.email_address = email_address;
    }

    public String getLand_line() {
        return land_line;
    }

    public void setLand_line(String land_line) {
        this.land_line = land_line;
    }

    public String getCell_phone() {
        return cell_phone;
    }

    public void setCell_phone(String cell_phone) {
        this.cell_phone = cell_phone;
    }

    public Long getclientID() {
        return clientID;
    }


    public void setClientID(Long clientID) {
        this.clientID = clientID;
    }

    public String printContactPersonDTO() {
        return "ContactPersonDTO{" +
                "first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", email_address='" + email_address + '\'' +
                ", land_line='" + land_line + '\'' +
                ", cell_phone='" + cell_phone + '\'' +
                ", designation='" + designation + '\'' +
                ", clientID=" + clientID +
                '}';
    }
}
