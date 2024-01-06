package za.co.recruitmentzone.application.dto;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.springframework.web.multipart.MultipartFile;
import za.co.recruitmentzone.util.Enums.EducationLevel;
import za.co.recruitmentzone.util.Enums.Province;
//    @NotEmpty(message = "Please enter job title")
//   @FutureOrPresent(message = "Date cannot be in the past")
//  @Future(message = "Date must be in the future")

public class NewApplicationDTO {
    private Long vacancyID;
      private Long clientID;
    private String vacancyName;
    @NotEmpty(message = "First Name Is Mandatory")
    private String first_name;
    @NotEmpty(message = "Last Name Is Mandatory")
    private String last_name;
    @NotEmpty(message = "ID Number is mandatory")
    private String id_number;
    @NotEmpty(message = " ")
    private String email_address;
    @Size(min=10, max=10)
    private String phone_number;
    @Enumerated(EnumType.STRING)
    private Province current_province;
    @NotEmpty(message = " ")
    private String current_role;

    private String current_employer;
    @NotEmpty(message = " ")
    private String seniority_level;
    @Enumerated(EnumType.STRING)
    private EducationLevel education_level;
    private Boolean relocation;
    @Transient
    private MultipartFile cvFile;

    public NewApplicationDTO() {
    }

    public NewApplicationDTO(Long vacancyID, String vacancyName, String first_name, String last_name,
                             String id_number, String email_address, String phone_number, Province current_province, String current_role, String current_employer, String seniority_level, EducationLevel education_level, Boolean relocation, MultipartFile cvFile) {
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
        this.cvFile = cvFile;
    }

    public Long getClientID() {
        return clientID;
    }

    public void setClientID(Long clientID) {
        this.clientID = clientID;
    }

    public String getVacancyName() {
        return vacancyName;
    }

    public void setVacancyName(String vacancyName) {
        this.vacancyName = vacancyName;
    }

    public Long getVacancyID() {
        return vacancyID;
    }

    public void setVacancyID(Long vacancyID) {
        this.vacancyID = vacancyID;
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

    public String getId_number() {
        return id_number;
    }

    public void setId_number(String id_number) {
        this.id_number = id_number;
    }

    public String getEmail_address() {
        return email_address;
    }

    public void setEmail_address(String email_address) {
        this.email_address = email_address;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public Province getCurrent_province() {
        return current_province;
    }

    public void setCurrent_province(Province current_province) {
        this.current_province = current_province;
    }

    public String getCurrent_role() {
        return current_role;
    }

    public void setCurrent_role(String current_role) {
        this.current_role = current_role;
    }

    public String getCurrent_employer() {
        return current_employer;
    }

    public void setCurrent_employer(String current_employer) {
        this.current_employer = current_employer;
    }

    public String getSeniority_level() {
        return seniority_level;
    }

    public void setSeniority_level(String seniority_level) {
        this.seniority_level = seniority_level;
    }

    public EducationLevel getEducation_level() {
        return education_level;
    }

    public void setEducation_level(EducationLevel education_level) {
        this.education_level = education_level;
    }

    public Boolean getRelocation() {
        return relocation;
    }

    public void setRelocation(Boolean relocation) {
        this.relocation = relocation;
    }

    public MultipartFile getCvFile() {
        return cvFile;
    }

    public void setCvFile(MultipartFile cvFile) {
        this.cvFile = cvFile;
    }

    @Override
    public String toString() {
        return "NewApplicationDTO{" +
                "vacancyID=" + vacancyID +
                ", clientID=" + clientID +
                ", vacancyName='" + vacancyName + '\'' +
                ", first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", phone_number='" + phone_number + '\'' +
                ", current_province=" + current_province +
                ", current_role='" + current_role + '\'' +
                ", current_employer='" + current_employer + '\'' +
                ", seniority_level='" + seniority_level + '\'' +
                ", education_level=" + education_level +
                ", relocation=" + relocation +
                ", cvFile=" + cvFile +
                '}';
    }
}


