package za.co.RecruitmentZone.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import za.co.RecruitmentZone.service.RecruitmentManagerService;

@Controller
@RequestMapping("/recruitment-managers")
@CrossOrigin("*")
class RecruitmentManagerController {
    private final RecruitmentManagerService eventManagement;
    private final Logger log = LoggerFactory.getLogger(RecruitmentManagerController.class);

    public RecruitmentManagerController(RecruitmentManagerService eventManagement) {
        this.eventManagement = eventManagement;
    }

    @PostMapping("/suspendRecruiter/{recruiterID}")
    public ResponseEntity<String> suspendRecruiter(@PathVariable Integer recruiterID ) {
        try {
            eventManagement.publishRecruiterSuspendedEvent(recruiterID);
            return new ResponseEntity<>("Recruiter Suspended successfully", HttpStatus.OK);
        }catch (Exception e) {
            log.info(e.getMessage());
            return new ResponseEntity<>("Failed to suspend Recruiter" + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PostMapping("/createRecruiter")
    public ResponseEntity<String> createRecruiter(@RequestBody String json) {
        try {
            eventManagement.publishRecruiterCreateEvent(json);
            return new ResponseEntity<>("Recruiter Added Successfully", HttpStatus.OK);
        }catch (Exception e) {
            log.info(e.getMessage());
            return new ResponseEntity<>("Error adding vacancy: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    // Amend Recruiter details
    @PostMapping("/amendRecruiter")
    public ResponseEntity<String> amendRecruiter(@RequestBody String json) {
        try {
            eventManagement.publishRecruiterAmendEvent(json);
            return new ResponseEntity<>("Recruiter Amended Successfully", HttpStatus.OK);
        }catch (Exception e) {
            log.info(e.getMessage());
            return new ResponseEntity<>("Error adding vacancy: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }



    // Add other controller methods for recruitment manager management as needed
}

