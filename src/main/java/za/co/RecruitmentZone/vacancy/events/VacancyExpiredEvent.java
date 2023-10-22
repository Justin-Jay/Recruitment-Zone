package za.co.RecruitmentZone.vacancy.events;

import org.springframework.context.ApplicationEvent;
import za.co.RecruitmentZone.vacancy.util.Vacancy;

import java.time.Clock;

public class VacancyExpiredEvent extends ApplicationEvent {
    private Integer vacancyID;

    private Vacancy vacancy;


    public VacancyExpiredEvent(Object source, Clock clock) {
        super(source, clock);
    }

    public VacancyExpiredEvent(Object source, Integer vacancyID) {
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

