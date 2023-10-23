package za.co.RecruitmentZone.entity;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.time.LocalDateTime;

public class VacancyDTO {
    private String title;
    private String Description;
    private String industry;;
    private LocalDateTime startDate;;
    private LocalDateTime endDate;
    @Enumerated(EnumType.STRING)
    private VacancyStatus status;

}
