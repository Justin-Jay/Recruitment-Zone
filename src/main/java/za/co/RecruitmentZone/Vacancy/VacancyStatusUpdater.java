package za.co.RecruitmentZone.Vacancy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class VacancyStatusUpdater {

    @Autowired
    private VacancyRepository vacancyRepository;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Scheduled(fixedRate = 3600000) // Run every hour (adjust as needed)
    public void updateVacancyStatus() {
        LocalDateTime currentDate = LocalDateTime.now();

        List<Vacancy> vacancies = (List<Vacancy>) vacancyRepository.findAll();

        for (Vacancy vacancy : vacancies) {
            VacancyDate startDate = new VacancyDate(currentDate.toString());
            VacancyDate endDate = new VacancyDate(currentDate.toString());

            if (startDate.isBefore(currentDate)) {
                if (endDate.isBefore(currentDate)) {
                    // The vacancy has reached its expiration date
                    if (vacancy.getStatus() != VacancyStatus.EXPIRED) {
                        vacancy.setStatus(VacancyStatus.EXPIRED);
                        vacancyRepository.save(vacancy);

                        // Publish the event when a vacancy becomes expired
                        eventPublisher.publishEvent(new VacancyExpiredEvent(this, vacancy.getId()));
                    }
                } else {
                    // The vacancy's start date has been reached but it's not expired yet
                    if (vacancy.getStatus() != VacancyStatus.ACTIVE) {
                        vacancy.setStatus(VacancyStatus.ACTIVE);
                        vacancyRepository.save(vacancy);

                        // Publish the event when a vacancy becomes active
                        eventPublisher.publishEvent(new VacancyActivatedEvent(this, vacancy.getId()));
                    }
                }
            }
        }
    }
}




