package za.co.RecruitmentZone.controller.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.co.RecruitmentZone.entity.Enums.VacancyStatus;
import za.co.RecruitmentZone.entity.domain.Vacancy;
import za.co.RecruitmentZone.service.RecruitmentZoneService;

import java.util.List;

@RestController
@RequestMapping("/RecruitmentZone/api")
@CrossOrigin("*")
public class RecruitmentZoneAPIController {

    private final Logger log = LoggerFactory.getLogger(RecruitmentZoneAPIController.class);

    private final RecruitmentZoneService vacancyService;

    public RecruitmentZoneAPIController(RecruitmentZoneService vacancyService) {
        this.vacancyService = vacancyService;
    }

    @GetMapping("/getAllVacancies")
    public ResponseEntity<List<Vacancy>> getAllVacancies() {
        List<Vacancy> vacancies = vacancyService.getAllVacancies();
        if (!vacancies.isEmpty()){
            return new ResponseEntity<>(vacancies,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/deleteVacancy/{id}")
    public ResponseEntity<String> deleteVacancy(@PathVariable Long id) {
        boolean vacancyDeleted = vacancyService.deleteVacancy(id);
        if (vacancyDeleted) {
            return new ResponseEntity<>("Vacancy Deleted", HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }



}
