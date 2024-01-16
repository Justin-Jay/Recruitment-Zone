package za.co.recruitmentzone.vacancy.dto;

import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotEmpty;
import za.co.recruitmentzone.util.enums.EmpType;
import za.co.recruitmentzone.util.enums.Industry;
import za.co.recruitmentzone.util.enums.JobType;
import za.co.recruitmentzone.util.enums.VacancyStatus;

import java.time.LocalDate;


public class VacancyDTO {
    @NotEmpty(message = "Please enter job title")
    private String job_title;
    @NotEmpty(message = "Please enter job description")
    private String job_description;
    @NotEmpty(message = "Please select seniority level")
    private String seniority_level;
    @NotEmpty(message = "Please enter requirements")
    private String requirements;
    @NotEmpty(message = "Location must not be empty")
    private String location;
    @Enumerated(EnumType.STRING)
    private Industry industry;
    @FutureOrPresent(message = "Date cannot be in the past")
    private LocalDate publish_date;
    @Future(message = "Date must be in the future")
    private LocalDate end_date;
    @Enumerated(EnumType.STRING)
    private VacancyStatus status;
    @Enumerated(EnumType.STRING)
    private JobType jobType;
    @Enumerated(EnumType.STRING)
    private EmpType empType;
    private Long clientID;
    private Long employeeID;
    private Long vacancyID;

    public VacancyDTO() {
    }

    public VacancyDTO(String job_title, String job_description, String seniority_level, String requirements, String location, Industry industry, LocalDate publish_date, LocalDate end_date, VacancyStatus status, JobType jobType, EmpType empType, Long clientID, Long employeeID, Long vacancyID) {
        this.job_title = job_title;
        this.job_description = job_description;
        this.seniority_level = seniority_level;
        this.requirements = requirements;
        this.location = location;
        this.industry = industry;
        this.publish_date = publish_date;
        this.end_date = end_date;
        this.status = status;
        this.jobType = jobType;
        this.empType = empType;
        this.clientID = clientID;
        this.employeeID = employeeID;
        this.vacancyID = vacancyID;
    }

    public String getJob_title() {
        return job_title;
    }

    public void setJob_title(String job_title) {
        this.job_title = job_title;
    }

    public String getJob_description() {
        return job_description;
    }

    public void setJob_description(String job_description) {
        this.job_description = job_description;
    }

    public String getSeniority_level() {
        return seniority_level;
    }

    public void setSeniority_level(String seniority_level) {
        this.seniority_level = seniority_level;
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

    public Industry getIndustry() {
        return industry;
    }

    public void setIndustry(Industry industry) {
        this.industry = industry;
    }

    public LocalDate getPublish_date() {
        return publish_date;
    }

    public void setPublish_date(LocalDate publish_date) {
        this.publish_date = publish_date;
    }

    public LocalDate getEnd_date() {
        return end_date;
    }

    public void setEnd_date(LocalDate end_date) {
        this.end_date = end_date;
    }

    public VacancyStatus getStatus() {
        return status;
    }

    public void setStatus(VacancyStatus status) {
        this.status = status;
    }

    public JobType getJobType() {
        return jobType;
    }

    public void setJobType(JobType jobType) {
        this.jobType = jobType;
    }

    public EmpType getEmpType() {
        return empType;
    }

    public void setEmpType(EmpType empType) {
        this.empType = empType;
    }

    public Long getClientID() {
        return clientID;
    }

    public void setClientID(Long clientID) {
        this.clientID = clientID;
    }

    public Long getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(Long employeeID) {
        this.employeeID = employeeID;
    }

    public Long getVacancyID() {
        return vacancyID;
    }

    public void setVacancyID(Long vacancyID) {
        this.vacancyID = vacancyID;
    }

    @Override
    public String toString() {
        return "VacancyDTO{" +
                "job_title='" + job_title + '\'' +
                ", job_description='" + job_description + '\'' +
                ", seniority_level='" + seniority_level + '\'' +
                ", requirements='" + requirements + '\'' +
                ", location='" + location + '\'' +
                ", industry=" + industry +
                ", publish_date=" + publish_date +
                ", end_date=" + end_date +
                ", status=" + status +
                ", jobType=" + jobType +
                ", empType=" + empType +
                ", clientID=" + clientID +
                ", employeeID=" + employeeID +
                ", vacancyID=" + vacancyID +
                '}';
    }
    public String printVacancy(){
        return "Vacancy{" +
                "vacancyID=" + vacancyID +
                ", job_title='" + job_title + '\'' +
                ", seniority_level='" + seniority_level + '\'' +
                ", publish_date='" + publish_date + '\'' +
                ", end_date='" + end_date + '\'' +
                ", status=" + status +
                '}';
    }
}
