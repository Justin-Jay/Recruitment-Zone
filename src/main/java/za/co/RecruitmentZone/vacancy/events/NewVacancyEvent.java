package za.co.RecruitmentZone.vacancy.events;

import org.springframework.context.ApplicationEvent;
import za.co.RecruitmentZone.vacancy.util.Vacancy;

import java.time.Clock;

public class NewVacancyEvent extends ApplicationEvent {

    Vacancy vacancy;

    public NewVacancyEvent(Object source) {
        super(source);
    }

    public NewVacancyEvent(Object source, Clock clock) {
        super(source, clock);
    }


    public Vacancy getVacancy() {
        return vacancy;
    }

    public void setVacancy(Vacancy vacancy) {
        this.vacancy = vacancy;
    }
}

