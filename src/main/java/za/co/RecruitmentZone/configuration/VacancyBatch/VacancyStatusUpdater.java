package za.co.RecruitmentZone.configuration.VacancyBatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import za.co.RecruitmentZone.entity.Vacancy;
import za.co.RecruitmentZone.entity.VacancyStatus;
import za.co.RecruitmentZone.repository.VacancyRepository;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class VacancyStatusUpdater {
    private final VacancyRepository vacancyRepository;
    private final Logger log = LoggerFactory.getLogger(VacancyStatusUpdater.class);

    public VacancyStatusUpdater(VacancyRepository vacancyRepository) {
        this.vacancyRepository = vacancyRepository;
    }

    // update this to a batch job
    //@Scheduled(fixedRate = 3600000) // Run every hour
   // @Transactional // Ensures consistency
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
                        }
                    } else {
                        if (vacancy.getStatus() != VacancyStatus.ACTIVE) {
                            vacancy.setStatus(VacancyStatus.ACTIVE);
                            vacancyRepository.save(vacancy);
                        }
                    }
                }
            }
        } catch (Exception e) {
            log.info("An error occurred while updating vacancy statuses: {}", e.getMessage(), e);
        }
    }
}
