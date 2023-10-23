package za.co.RecruitmentZone.controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.co.RecruitmentZone.entity.Vacancy;
import za.co.RecruitmentZone.entity.VacancyDTO;
import za.co.RecruitmentZone.service.VacancyService;

@RestController
@RequestMapping("/api/user/recruiter")
@CrossOrigin("*")
public class VacancyApiController {

    private final VacancyService vacancyService;
    private final Logger log = LoggerFactory.getLogger(VacancyApiController.class);

    public VacancyApiController(VacancyService vacancyService) {
        this.vacancyService = vacancyService;
    }

    // Example API method
    @PostMapping("/view-vacancies")
    public ResponseEntity<String> viewVacancy() {
        return new ResponseEntity<>("User Added Successfully", HttpStatus.OK);
    }

    // API method to create a new vacancy
    @PostMapping("/createVacancy")
    public ResponseEntity<Vacancy> createVacancy(@RequestBody Vacancy vacancy) {
        Vacancy newVacancy = vacancyService.createVacancy(vacancy);
        if (newVacancy != null) {
            return new ResponseEntity<>(newVacancy, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // API method to update an existing vacancy
    @PutMapping("/updateVacancy/{id}")
    public ResponseEntity<Vacancy> updateVacancy(@PathVariable("id") Integer id, @RequestBody VacancyDTO vacancyDTO) {
        Vacancy updatedVacancy = vacancyService.updateVacancy(id, vacancyDTO);
        if (updatedVacancy != null) {
            return new ResponseEntity<>(updatedVacancy, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
