package za.co.recruitmentzone.application.dto;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
import za.co.recruitmentzone.util.enums.EducationLevel;
import za.co.recruitmentzone.util.enums.Province;

import java.io.Serializable;

@Data
public class NewApplicationDTO implements Serializable {
    @NotEmpty(message = "First Name Is Mandatory")
    @Size(min = 5, max = 50,message = "Min 2, Max 50")
    private String first_name;

    @NotEmpty(message = "Last Name Is Mandatory")
    @Size(min = 5, max = 50,message = "Min 2, Max 50")
    private String last_name;

    @NotEmpty(message = "ID Number is mandatory")
    private String id_number;

    @NotEmpty(message = "Please enter email address")
    private String email_address;

    @Size(min = 10, max = 10, message = "Please enter valid phone number")
    private String phone_number;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Make selection")
    private Province current_province;

    @NotEmpty(message = "Enter your current role")
    private String current_role;

    @NotEmpty(message = "Enter your current employers name")
    private String current_employer;

    @NotEmpty(message = "Enter your current seniority level")
    private String seniority_level;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Make selection")
    private EducationLevel education_level;

    private Boolean relocation;

    @Transient
    @NotNull(message = "PLease attach a file")
    private MultipartFile documentAttachment;

    private Long vacancyID;

    private Long clientID;

    private String vacancyName;

    public NewApplicationDTO() {
    }

    public NewApplicationDTO(Long vacancyID, String vacancyName) {
        this.vacancyID = vacancyID;
        this.vacancyName = vacancyName;
    }

    public NewApplicationDTO(Long vacancyID, String vacancyName, String first_name, String last_name,
                             String id_number, String email_address, String phone_number, Province current_province, String current_role, String current_employer, String seniority_level, EducationLevel education_level, Boolean relocation, MultipartFile documentAttachment) {
        this.vacancyID = vacancyID;
        this.vacancyName = vacancyName;
        this.first_name = first_name;
        this.last_name = last_name;
        this.id_number = id_number;
        this.email_address = email_address;
        this.phone_number = phone_number;
        this.current_province = current_province;
        this.current_role = current_role;
        this.current_employer = current_employer;
        this.seniority_level = seniority_level;
        this.education_level = education_level;
        this.relocation = relocation;
        this.documentAttachment = documentAttachment;
    }


    public String printNewApplicationDTO() {
        return "NewApplicationDTO{" +
                "vacancyID=" + vacancyID +
                ", clientID=" + clientID +
                ", vacancyName='" + vacancyName + '\'' +
                ", first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                '}';
    }
}


