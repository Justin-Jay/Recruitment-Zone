package za.co.recruitmentzone.candidate.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
import za.co.recruitmentzone.util.enums.EducationLevel;
import za.co.recruitmentzone.util.enums.Province;

import java.io.Serializable;

@Data
public class CandidateDTO implements Serializable  {

    private String first_name;
    private String last_name;
    private String id_number;
    private String email_address;
    private String phone_number;
    @Enumerated(EnumType.STRING)
    private Province current_province;
    private String current_role;
    private String current_employer;
    private String seniority_level;
    @Enumerated(EnumType.STRING)
    private EducationLevel education_level;
    private Boolean relocation;
    private String fileLocation;
    @Transient
    private MultipartFile file;

    public CandidateDTO() {
    }


    public String printCandidateDTO() {
        return "CandidateDTO{" +
                "first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", email_address='" + email_address + '\'' +
                ", current_province=" + current_province +
                ", current_role='" + current_role + '\'' +
                ", current_employer='" + current_employer + '\'' +
                ", seniority_level='" + seniority_level + '\'' +
                ", education_level=" + education_level +
                ", relocation=" + relocation +
                ", fileLocation='" + fileLocation + '\'' +
                '}';
    }
}
