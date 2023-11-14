package za.co.RecruitmentZone.entity.domain;
import jakarta.persistence.*;
import za.co.RecruitmentZone.entity.Enums.ApplicationStatus;
import za.co.RecruitmentZone.entity.Enums.EducationLevel;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name ="candidate")
public class Candidate {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long candidateID;
    private String first_name;
    private String last_name;
    private String id_number;
    private String email_address;
    private String phone_number;
    private String current_province;
    private String current_role;
    private String current_employer;
    private String seniority_level;
    @Enumerated(EnumType.STRING)
    private EducationLevel education_level;
    private boolean relocation;
    private String cvFilePath; // Store the path to the CV file
    @OneToMany(mappedBy = "candidate", cascade = {
            CascadeType.MERGE,CascadeType.DETACH,
            CascadeType.REFRESH})
    private List<Application> applications;

    public Candidate() {
    }

    public Candidate(String first_name, String last_name, String id_number, String email_address, String phone_number, String current_province, String current_role, String current_employer, String seniority_level, EducationLevel education_level, boolean relocation) {
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
    }

    public long getCandidateID() {
        return candidateID;
    }

    public void setCandidateID(long candidateID) {
        this.candidateID = candidateID;
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

    public String getCurrent_province() {
        return current_province;
    }

    public void setCurrent_province(String current_province) {
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

    public boolean isRelocation() {
        return relocation;
    }

    public void setRelocation(boolean relocation) {
        this.relocation = relocation;
    }

    public String getCvFilePath() {
        return cvFilePath;
    }

    public void setCvFilePath(String cvFilePath) {
        this.cvFilePath = cvFilePath;
    }


    public EducationLevel getEducation_level() {
        return education_level;
    }

    public void setEducation_level(EducationLevel education_level) {
        this.education_level = education_level;
    }

    public List<Application> getApplications() {
        return applications;
    }

    public void setApplications(List<Application> applications) {
        this.applications = applications;
    }

    @Override
    public String toString() {
        return "Candidate{" +
                "candidateID=" + candidateID +
                ", first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", id_number='" + id_number + '\'' +
                ", email_address='" + email_address + '\'' +
                ", phone_number='" + phone_number + '\'' +
                ", current_province='" + current_province + '\'' +
                ", current_role='" + current_role + '\'' +
                ", current_employer='" + current_employer + '\'' +
                ", seniority_level='" + seniority_level + '\'' +
                ", education_level=" + education_level +
                ", relocation=" + relocation +
                ", cvFilePath='" + cvFilePath + '\'' +
                '}';
    }

}
