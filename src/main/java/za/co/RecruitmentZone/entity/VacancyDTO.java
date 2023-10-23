package za.co.RecruitmentZone.entity;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.time.LocalDateTime;

public class VacancyDTO {
    private String title;
    private String description;
    private String industry;

    // Constructors, getters, and setters

    public VacancyDTO() {
    }

    public VacancyDTO(String title, String description, String industry) {
        this.title = title;
        this.description = description;
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

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }
}
