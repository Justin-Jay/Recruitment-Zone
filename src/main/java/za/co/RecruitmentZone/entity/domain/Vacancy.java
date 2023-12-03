package za.co.RecruitmentZone.entity.domain;

import jakarta.persistence.*;
import za.co.RecruitmentZone.entity.Enums.EmpType;
import za.co.RecruitmentZone.entity.Enums.Industry;
import za.co.RecruitmentZone.entity.Enums.JobType;
import za.co.RecruitmentZone.entity.Enums.VacancyStatus;

import java.util.Objects;


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
    private String location;
    @Enumerated(EnumType.STRING)
    private Industry industry;
    private String publish_date;
    private String end_date;
    @Enumerated(EnumType.STRING)
    private VacancyStatus status;
    @Enumerated(EnumType.STRING)
    private JobType jobType;
    @Enumerated(EnumType.STRING)
    private EmpType empType;

    /*  @ManyToOne(
              cascade = {
              CascadeType.PERSIST,CascadeType.MERGE,CascadeType.DETACH,CascadeType.REFRESH
      })*/
    private Long clientID;
    /*    @ManyToOne(cascade = {
                CascadeType.PERSIST,CascadeType.MERGE,CascadeType.DETACH,CascadeType.REFRESH
        })*/
    private Long employeeID;
  /*  @OneToMany(mappedBy = "vacancy",
            cascade = {
                    CascadeType.PERSIST,CascadeType.MERGE,CascadeType.DETACH,CascadeType.REFRESH
            })
    @JoinTable(
            name = "vacancy_application",
            joinColumns = @JoinColumn(name = "vacancyID"),
            inverseJoinColumns = @JoinColumn(name = "applicationID")
    )
    private List<Application> applications;*/


    public Vacancy() {
    }

    public Vacancy(String job_title, String job_description, String seniority_level, String requirements, String location, Industry industry, String publish_date, String end_date, VacancyStatus status, JobType jobType, EmpType empType) {
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

    public JobType getJobType() {
        return jobType;
    }

    public void setJobType(JobType jobType) {
        this.jobType = jobType;
    }

    @Override
    public String toString() {
        return "Vacancy{" +
                "job_title='" + job_title + '\'' +
                ", job_description='" + job_description + '\'' +
                ", seniority_level='" + seniority_level + '\'' +
                ", requirements='" + requirements + '\'' +
                ", location='" + location + '\'' +
                ", industry='" + industry + '\'' +
                ", publish_date='" + publish_date + '\'' +
                ", end_date='" + end_date + '\'' +
                ", status=" + status +
                ", jobType=" + jobType +
                ", empType='" + empType + '\'' +
                ", clientID=" + clientID +
                ", employeeID=" + employeeID +
                '}';
    }
    /*    public void addApplication(Application application){
        if (applications ==null){
            applications = new ArrayList<>();
        }
        applications.add(application);
        application.setVacancy(this);
    }*/






}
