package za.co.recruitmentzone.vacancy.entity;

import jakarta.persistence.*;
import za.co.recruitmentzone.application.entity.Application;
import za.co.recruitmentzone.client.entity.Client;
import za.co.recruitmentzone.employee.entity.Employee;
import za.co.recruitmentzone.util.enums.EmpType;
import za.co.recruitmentzone.util.enums.Industry;
import za.co.recruitmentzone.util.enums.JobType;
import za.co.recruitmentzone.util.enums.VacancyStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "VACANCY")
public class Vacancy {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long vacancyID;
    private String job_title;
    private String job_description;
    private String seniority_level;
    private String requirements;
    private String location;
    @Enumerated(EnumType.STRING)
    private Industry industry;
    private LocalDate publish_date;
    private LocalDate end_date;
    @Enumerated(EnumType.STRING)
    private VacancyStatus status;
    @Enumerated(EnumType.STRING)
    @Column(name ="job_type")
    private JobType jobType;
    @Enumerated(EnumType.STRING)
    @Column(name ="emp_type")
    private EmpType empType;
    @Column(name="created")
    private LocalDateTime created;
    @ManyToOne(
            cascade = {
                    CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH
            })
    @JoinColumn(name = "clientid")
    private Client client;
private Long theClientID;
    @ManyToOne(cascade = {
            CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH
    })
    @JoinColumn(name = "employeeID")
    private Employee employee;

    private Long theEmpID;

    @OneToMany(mappedBy = "vacancy",
            cascade = {
                    CascadeType.PERSIST,CascadeType.MERGE,CascadeType.DETACH,CascadeType.REFRESH
            })
    private Set<Application> applications;

    public Vacancy() {
    }


    public Vacancy(String job_title, String job_description, String seniority_level, String requirements, String location, Industry industry, LocalDate publish_date, LocalDate end_date, VacancyStatus status, JobType jobType, EmpType empType) {
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
    }

    public Long getTheClientID() {
        return theClientID;
    }

    public void setTheClientID(Long theClientID) {
        this.theClientID = theClientID;
    }

    public Long getTheEmpID() {
        return theEmpID;
    }

    public void setTheEmpID(Long theEmpID) {
        this.theEmpID = theEmpID;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
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

    public String printVacancy(){
        return "Vacancy{" +
                "vacancyID=" + vacancyID +
                ", job_title='" + job_title + '\'' +
                ", seniority_level='" + seniority_level + '\'' +
                ", publish_date='" + publish_date + '\'' +
                ", end_date='" + end_date + '\'' +
                ", status=" + status +
                ", client=" + client +
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
