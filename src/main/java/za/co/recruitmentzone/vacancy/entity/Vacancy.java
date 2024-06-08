package za.co.recruitmentzone.vacancy.entity;

import jakarta.persistence.*;
import za.co.recruitmentzone.application.entity.Application;
import za.co.recruitmentzone.client.entity.Client;
import za.co.recruitmentzone.employee.entity.Employee;
import za.co.recruitmentzone.util.enums.EmpType;
import za.co.recruitmentzone.util.enums.Industry;
import za.co.recruitmentzone.util.enums.JobType;
import za.co.recruitmentzone.util.enums.VacancyStatus;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "VACANCY")
public class Vacancy implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long vacancyID;
    @Column(name = "job_title")
    private String jobTitle;
    @Column(name = "job_description", length = 65535)
    private String job_description;
    @Column(name = "seniority_level")
    private String seniority_level;
    @Column(name = "requirements", length = 65535)
    private String requirements;
    @Column(name = "location")
    private String location;
    @Enumerated(EnumType.STRING)
    private Industry industry;
    @Column(name = "publish_date")
    private LocalDate publish_date;
    @Column(name = "end_date")
    private LocalDate end_date;
    @Enumerated(EnumType.STRING)
    private VacancyStatus status;
    @Enumerated(EnumType.STRING)
    private JobType jobType;
    @Enumerated(EnumType.STRING)
    private EmpType empType;
    @Column(name = "created")
    private LocalDateTime created;
    @Column(name = "vacancyRef")
    private String vacancyRef;
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

    @OneToMany(mappedBy = "vacancy",fetch = FetchType.EAGER,
            cascade = {
                    CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH
            })
    private List<Application> applications;

    public Vacancy() {
    }

    public Vacancy(String jobTitle, String job_description, String seniority_level, String requirements, String location, Industry industry, LocalDate publish_date, LocalDate end_date, VacancyStatus status, JobType jobType, EmpType empType) {
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
    }

    public Long getTheClientID() {
        return client.getClientID();
    }


    public Long getTheEmpID() {
        return employee.getEmployeeID();
    }

    public void setTheClientID(Long id) {
         client.setClientID(id);
    }


    public void setTheEmpID(Long id) {
        employee.setEmployeeID(id);
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

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String job_title) {
        this.jobTitle = job_title;
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

    public String getVacancyRef() {
        return vacancyRef;
    }

    public void setVacancyRef(String vacancyRef) {
        this.vacancyRef = vacancyRef;
    }

    public List<Application> getApplications() {
        if (this.applications!=null){
            List<Application> sortedApplications = new ArrayList<>(this.applications);
            Collections.sort(sortedApplications, new Comparator<Application>() {
                @Override
                public int compare(Application item1, Application item2) {
                    return item2.getDate_received().compareTo(item1.getDate_received());
                }
            });
            return sortedApplications;
        }
        else return new ArrayList<>();
    }

    public void setApplications(List<Application> applications) {
        this.applications = applications;
    }

    public void addApplication(Application application) {
        if (applications == null) {
            applications = new ArrayList<>() {
            };
        }
        applications.add(application);
        application.setVacancy(this);
    }
    public int getApplicationsSize() {
        if (applications == null) {
            return 0;
        }else return applications.size();
    }

    public String printVacancy() {
        return "Vacancy{" +
                "vacancyID=" + vacancyID +
                ", job_title='" + jobTitle + '\'' +
                ", seniority_level='" + seniority_level + '\'' +
                ", publish_date='" + publish_date + '\'' +
                ", end_date='" + end_date + '\'' +
                ", status=" + status +
                ", client=" + client.getName() +
                '}';
    }

}
