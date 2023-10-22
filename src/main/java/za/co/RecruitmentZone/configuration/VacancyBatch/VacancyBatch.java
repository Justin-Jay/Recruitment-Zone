package za.co.RecruitmentZone.configuration.VacancyBatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cglib.core.Local;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import za.co.RecruitmentZone.vacancy.events.VacancyActivatedEvent;
import za.co.RecruitmentZone.vacancy.events.VacancyExpiredEvent;
import za.co.RecruitmentZone.vacancy.repository.VacancyRepository;
import za.co.RecruitmentZone.vacancy.util.Vacancy;
import za.co.RecruitmentZone.vacancy.util.VacancyStatus;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class VacancyBatch {
    private final VacancyRepository vacancyRepository;
    private final ApplicationEventPublisher eventPublisher;
    Logger log = LoggerFactory.getLogger(VacancyBatch.class);

    public VacancyBatch(VacancyRepository vacancyRepository, ApplicationEventPublisher eventPublisher) {
        this.vacancyRepository = vacancyRepository;
        this.eventPublisher = eventPublisher;
    }

    //@Scheduled(fixedRate = 3600000) // Run every hour
    //@Transactional // Ensures consistency
    public void updateVacancyStatus() {
        try {
            LocalDateTime currentDate = LocalDateTime.now();
            List<Vacancy> vacancies = (List<Vacancy>) vacancyRepository.findAll();

            for (Vacancy vacancy : vacancies) {
                LocalDateTime startDate = vacancy.getStartDate();
                LocalDateTime endDate = vacancy.getEndDate();

                if (startDate.isBefore(currentDate) || startDate.isEqual(currentDate)) {
                    if (endDate.isBefore(currentDate) || endDate.isEqual(currentDate)) {
                        if (vacancy.getStatus() != VacancyStatus.EXPIRED) {
                            vacancy.setStatus(VacancyStatus.EXPIRED);
                            vacancyRepository.save(vacancy);
                            Clock clock = Clock.systemDefaultZone();
                            eventPublisher.publishEvent(new VacancyExpiredEvent(this, clock));
                        }
                    } else {
                        if (vacancy.getStatus() != VacancyStatus.ACTIVE) {
                            vacancy.setStatus(VacancyStatus.ACTIVE);
                            vacancyRepository.save(vacancy);
                            Clock clock = Clock.systemDefaultZone();
                            eventPublisher.publishEvent(new VacancyActivatedEvent(this, clock));
                        }
                    }
                }
            }
        } catch (Exception e) {
            log.info("An error occurred while updating vacancy statuses: {}", e.getMessage(), e);
        }
    }
}
