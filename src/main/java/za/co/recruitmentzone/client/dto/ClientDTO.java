package za.co.recruitmentzone.client.dto;


import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import za.co.recruitmentzone.util.enums.Industry;

import java.io.Serializable;

public class ClientDTO implements Serializable {
    private String name;
    @Enumerated(EnumType.STRING)
    private Industry industry;
    private String contactPerson_FirstName;
    private String contactPerson_last_name;
    @Email(message = "Email should be valid")
    private String contactPerson_email_address;
    private String contactPerson_land_line;
    private String contactPerson_cell_phone;

    private String contactPerson_designation;

    public ClientDTO() {
    }

    public ClientDTO(String name,Industry industry, String contactPerson_FirstName,
                     String contactPerson_last_name, String contactPerson_email_address,
                     String contactPerson_land_line, String contactPerson_cell_phone, String contactPerson_designation) {
        this.name = name;
        this.industry = industry;
        this.contactPerson_FirstName = contactPerson_FirstName;
        this.contactPerson_last_name = contactPerson_last_name;
        this.contactPerson_email_address = contactPerson_email_address;
        this.contactPerson_land_line = contactPerson_land_line;
        this.contactPerson_cell_phone = contactPerson_cell_phone;
        this.contactPerson_designation=contactPerson_designation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Industry getIndustry() {
        return industry;
    }

    public void setIndustry(Industry industry) {
        this.industry = industry;
    }

    public String getContactPerson_FirstName() {
        return contactPerson_FirstName;
    }

    public void setContactPerson_FirstName(String contactPerson_FirstName) {
        this.contactPerson_FirstName = contactPerson_FirstName;
    }

    public String getContactPerson_designation() {
        return contactPerson_designation;
    }

    public void setContactPerson_designation(String contactPerson_designation) {
        this.contactPerson_designation = contactPerson_designation;
    }

    public String getContactPerson_last_name() {
        return contactPerson_last_name;
    }

    public void setContactPerson_last_name(String contactPerson_last_name) {
        this.contactPerson_last_name = contactPerson_last_name;
    }

    public String getContactPerson_email_address() {
        return contactPerson_email_address;
    }

    public void setContactPerson_email_address(String contactPerson_email_address) {
        this.contactPerson_email_address = contactPerson_email_address;
    }

    public String getContactPerson_land_line() {
        return contactPerson_land_line;
    }

    public void setContactPerson_land_line(String contactPerson_land_line) {
        this.contactPerson_land_line = contactPerson_land_line;
    }

    public String getContactPerson_cell_phone() {
        return contactPerson_cell_phone;
    }

    public void setContactPerson_cell_phone(String contactPerson_cell_phone) {
        this.contactPerson_cell_phone = contactPerson_cell_phone;
    }


    public String printClientDTO() {
        return "ClientDTO{" +
                "name='" + name + '\'' +
                ", industry=" + industry +
                ", contactPerson_FirstName='" + contactPerson_FirstName + '\'' +
                ", contactPerson_last_name='" + contactPerson_last_name + '\'' +
                ", contactPerson_email_address='" + contactPerson_email_address + '\'' +
                ", contactPerson_land_line='" + contactPerson_land_line + '\'' +
                ", contactPerson_cell_phone='" + contactPerson_cell_phone + '\'' +
                ", contactPerson_designation='" + contactPerson_designation + '\'' +
                '}';
    }
}
