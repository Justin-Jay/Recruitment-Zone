package za.co.RecruitmentZone.Vacancy;

import org.springframework.stereotype.Service;
import za.co.RecruitmentZone.Events.EventPublisherService;

import java.util.List;

@Service
class VacancyService {

    private final VacancyRepository vacancyRepository;
    private final EventPublisherService eventPublisherService;

    public VacancyService(VacancyRepository vacancyRepository, EventPublisherService eventPublisherService) {
        this.vacancyRepository = vacancyRepository;
        this.eventPublisherService = eventPublisherService;
    }

    // Inject the repository and event publisher service

    public void addVacancy(Vacancy vacancy) {
        // Perform any necessary validation or business logic
        vacancyRepository.save(vacancy);
    }

    public List<Vacancy> getAllVacancies() {
        return (List<Vacancy>) vacancyRepository.findAll();
    }

    public void publishVacancy(Long vacancyId) {
        // Business logic to publish a vacancy
        // ...

        // Trigger the event when a vacancy is published
        eventPublisherService.publishVacancyPublishedEvent(vacancyId);
    }

    public void expireVacancy(Long vacancyId) {
        // Business logic to expire a vacancy
        // ...

        // Trigger the event when a vacancy expires
        eventPublisherService.publishVacancyExpiredEvent(vacancyId);
    }
}

