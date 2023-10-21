package za.co.RecruitmentZone.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@CrossOrigin("*")
public class AdminController {

    @GetMapping("/")
    public String helloAdminController(){
        return "Admin access level.";
    }

    // Admin Task 1

    @PostMapping("/suspendRecruiter/{recruiterID}")
    public ResponseEntity<String> createApplicationUser(@PathVariable Integer recruiterID ) {

        try {
            managerEventService.suspendRecruiter(recruiterID);
            return new ResponseEntity<>("Recruiter Suspended successfully", HttpStatus.OK);
        }catch (Exception e) {
            log.info(e.getMessage());
            return new ResponseEntity<>("Failed to suspend Recruiter" + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


    // Admin Task 2
    // Admin Tsk 3
}
