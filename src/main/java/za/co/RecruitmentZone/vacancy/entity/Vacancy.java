package za.co.RecruitmentZone.vacancy.entity;

import jakarta.persistence.*;
import za.co.RecruitmentZone.application.entity.Application;
import za.co.RecruitmentZone.client.entity.Client;
import za.co.RecruitmentZone.employee.entity.Employee;
import za.co.RecruitmentZone.util.Enums.EmpType;
import za.co.RecruitmentZone.util.Enums.Industry;
import za.co.RecruitmentZone.util.Enums.JobType;
import za.co.RecruitmentZone.util.Enums.VacancyStatus;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "vacancy")
public class Vacancy {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long vacancyID;
    private String job_title;
    private String job_description;
    private String seniority_level;
    private String requirements;
    private String category;
    private String location;
    @Enumerated(EnumType.STRING)
    private Industry industry;
    private LocalDate publish_date;
    private LocalDate end_date;
    @Enumerated(EnumType.STRING)
    private VacancyStatus status;
    @Enumerated(EnumType.STRING)
    private JobType jobType;
    @Enumerated(EnumType.STRING)
    private EmpType empType;

    @ManyToOne(
            cascade = {
                    CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH
            })
    @JoinColumn(name = "clientid")
    private Client client;

    @ManyToOne(cascade = {
            CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH
    })
    @JoinColumn(name = "employeeID")
    private Employee employee;

    @OneToMany(mappedBy = "vacancy",
            cascade = {
                    CascadeType.PERSIST,CascadeType.MERGE,CascadeType.DETACH,CascadeType.REFRESH
            })
    private Set<Application> applications;

    public Vacancy() {
    }


    public Vacancy(String job_title, String job_description, String seniority_level, String requirements, String category, String location, Industry industry, LocalDate publish_date, LocalDate end_date, VacancyStatus status, JobType jobType, EmpType empType) {
        this.job_title = job_title;
        this.job_description = job_description;
        this.seniority_level = seniority_level;
        this.requirements = requirements;
        this.category = category;
        this.location = location;
        this.industry = industry;
        this.publish_date = publish_date;
        this.end_date = end_date;
        this.status = status;
        this.jobType = jobType;
        this.empType = empType;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
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

    public Long getVacancyID() {
        return vacancyID;
    }

    public void setVacancyID(Long vacancyID) {
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


    public VacancyStatus getStatus() {
        return status;
    }

    public void setStatus(VacancyStatus status) {
        this.status = status;
    }

    public EmpType getEmpType() {
        return empType;
    }

    public void setEmpType(EmpType empType) {
        this.empType = empType;
    }



    public JobType getJobType() {
        return jobType;
    }

    public void setJobType(JobType jobType) {
        this.jobType = jobType;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Set<Application> getApplications() {
        return applications;
    }

    public void setApplications(Set<Application> applications) {
        this.applications = applications;
    }

    @Override
    public String toString() {
        return "Vacancy{" +
                "vacancyID=" + vacancyID +
                ", job_title='" + job_title + '\'' +
                ", job_description='" + job_description + '\'' +
                ", seniority_level='" + seniority_level + '\'' +
                ", requirements='" + requirements + '\'' +
                ", location='" + location + '\'' +
                ", industry=" + industry +
                ", publish_date='" + publish_date + '\'' +
                ", end_date='" + end_date + '\'' +
                ", status=" + status +
                ", jobType=" + jobType +
                ", empType=" + empType +
                ", client=" + client +
                ", employee=" + employee +
                ", applications=" + applications +
                '}';
    }

        public void addApplication(Application application){
        if (applications ==null){
            applications = new HashSet<>();
        }
        applications.add(application);
        application.setVacancy(this);
    }


}
