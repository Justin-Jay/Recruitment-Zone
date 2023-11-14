package za.co.RecruitmentZone.entity.domain;

import jakarta.persistence.*;
import za.co.RecruitmentZone.entity.Enums.VacancyStatus;

@Entity
@Table(name="vacancy")
public class Vacancy {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long vacancyID;
    private String publish_date;
    private String end_date;
    private boolean active;
    private boolean pending;
    private boolean expired;
    @Enumerated(EnumType.STRING)
    private VacancyStatus status;
    @Column(name = "jobID")
    private Long jobID;
    @Column(name = "employeeID")
    private Long employeeID; // contact name , contact number contact email
    public Vacancy() {
    }

    public Vacancy(String publish_date, String end_date, VacancyStatus status, Long jobID, Long employeeID) {
        this.publish_date = publish_date;
        this.end_date = end_date;
        this.status = status;
        this.jobID = jobID;
        this.employeeID = employeeID;
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

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isPending() {
        return pending;
    }

    public void setPending(boolean pending) {
        this.pending = pending;
    }

    public boolean isExpired() {
        return expired;
    }

    public void setExpired(boolean expired) {
        this.expired = expired;
    }

    public Long getJobID() {
        return jobID;
    }

    public void setJobID(Long jobID) {
        this.jobID = jobID;
    }

    public Long getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(Long employeeID) {
        this.employeeID = employeeID;
    }

    public VacancyStatus getStatus() {
        return status;
    }

    public void setStatus(VacancyStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Vacancy{" +
                "publish_date='" + publish_date + '\'' +
                ", end_date='" + end_date + '\'' +
                ", active=" + active +
                ", pending=" + pending +
                ", expired=" + expired +
                ", jobID=" + jobID +
                ", employeeID=" + employeeID +
                '}';
    }
}
