package za.co.RecruitmentZone.service;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import za.co.RecruitmentZone.entity.Vacancy;
import za.co.RecruitmentZone.entity.VacancyDTO;
import za.co.RecruitmentZone.entity.VacancyStatus;
import za.co.RecruitmentZone.publisher.VacancyEventPublisher;
import za.co.RecruitmentZone.repository.VacancyRepository;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static java.time.Month.DECEMBER;

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



    public Vacancy createVacancy(Vacancy vacancy) {
        // Create a new Vacancy entity and populate it from the DTO
        Vacancy newVacancy = new Vacancy();
        newVacancy.setTitle(vacancy.getTitle());
        newVacancy.setDescription(vacancy.getDescription());
        newVacancy.setIndustry(vacancy.getIndustry());
        LocalDateTime startDate = LocalDateTime.parse("01-12-2023");
        LocalDateTime endDate = LocalDateTime.parse("31-01-2024");
        newVacancy.setStartDate(startDate);
        newVacancy.setEndDate(endDate);
        // Additional fields like 'active', 'pending', etc. can be set here if required.
if (startDate.isBefore(LocalDateTime.now()))
{
    newVacancy.setStatus(VacancyStatus.ACTIVE);
}
else {
    newVacancy.setStatus(VacancyStatus.PENDING);
}
        // Save the new Vacancy entity to the database
        return vacancyRepository.save(newVacancy);
    }

    public Vacancy updateVacancy(Integer id, VacancyDTO vacancyDTO) {
        // Find the existing Vacancy entity by ID
        Optional<Vacancy> optionalVacancy = vacancyRepository.findById(id);

        if (optionalVacancy.isPresent()) {
            Vacancy existingVacancy = optionalVacancy.get();

            // Update the existing Vacancy entity from the DTO
            existingVacancy.setTitle(vacancyDTO.getTitle());
            existingVacancy.setDescription(vacancyDTO.getDescription());
            existingVacancy.setIndustry(vacancyDTO.getIndustry());

            // Save the updated Vacancy entity to the database
            return vacancyRepository.save(existingVacancy);
        } else {
            return null;
        }
    }


    public void updateStatus(Integer id, VacancyStatus newStatus) throws Exception {
        Vacancy vacancy = vacancyRepository.findById(id)
                .orElseThrow(() -> new Exception("Vacancy not found"));
        vacancy.setStatus(newStatus);
        vacancyRepository.save(vacancy);
    }
    public void updateDetails(Integer id, VacancyDTO vacancyDTO) throws Exception {
        Vacancy existingVacancy = vacancyRepository.findById(id)
                .orElseThrow(() -> new Exception("Vacancy not found"));

        existingVacancy.setTitle(vacancyDTO.getTitle());
        existingVacancy.setDescription(vacancyDTO.getDescription());
        existingVacancy.setIndustry(vacancyDTO.getIndustry());



        vacancyRepository.save(existingVacancy);
    }

    }
