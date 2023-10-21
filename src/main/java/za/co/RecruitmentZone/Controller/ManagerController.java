package za.co.RecruitmentZone.Controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/manager")
@CrossOrigin("*")
public class ManagerController {
    private static final Logger log = LoggerFactory.getLogger(ManagerController.class);


    ManagerEventService managerEventService;

    public ManagerController(ManagerEventService managerEventService) {
        this.managerEventService = managerEventService;
    }
    // Add Recruiter


    @PostMapping("/createRecruiter")
    public ResponseEntity<String> createRecruiter(@RequestBody String json) {

        try {
            managerEventService.createRecruiter(json);
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
            managerEventService.amendRecruiter(json);
            return new ResponseEntity<>("Recruiter Added Successfully", HttpStatus.OK);
        }catch (Exception e) {
            log.info(e.getMessage());
            return new ResponseEntity<>("Error adding vacancy: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }



    // Suspend Recruiter



    @PostMapping("/suspendRecruiter/{recruiterID}")
    public ResponseEntity<String> suspendRecruiter(@PathVariable Integer recruiterID ) {

        try {
            managerEventService.suspendRecruiter(recruiterID);
            return new ResponseEntity<>("Recruiter Suspended successfully", HttpStatus.OK);
        }catch (Exception e) {
            log.info(e.getMessage());
            return new ResponseEntity<>("Failed to suspend Recruiter" + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }



}
