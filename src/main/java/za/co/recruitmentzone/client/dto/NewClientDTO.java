package za.co.recruitmentzone.client.dto;


import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import za.co.recruitmentzone.util.enums.Industry;

import java.io.Serializable;

@Data
public class NewClientDTO implements Serializable {
    @NotEmpty(message = "Client Name Is Mandatory")
    @Size(min = 5, max = 50,message = "Client name: Min 2, Max 50")
    private String name;
    @Enumerated(EnumType.STRING)
    @NotNull(message = "Make selection")
    private Industry industry;
    @NotEmpty(message = "Enter a name")
    @Size(min = 5, max = 50,message = "First name: Min 2, Max 50")
    private String contactPerson_FirstName;

    @NotEmpty(message = "Enter a last name")
    @Size(min = 5, max = 50,message = "Last name: min 2 max 50")
    private String contactPerson_last_name;

    @Email(message = "Enter valid e-mail address")
    private String contactPerson_email_address;

    @NotEmpty(message = "Enter a land line number")
    @Size(min = 10, max = 10,message = "Enter valid phone number")
    private String contactPerson_land_line;

    @NotEmpty(message = "Enter a cell number")
    @Size(min = 10, max = 10,message = "Enter valid phone number")
    private String contactPerson_cell_phone;

    @NotEmpty(message = "Enter a designation")
    private String contactPerson_designation;

    public NewClientDTO() {
    }

    public NewClientDTO(String name, Industry industry, String contactPerson_FirstName,
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
