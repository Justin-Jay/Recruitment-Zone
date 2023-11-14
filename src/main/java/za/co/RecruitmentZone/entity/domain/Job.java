package za.co.RecruitmentZone.entity.domain;

import jakarta.persistence.*;
import za.co.RecruitmentZone.entity.Enums.*;

import java.util.Objects;

@Entity
@Table(name="job")
public class Job {
    @Id
    @GeneratedValue()
    private Long jobID;
    private String jobTitle;
    @Enumerated(EnumType.STRING)
    private JobType jobType;
    @Enumerated(EnumType.STRING)
    private EmpType EmpType;
    private String jobDescription;
    private String seniority_level;
    private String category;
    private String requirements;
    private String location;
    private String industry;

    public Job() {
    }

    public Job(String jobTitle, JobType jobType, za.co.RecruitmentZone.entity.Enums.EmpType empType, String jobDescription, String seniority_level, String category, String requirements, String location, String industry) {
        this.jobTitle = jobTitle;
        this.jobType = jobType;
        EmpType = empType;
        this.jobDescription = jobDescription;
        this.seniority_level = seniority_level;
        this.category = category;
        this.requirements = requirements;
        this.location = location;
        this.industry = industry;
    }

    public Long getJobID() {
        return jobID;
    }


    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public JobType getJobType() {
        return jobType;
    }

    public void setJobType(JobType jobType) {
        this.jobType = jobType;
    }

    public za.co.RecruitmentZone.entity.Enums.EmpType getEmpType() {
        return EmpType;
    }

    public void setEmpType(za.co.RecruitmentZone.entity.Enums.EmpType empType) {
        EmpType = empType;
    }

    public String getJobDescription() {
        return jobDescription;
    }

    public void setJobDescription(String jobDescription) {
        this.jobDescription = jobDescription;
    }

    public String getSeniority_level() {
        return seniority_level;
    }

    public void setSeniority_level(String seniority_level) {
        this.seniority_level = seniority_level;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getRequirements() {
        return requirements;
    }

    public void setRequirements(String requirements) {
        this.requirements = requirements;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    @Override
    public String toString() {
        return "Job{" +
                "jobTitle='" + jobTitle + '\'' +
                ", jobType=" + jobType +
                ", EmpType=" + EmpType +
                ", jobDescription='" + jobDescription + '\'' +
                ", seniority_level='" + seniority_level + '\'' +
                ", category='" + category + '\'' +
                ", requirements='" + requirements + '\'' +
                ", location='" + location + '\'' +
                ", industry='" + industry + '\'' +
                '}';
    }
}
