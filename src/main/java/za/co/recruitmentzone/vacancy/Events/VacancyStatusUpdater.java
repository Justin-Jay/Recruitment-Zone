package za.co.recruitmentzone.vacancy.Events;


import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import za.co.recruitmentzone.util.enums.VacancyStatus;
import za.co.recruitmentzone.vacancy.entity.Vacancy;
import za.co.recruitmentzone.vacancy.service.VacancyService;

import java.time.LocalDate;
import java.util.List;


@EnableScheduling
public class VacancyStatusUpdater {

    private final VacancyService vacancyService;

    public VacancyStatusUpdater(VacancyService vacancyService) {
        this.vacancyService = vacancyService;
    }

    @Scheduled(cron = "0 0 0 * * ?")
    public void activateVacancyStatus() {

        List<Vacancy> vacancyList = vacancyService.getAllVacancies();
        LocalDate today = LocalDate.now();

        for (Vacancy vacancy : vacancyList) {

            if (vacancy.getStatus() == VacancyStatus.ACTIVE){
                // check what should be EXPIRED
                if (vacancy.getEnd_date().isBefore(today)) {
                    vacancy.setStatus(VacancyStatus.EXPIRED);
                    vacancyService.save(vacancy);
                }
            }


            if (vacancy.getStatus() == VacancyStatus.PENDING) {
                // check what should be ACTIVE
                if (vacancy.getPublish_date().isAfter(today)
                        || vacancy.getPublish_date().isEqual(today)) {
                    vacancy.setStatus(VacancyStatus.ACTIVE);
                    vacancyService.save(vacancy);
                }
            }


        }

    }


}

