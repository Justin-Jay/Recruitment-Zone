package za.co.RecruitmentZone.recruitmentManager.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import za.co.RecruitmentZone.admin.controller.AdminController;
import za.co.RecruitmentZone.recruitmentManager.RecruitmentManagerService;

@Controller
@RequestMapping("/recruitment-managers")
@CrossOrigin("*")
class RecruitmentManagerController {

    private final RecruitmentManagerService recruitmentManagerService;
    Logger log = LoggerFactory.getLogger(AdminController.class);

    @Autowired
    public RecruitmentManagerController(RecruitmentManagerService recruitmentManagerService) {
        this.recruitmentManagerService = recruitmentManagerService;
    }

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

    // Add other controller methods for recruitment manager management as needed
}

