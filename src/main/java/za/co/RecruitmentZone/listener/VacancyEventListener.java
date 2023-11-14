/*
package za.co.RecruitmentZone.listener;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.modulith.ApplicationModuleListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import za.co.RecruitmentZone.entity.domain.Vacancy;
import za.co.RecruitmentZone.entity.Enums.VacancyStatus;
import za.co.RecruitmentZone.events.Vacancy.VacancyActivatedEvent;
import za.co.RecruitmentZone.events.Vacancy.VacancyAmendedEvent;
import za.co.RecruitmentZone.events.Vacancy.VacancyCreateEvent;
import za.co.RecruitmentZone.events.Vacancy.VacancyExpiredEvent;
import za.co.RecruitmentZone.repository.VacancyRepository;

import java.time.LocalDateTime;
import java.util.Optional;

@Transactional
@Component
public class VacancyEventListener {

    private final VacancyRepository vacancyRepository;

    Logger log = LoggerFactory.getLogger(VacancyEventListener.class);

    public VacancyEventListener(VacancyRepository vacancyRepository) {
        this.vacancyRepository = vacancyRepository;
    }

    // NEW VACANCY
    @EventListener
    public void onVacancyCreateEvent(VacancyCreateEvent event) {
        // Your event handling logic

        // Upload the file using the storage controller
        //  MultipartFile file = event.get// Obtain the file to upload
        //eventPublisherService.uploadFile(file);

        try {
            Vacancy v = event.getVacancy();
            v.setStatus(VacancyStatus.PENDING);
            vacancyRepository.save(v);

        } catch (Exception e) {
            log.info("FAILED TO SAVE NEW EVENT");
        }
    }

    // AMEND VACANCY
    @EventListener
    public void onVacancyAmendedEvent(VacancyAmendedEvent event) {
        try {
            // Assuming vacancyId is a field in VacancyActivatedEvent
            Optional<Vacancy> optionalVacancy = vacancyRepository.findById(event.getVacancy().getVacancyID());

            if (optionalVacancy.isPresent()) {
                Vacancy vacancy = optionalVacancy.get();
                vacancy.setTitle(event.getVacancy().getTitle());
                vacancy.setDescription(event.getVacancy().getDescription());
                vacancy.setStatus(event.getVacancy().getStatus());
                vacancy.setIndustry(event.getVacancy().getIndustry());
                LocalDateTime now = LocalDateTime.now();
                if (event.getVacancy().getStartDate().isBefore(now)){
                    vacancy.setStatus(VacancyStatus.ACTIVE);
                }
                vacancy.setEndDate(event.getVacancy().getEndDate());
                vacancyRepository.save(vacancy);

            } else {
                log.info("No vacancy found with ID {}.", event.getVacancy().getVacancyID());
            }
        } catch (Exception e) {
            log.error("FAILED VACANCY ACTIVATION: {}", e.getMessage(), e);
        }
    }

    // ACTIVATE VACANCY
    @EventListener
    public void onVacancyActivatedEvent(VacancyActivatedEvent event) {
        try {
            // Assuming vacancyId is a field in VacancyActivatedEvent
            Optional<Vacancy> optionalVacancy = vacancyRepository.findById(event.getVacancy().getVacancyID());

            if (optionalVacancy.isPresent()) {
                Vacancy vacancy = optionalVacancy.get();
                if (vacancy.isActive()) {
                    // If the vacancy is already active, no need to update it.
                    log.info("Vacancy with ID {} is already active. Skipping activation.", vacancy.getVacancyID());
                } else {
                    // The vacancy is not active; your code to set it to active goes here.
                    vacancy.setStatus(VacancyStatus.ACTIVE);
                    vacancyRepository.save(vacancy);
                    log.info("Vacancy with ID {} is now activated.", vacancy.getVacancyID());
                }
            } else {
                log.info("No vacancy found with ID {}.", event.getVacancy().getVacancyID());
            }
        } catch (Exception e) {
            log.error("FAILED VACANCY ACTIVATION: {}", e.getMessage(), e);
        }
    }

    // EXPIRE VACANCY
    @EventListener
    public void onVacancyExpiredEvent(VacancyExpiredEvent event) {
        // Perform actions when a vacancy expires
        try {
            // Assuming vacancyId is a field in VacancyActivatedEvent
            Optional<Vacancy> optionalVacancy = vacancyRepository.findById(event.getVacancy().getVacancyID());

            if (optionalVacancy.isPresent()) {
                Vacancy vacancy = optionalVacancy.get();
                if (!(vacancy.getStatus() == VacancyStatus.EXPIRED)) {
                    vacancy.setStatus(VacancyStatus.EXPIRED);
                    vacancyRepository.save(vacancy);
                    log.info("Vacancy with ID {} is set to expired.", vacancy.getVacancyID());
                } else {

                    log.info("Vacancy with ID {} is now activated.", vacancy.getVacancyID());
                }
            } else {
                log.info("No vacancy found with ID {}.", event.getVacancy().getVacancyID());
            }
        } catch (Exception e) {
            log.error("FAILED {}", e.getMessage());
        }
    }




}
*/
