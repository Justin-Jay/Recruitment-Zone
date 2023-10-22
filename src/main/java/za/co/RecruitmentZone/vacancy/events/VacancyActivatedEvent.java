package za.co.RecruitmentZone.vacancy.events;

import org.springframework.context.ApplicationEvent;
import za.co.RecruitmentZone.vacancy.util.Vacancy;

import java.time.Clock;

public class VacancyActivatedEvent extends ApplicationEvent {

    Integer vacancyID;


    public VacancyActivatedEvent(Object source) {
        super(source);
    }

    public VacancyActivatedEvent(Object source, Clock clock) {
        super(source, clock);
    }

    public Integer getVacancyID() {
        return vacancyID;
    }

    public void setVacancyID(Integer vacancyID) {
        this.vacancyID = vacancyID;
    }
}

