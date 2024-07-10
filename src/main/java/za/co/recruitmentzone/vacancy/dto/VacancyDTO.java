package za.co.recruitmentzone.vacancy.dto;

import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import za.co.recruitmentzone.util.enums.EmpType;
import za.co.recruitmentzone.util.enums.Industry;
import za.co.recruitmentzone.util.enums.JobType;
import za.co.recruitmentzone.util.enums.VacancyStatus;

import java.io.Serializable;
import java.time.LocalDate;


@Data
public class VacancyDTO implements Serializable {
    @NotEmpty(message = "Please enter job title")
    private String jobTitle;
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
    private long applicationCount;
    private String clientName;
    private String vacancyImageRef;

    public VacancyDTO() {
    }

    public VacancyDTO(long vacancyID) {
        this.vacancyID=vacancyID;
    }

    public VacancyDTO(String jobTitle, String job_description, String seniority_level, String requirements, String location, Industry industry, LocalDate publish_date, LocalDate end_date, VacancyStatus status, JobType jobType, EmpType empType, Long clientID, Long employeeID, Long vacancyID) {
        this.jobTitle = jobTitle;
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


 /*   public String printVacancy(){
        return "Vacancy{" +
                "vacancyID=" + vacancyID +
                ", job_title='" + jobTitle + '\'' +
                ", seniority_level='" + seniority_level + '\'' +
                ", publish_date='" + publish_date + '\'' +
                ", end_date='" + end_date + '\'' +
                ", status=" + status +
                '}';
    }
*/

    public String printVacancy() {
        return "VacancyDTO{" +
                "jobTitle='" + jobTitle + '\'' +
                ", seniority_level='" + seniority_level + '\'' +
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
                ", clientName='" + clientName + '\'' +
                ", vacancyImageRef='" + vacancyImageRef + '\'' +
                '}';
    }
}
