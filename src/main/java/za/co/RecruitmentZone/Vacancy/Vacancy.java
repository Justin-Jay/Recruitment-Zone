package za.co.RecruitmentZone.Vacancy;

import za.co.RecruitmentZone.Application.Application;
import za.co.RecruitmentZone.Recruiter.Recruiter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name="Vacancy")
public class Vacancy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private String industry;
    private boolean active;
    private VacancyDate startDate;
    private VacancyDate endDate;
    @Enumerated(EnumType.STRING)
    private VacancyStatus status;

    // Relationship: Many vacancies can have one recruiter
    @ManyToOne
    @JoinColumn(name = "recruiter_id")
    private Recruiter recruiter;

    // Relationship: Many candidates can apply for one vacancy
    @OneToMany(mappedBy = "appliedForVacancy")
    private List<Application> applications = new ArrayList<>();

    // Constructors, getters, and setters

    public Vacancy() {
    }

    public Vacancy(String title, String description, String industry, boolean active, VacancyDate startDate, VacancyDate endDate, VacancyStatus status, Recruiter recruiter, List<Application> applications) {
        this.title = title;
        this.description = description;
        this.industry = industry;
        this.active = active;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.recruiter = recruiter;
        this.applications = applications;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
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

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public VacancyDate getStartDate() {
        return startDate;
    }

    public void setStartDate(VacancyDate startDate) {
        this.startDate = startDate;
    }

    public VacancyDate getEndDate() {
        return endDate;
    }

    public void setEndDate(VacancyDate endDate) {
        this.endDate = endDate;
    }

    public VacancyStatus getStatus() {
        return status;
    }

    public void setStatus(VacancyStatus status) {
        this.status = status;
    }

    public Recruiter getRecruiter() {
        return recruiter;
    }

    public void setRecruiter(Recruiter recruiter) {
        this.recruiter = recruiter;
    }

    public List<Application> getApplications() {
        return applications;
    }

    public void setApplications(List<Application> applications) {
        this.applications = applications;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vacancy vacancy)) return false;
        return active == vacancy.active && Objects.equals(id, vacancy.id) && Objects.equals(title, vacancy.title) && Objects.equals(description, vacancy.description) && Objects.equals(startDate, vacancy.startDate) && Objects.equals(endDate, vacancy.endDate) && status == vacancy.status && Objects.equals(recruiter, vacancy.recruiter) && Objects.equals(applications, vacancy.applications);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, active, startDate, endDate, status, recruiter, applications);
    }
}
