package za.co.RecruitmentZone.client.dto;


import jakarta.validation.constraints.Email;

public class ClientDTO {
    private String name;
    private String industry;
    private String contactPerson_FirstName;
    private String contactPerson_last_name;
    @Email(message = "Email should be valid")
    private String contactPerson_email_address;
    private String contactPerson_land_line;
    private String contactPerson_cell_phone;

    public ClientDTO() {
    }

    public ClientDTO(String name, String industry, String contactPerson_FirstName, String contactPerson_last_name, String contactPerson_email_address, String contactPerson_land_line, String contactPerson_cell_phone) {
        this.name = name;
        this.industry = industry;
        this.contactPerson_FirstName = contactPerson_FirstName;
        this.contactPerson_last_name = contactPerson_last_name;
        this.contactPerson_email_address = contactPerson_email_address;
        this.contactPerson_land_line = contactPerson_land_line;
        this.contactPerson_cell_phone = contactPerson_cell_phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getContactPerson_FirstName() {
        return contactPerson_FirstName;
    }

    public void setContactPerson_FirstName(String contactPerson_FirstName) {
        this.contactPerson_FirstName = contactPerson_FirstName;
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
}
