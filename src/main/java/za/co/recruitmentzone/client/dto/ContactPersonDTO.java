package za.co.recruitmentzone.client.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import java.io.Serializable;

@Data
public class ContactPersonDTO implements Serializable {
    @NotEmpty(message = "First Name Is Mandatory")
    @Size(min = 5, max = 50,message = "Min 2, Max 50")
    private String first_name;
    @NotEmpty(message = "Last Name Is Mandatory")
    @Size(min = 5, max = 50,message = "Min 2, Max 50")
    private String last_name;
    //    @Email(message = "Enter valid email")
    @NotEmpty(message = "Email is Mandatory")
    private String email_address;
    @Size(min=10, max=10,message = "Enter valid land line")
    private String land_line;
    @Size(min=10, max=10,message = "Enter valid cell number")
    private String cell_phone;
    @NotEmpty(message = "Designation be empty")
    private String designation;
    private Long clientID;
    private Long contactPersonID;

    public ContactPersonDTO() {
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
