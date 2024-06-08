package za.co.recruitmentzone.employee.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serializable;

@Data
public class EmployeeDTO implements Serializable {
    @NotEmpty(message = "First Name Cannot be empty")
    public String first_name;
    @NotEmpty(message = "Last Name Cannot be empty")
    public String last_name;
    @NotEmpty(message = "Password cannot be blank")
    private String password;
    private String adminAgent;
    private String role;
    @Size(min=10, max=10,message = "Enter valid cell number")
    public String contact_number;

    public EmployeeDTO() {
    }

    public EmployeeDTO(String first_name, String last_name, String contact_number) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.contact_number = contact_number;
    }

    public String printEmployeeDTO() {
        return "EmployeeDTO{" +
                "first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", contact_number='" + contact_number + '\'' +
                '}';
    }
}
