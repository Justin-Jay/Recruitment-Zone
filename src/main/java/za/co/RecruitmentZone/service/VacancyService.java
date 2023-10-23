package za.co.RecruitmentZone.service;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import za.co.RecruitmentZone.entity.Vacancy;
import za.co.RecruitmentZone.entity.VacancyStatus;
import za.co.RecruitmentZone.publisher.VacancyEventPublisher;
import za.co.RecruitmentZone.repository.VacancyRepository;

import java.util.List;
import java.util.Optional;

@Service
public class VacancyService {

    private VacancyRepository vacancyRepository;

    private VacancyEventPublisher vacancyEventPublisher;

    private final Logger log = LoggerFactory.getLogger(VacancyService.class);


    // Service layer takes in String json objects from controller and converts to POJO before passing to vacancyEventPublisher
    // any repository methods are done here.

    // repository methods
    public List<Vacancy> getActiveVacancies() {
        return vacancyRepository.findByActiveTrue();
    }


    public void updateVacancyDescription(Integer vacancyID, String description) {  // Changed from Long to Integer
        Vacancy vacancy = vacancyRepository.findById(vacancyID).orElseThrow();
        vacancy.setDescription(description);
        vacancyRepository.save(vacancy);
    }


    // update the vacancy status to expired using repository

    public boolean setVacancyStatusToExpired(Integer vacancyID) {
        Optional<Vacancy> optionalVacancy = vacancyRepository.findById(vacancyID);
        if (optionalVacancy.isPresent()) {
            Vacancy vacancy = optionalVacancy.get();
            vacancy.setStatus(VacancyStatus.EXPIRED);
            vacancyRepository.save(vacancy);
            return true;
        } else {
            // handle the case where the vacancy is not found
            return false;
        }
    }


    // json conversion methods - create events
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


    }
