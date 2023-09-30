package za.co.RecruitmentZone.Vacancy;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class VacancyActivatedEventListener {

    @EventListener
    public void handleVacancyActivatedEvent(VacancyActivatedEvent event) {
        Long vacancyId = event.getVacancyId();

        // Perform actions when a vacancy becomes active
        // For example, send notifications or update UI
    }
}

