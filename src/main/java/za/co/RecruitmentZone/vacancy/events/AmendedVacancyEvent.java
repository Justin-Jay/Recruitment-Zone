package za.co.RecruitmentZone.vacancy.events;

import org.springframework.context.ApplicationEvent;
import za.co.RecruitmentZone.vacancy.util.Vacancy;

import java.time.Clock;

public class AmendedVacancyEvent extends ApplicationEvent {

    Vacancy vacancy;

    public AmendedVacancyEvent(Object source) {
        super(source);
    }

    public AmendedVacancyEvent(Object source, Clock clock) {
        super(source, clock);
    }

    public Vacancy getVacancy() {
        return vacancy;
    }

    public void setVacancy(Vacancy vacancy) {
        this.vacancy = vacancy;
    }
}

