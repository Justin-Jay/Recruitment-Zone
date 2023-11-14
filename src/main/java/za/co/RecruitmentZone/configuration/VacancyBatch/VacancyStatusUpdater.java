package za.co.RecruitmentZone.configuration.VacancyBatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import za.co.RecruitmentZone.entity.Enums.VacancyStatus;
import za.co.RecruitmentZone.entity.domain.Vacancy;
import za.co.RecruitmentZone.repository.VacancyRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

                String start = vacancy.getPublish_date();
                String end = vacancy.getEnd_date();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

                LocalDateTime startDate = LocalDateTime.parse(start, formatter);
                LocalDateTime endDate = LocalDateTime.parse(end, formatter);

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
