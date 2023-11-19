/*
package za.co.RecruitmentZone.entity.domain.backupdomains;

import jakarta.persistence.*;

@Entity
@Table(name="vacancy")
public class Vacancy {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "vacancyID")
    private Long vacancyID;
    @Column(name = "jobTitle")
    private String jobTitle;
    @Column(name = "jobDescription")
    private String jobDescription;
    @Column(name = "seniority_level")
    private String seniority_level;
    @Column(name = "category")
    private String category;
    @Column(name = "requirements")
    private String requirements;
    @Column(name = "location")
    private String location;
    @Column(name = "Industry")
    private String industry;
    @Column(name = "publish_date")
    private String publish_date;
    @Column(name = "end_date")
    private String end_date;
    @Column(name = "status")
    private String status;
    @Column(name = "jobType")
    private String jobType;
    @Column(name = "empType")
    private String empType;
    @Column(name = "employeeID")
    private Long employeeID; // contact name , contact number contact email
    public Vacancy() {
    }

    public Vacancy(String jobTitle, String jobDescription, String seniority_level, String category, String requirements, String location, String industry, String publish_date, String end_date, String jobType, String empType, Long employeeID) {
        this.jobTitle = jobTitle;
        this.jobDescription = jobDescription;
        this.seniority_level = seniority_level;
        this.category = category;
        this.requirements = requirements;
        this.location = location;
        this.industry = industry;
        this.publish_date = publish_date;
        this.end_date = end_date;
        this.jobType = jobType;
        this.empType = empType;
        this.employeeID = employeeID;
    }

    public void setVacancyID(Long vacancyID) {
        this.vacancyID = vacancyID;
    }

    public Long getVacancyID() {
        return vacancyID;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
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

    public String getPublish_date() {
        return publish_date;
    }

    public void setPublish_date(String publish_date) {
        this.publish_date = publish_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getJobType() {
        return jobType;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType;
    }

    public String getEmpType() {
        return empType;
    }

    public void setEmpType(String empType) {
        this.empType = empType;
    }

    public Long getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(Long employeeID) {
        this.employeeID = employeeID;
    }

    @Override
    public String toString() {
        return "Vacancy{" +
                "jobTitle='" + jobTitle + '\'' +
                ", jobDescription='" + jobDescription + '\'' +
                ", seniority_level='" + seniority_level + '\'' +
                ", category='" + category + '\'' +
                ", requirements='" + requirements + '\'' +
                ", location='" + location + '\'' +
                ", industry='" + industry + '\'' +
                ", publish_date='" + publish_date + '\'' +
                ", end_date='" + end_date + '\'' +
                ", status='" + status + '\'' +
                ", jobType='" + jobType + '\'' +
                ", empType='" + empType + '\'' +
                ", employeeID=" + employeeID +
                '}';
    }
}
*/
