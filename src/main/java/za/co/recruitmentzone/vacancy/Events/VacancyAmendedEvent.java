package za.co.recruitmentzone.vacancy.Events;

import org.springframework.context.ApplicationEvent;
import za.co.recruitmentzone.vacancy.entity.Vacancy;

import java.time.Clock;

public class VacancyAmendedEvent extends ApplicationEvent {
    private Integer vacancyID;

    private Vacancy vacancy;


    public VacancyAmendedEvent(Object source, Clock clock) {
        super(source, clock);
    }

    public VacancyAmendedEvent(Object source, Integer vacancyID) {
        super(source);
        this.vacancyID = vacancyID;
    }

    public Integer getVacancyID() {
        return vacancyID;
    }

    public void setVacancyID(Integer vacancyID) {
        this.vacancyID = vacancyID;
    }

    public Vacancy getVacancy() {
        return vacancy;
    }

    public void setVacancy(Vacancy vacancy) {
        this.vacancy = vacancy;
    }
}

