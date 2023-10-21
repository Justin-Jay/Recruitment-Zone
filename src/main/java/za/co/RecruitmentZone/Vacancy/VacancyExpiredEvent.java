package za.co.RecruitmentZone.Vacancy;

import org.springframework.context.ApplicationEvent;

public class VacancyExpiredEvent extends ApplicationEvent {
    private Long vacancyId;

    public VacancyExpiredEvent(Object source, Long vacancyId) {
        super(source);
        this.vacancyId = vacancyId;
    }

    public Long getVacancyId() {
        return vacancyId;
    }
}

