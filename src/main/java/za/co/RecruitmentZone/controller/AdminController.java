package za.co.RecruitmentZone.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.co.RecruitmentZone.service.ApplicationUserService;

@RestController
@RequestMapping("/admin")
@CrossOrigin("*")
public class AdminController {
    private final Logger log = LoggerFactory.getLogger(AdminController.class);

    private ApplicationUserService applicationUserService;

    public AdminController(ApplicationUserService applicationUserService) {
        this.applicationUserService = applicationUserService;
    }

    @GetMapping("/")
    public String helloAdminController(){
        return "Admin access level.";
    }

    // Add Application User
    // reset Application user password
    // other admin stuff



    @PostMapping("/sendSignUp")
    public ResponseEntity<String> sendSignUpLink(@RequestBody String json) {
        try {
            applicationUserService.publishApplicationUserCreateEvent(json);
            return new ResponseEntity<>("User Added Successfully", HttpStatus.OK);
        }catch (Exception e) {
            log.info(e.getMessage());
            return new ResponseEntity<>("Error adding vacancy: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PostMapping("/createApplicationUser")
    public ResponseEntity<String> createApplicationUser(@RequestBody String json) {
        try {
            applicationUserService.createNewUser(json);
            return new ResponseEntity<>("User Added Successfully", HttpStatus.OK);
        }catch (Exception e) {
            log.info(e.getMessage());
            return new ResponseEntity<>("Error adding vacancy: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

}
