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





/*    @GetMapping("/addVacancy")
    public ResponseEntity<Vacancy> createVacancy(@RequestBody VacancyFormData vacancy) {
        Vacancy newVacancy = vacancyService.createVacancy(vacancy);
        if (newVacancy != null) {
            return new ResponseEntity<>(newVacancy, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }*/
    // findAllVacancies @PathVariable Integer id,


    // findAllVacancies @PathVariable Integer id,
    @GetMapping("/getAllVacancies")
    public ResponseEntity<List<Vacancy>> getAllVacancies() {
        List<Vacancy> vacancies = vacancyService.getAllVacancies();
        if (!vacancies.isEmpty()){
            return new ResponseEntity<>(vacancies,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/deleteVacancy/{id}")
    public ResponseEntity<String> deleteVacancy(@PathVariable Integer id) {
        boolean vacancyDeleted = vacancyService.deleteVacancy(id);
        if (vacancyDeleted) {
            return new ResponseEntity<>("Vacancy Deleted", HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/updateVacancyStatus/{id}/status")
    public ResponseEntity<Vacancy> updateVacancyStatus(@PathVariable Integer id, @RequestBody VacancyStatus newStatus) {
        try {
            vacancyService.updateStatus(id, newStatus);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

/*    @PutMapping("/updateVacancyDetails/{id}")
    public ResponseEntity<Vacancy> updateVacancyDetails(@PathVariable Integer id, @RequestBody VacancyDTO vacancyDTO) {
        try {
            vacancyService.updateDetails(id, vacancyDTO);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }*/

    // ADMIN
    /*
    * ADD USER
    * VIEW USERS
    * UPDATE USER
    * */
/*    @PostMapping("/createUser")
    public ResponseEntity<String> createUser(@RequestBody Employee user) {
        try {
            applicationUserService.createNewRestUser(user);
            return new ResponseEntity<>("User Created Successfully", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error Creating User: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }*/
/*    @PutMapping("/updateUser/{id}")
    public ResponseEntity<String> updateUser(@PathVariable("id") Integer id, @RequestBody UserDTO userDto) {
        try {
            applicationUserService.updateUser(id, userDto);
            return new ResponseEntity<>("User Updated Successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error Updating User: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }*/


    /*@PutMapping("/updateUserAuthorities/{id}")
    public ResponseEntity<String> updateUserAuthorities(@PathVariable("id") Integer id, @RequestBody AuthoritiesDTO authoritiesDto) {
        try {
            applicationUserService.updateUserAuthorities(id, authoritiesDto.getRoles());
            return new ResponseEntity<>("User Authorities Updated Successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error Updating User Authorities: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
*/


}
