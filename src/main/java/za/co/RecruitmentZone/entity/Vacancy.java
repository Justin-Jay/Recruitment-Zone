package za.co.RecruitmentZone.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name="Vacancy")
public class Vacancy {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer vacancyID;
    private String title;
    private String description;
    private String industry;
    private boolean active;
    private boolean pending;
    private boolean expired;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    @Enumerated(EnumType.STRING)
    private VacancyStatus status;

    // Replace object reference with ID
    @Column(name = "recruiterID")
    private Integer recruiterId;

    // Constructors, getters, and setters

    public Vacancy() {
    }

    public Vacancy(String title, String description, String industry, boolean active, boolean pending, boolean expired, LocalDateTime startDate, LocalDateTime endDate, VacancyStatus status, Integer recruiterId) {
        this.title = title;
        this.description = description;
        this.industry = industry;
        this.active = active;
        this.pending = pending;
        this.expired = expired;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.recruiterId = recruiterId;
    }

    public Integer getVacancyID() {
        return vacancyID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
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

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public VacancyStatus getStatus() {
        return status;
    }

    public void setStatus(VacancyStatus status) {
        this.status = status;
    }

    // Other getters and setters

    // Getters and setters for recruiterId

    public Integer getRecruiterId() {
        return recruiterId;
    }

    public void setRecruiterId(Integer recruiterId) {
        this.recruiterId = recruiterId;
    }

    // Removed the equals and hashCode methods as they require a full update to reflect the change in relationships


}
