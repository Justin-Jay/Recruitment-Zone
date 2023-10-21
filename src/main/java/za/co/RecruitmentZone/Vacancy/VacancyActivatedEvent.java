package za.co.RecruitmentZone.Vacancy;

import org.springframework.context.ApplicationEvent;

public class VacancyActivatedEvent extends ApplicationEvent {
    private final Long vacancyId;

    public VacancyActivatedEvent(Object source, Long vacancyId) {
        super(source);
        this.vacancyId = vacancyId;
    }

    public Long getVacancyId() {
        return vacancyId;
    }
}

