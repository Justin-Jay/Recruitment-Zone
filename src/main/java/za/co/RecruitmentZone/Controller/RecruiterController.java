package za.co.RecruitmentZone.Controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.co.RecruitmentZone.Vacancy.VacancyManagement;

@RestController
@RequestMapping("/recruiter")
@CrossOrigin("*")
public class RecruiterController {
    private static final Logger log = LoggerFactory.getLogger(RecruiterController.class);

    // Add Vacancy


    VacancyManagement recruiterEventService;

    public RecruiterController(VacancyManagement recruiterEventService) {
        this.recruiterEventService = recruiterEventService;
    }

    @PostMapping("/addVacancy")
    public ResponseEntity<String> addVacancy(@RequestBody String json) {

        try {
            recruiterEventService.addVacancy(json);
            return new ResponseEntity<>("Vacancy created successfully", HttpStatus.OK);
        }catch (Exception e) {
            log.info(e.getMessage());
            return new ResponseEntity<>("Error adding vacancy: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


    // Post Vacancy

    @PostMapping("/postVacancy/{vacancyID}")
    public ResponseEntity<String> activateVacancy(@PathVariable Integer vacancyID) {

        try {
            recruiterEventService.activateVacancy(vacancyID);
            return new ResponseEntity<>("Vacancy created successfully", HttpStatus.OK);
        }catch (Exception e) {
            log.info(e.getMessage());
            return new ResponseEntity<>("Error adding vacancy: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }



    // Suspend Vacancy
    @PostMapping("/suspendVacancy/{vacancyID}")
    public ResponseEntity<String> suspendVacancy(@PathVariable Integer vacancyID) {

        try {
            recruiterEventService.suspendVacancy(vacancyID);
            return new ResponseEntity<>("Vacancy suspended successfully", HttpStatus.OK);
        }catch (Exception e) {
            log.info(e.getMessage());
            return new ResponseEntity<>("Error suspending vacancy: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    // Amend Vacancy

    @PostMapping("/amendVacancy")
    public ResponseEntity<String> amendVacancy(@RequestBody String json) {

        try {
            recruiterEventService.amendVacancy(json);
            return new ResponseEntity<>("Vacancy amended successfully", HttpStatus.OK);
        }catch (Exception e) {
            log.info(e.getMessage());
            return new ResponseEntity<>("Error amending vacancy: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    // View Applications

    @GetMapping("/viewApplications/{vacancyID}")
    public ResponseEntity<String> viewApplications(@PathVariable Integer vacancyID) {

        try {
            // return a list of vacancies  or a join table that shows the applications received for any given vacancy
            // return null if none
            //recruiterEventService.amendVacancy(json);
            // return a MODEL ?!
            return new ResponseEntity<>("Vacancy amended successfully", HttpStatus.OK);
        }catch (Exception e) {
            log.info(e.getMessage());
            return new ResponseEntity<>("Error amending vacancy: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    // Reject candidate

    @PostMapping("/rejectCandidate/{candidateID}")
    public ResponseEntity<String> rejectCandidate(@PathVariable Integer candidateID) {

        try {
            recruiterEventService.rejectCandidate(candidateID);
            return new ResponseEntity<>("Vacancy amended successfully", HttpStatus.OK);
        }catch (Exception e) {
            log.info(e.getMessage());
            return new ResponseEntity<>("Error amending vacancy: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    // Short list candidate

    @PostMapping("/shortList/{candidateID}/{vacancyID}")
    public ResponseEntity<String> shortList(@PathVariable Integer candidateID,@PathVariable Integer vacancyID) {
        try {
            recruiterEventService.shortList(candidateID,vacancyID);
            return new ResponseEntity<>("Added to the short list", HttpStatus.OK);
        }catch (Exception e) {
            log.info(e.getMessage());
            return new ResponseEntity<>("Failed to short list" + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    // Remove candidate from short list

    @PostMapping("/removeFromShortList/{candidateID}/{vacancyID}")
    public ResponseEntity<String> removeFromShortList(@PathVariable Integer candidateID,@PathVariable Integer vacancyID) {
        try {
            recruiterEventService.removeFromShortList(candidateID,vacancyID);
            return new ResponseEntity<>("Removed From ShortList", HttpStatus.OK);
        }catch (Exception e) {
            log.info(e.getMessage());
            return new ResponseEntity<>("Failed to remove from short list" + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }



}
