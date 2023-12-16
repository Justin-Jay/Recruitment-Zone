package za.co.RecruitmentZone.candidate.entity;

import jakarta.persistence.*;
import org.springframework.web.multipart.MultipartFile;
import za.co.RecruitmentZone.util.Enums.EducationLevel;
import za.co.RecruitmentZone.util.Enums.Province;

public class CandidateDTO {
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

    public CandidateDTO(String first_name, String last_name, String id_number, String email_address,
                        String phone_number, Province current_province, String current_role, String current_employer, String seniority_level, EducationLevel education_level, Boolean relocation, String fileLocation, MultipartFile file) {
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
        this.fileLocation = fileLocation;
        this.file = file;
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

    public String getFileLocation() {
        return fileLocation;
    }

    public void setFileLocation(String fileLocation) {
        this.fileLocation = fileLocation;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }
}
