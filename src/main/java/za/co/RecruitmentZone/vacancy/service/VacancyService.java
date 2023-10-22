package za.co.RecruitmentZone.vacancy.service;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import za.co.RecruitmentZone.vacancy.events.VacancyCreateEvent;
import za.co.RecruitmentZone.vacancy.publisher.VacancyEventPublisher;
import za.co.RecruitmentZone.vacancy.repository.VacancyRepository;
import za.co.RecruitmentZone.vacancy.util.Vacancy;
import za.co.RecruitmentZone.vacancy.util.VacancyStatus;

import java.util.List;

@Component
public class VacancyService {

    VacancyRepository vacancyRepository;
    VacancyEventPublisher vacancyEventPublisher;
    Logger log = LoggerFactory.getLogger(VacancyService.class);

    public VacancyService(VacancyRepository vacancyRepository, VacancyEventPublisher vacancyEventPublisher) {
        this.vacancyRepository = vacancyRepository;
        this.vacancyEventPublisher = vacancyEventPublisher;
    }
    public List<Vacancy> findAllVacancies() {
        return vacancyRepository.findAllVacancies();
    }


    public boolean publishVacancyCreateEvent(String json) {
        boolean result = false;
        try {
            Gson gson = new Gson();
            Vacancy newVacancy = gson.fromJson(json, Vacancy.class);
            log.info("User Saved");
            result = vacancyEventPublisher.publishVacancyCreateEvent(newVacancy);

        } catch (Exception e) {
            log.info("Unable to save" + e.getMessage());
        }
        return result;
    }


    public boolean publishAmendedVacancyEvent(String json) {
        boolean result = false;
        try {
            Gson gson = new Gson();
            Vacancy newVacancy = gson.fromJson(json, Vacancy.class);
            log.info("User Saved");
            result = vacancyEventPublisher.publishVacancyAmendedEvent(newVacancy);

        } catch (Exception e) {
            log.info("Unable to save" + e.getMessage());
        }
        return result;
    }


    public boolean publishVacancyActivatedEvent(Integer vacancyID){
        boolean result = false;
        result = vacancyEventPublisher.publishVacancyActivatedEvent(vacancyID);
        return true;
    }



    public boolean publishVacancyExpiredEvent(){
        return true;
    }

}
