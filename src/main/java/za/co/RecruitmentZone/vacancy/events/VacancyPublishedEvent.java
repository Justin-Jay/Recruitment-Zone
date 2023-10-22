package za.co.RecruitmentZone.vacancy.events;

import org.springframework.context.ApplicationEvent;

public class VacancyPublishedEvent extends ApplicationEvent {
    private final Long vacancyId;

    public VacancyPublishedEvent(Object source, Long vacancyId) {
        super(source);
        this.vacancyId = vacancyId;
    }

    public Long getVacancyId() {
        return vacancyId;
    }
}

