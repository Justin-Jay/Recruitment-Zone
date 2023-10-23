package za.co.RecruitmentZone.controller;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.co.RecruitmentZone.entity.AuthoritiesDTO;
import za.co.RecruitmentZone.entity.User;
import za.co.RecruitmentZone.entity.UserDTO; // Assuming this exists
import za.co.RecruitmentZone.service.ApplicationUserService;

import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;

@RestController
@RequestMapping("/admin")
@CrossOrigin("*")
public class AdminController {
    private final Logger log = LoggerFactory.getLogger(AdminController.class);

    private ApplicationUserService applicationUserService;

    public AdminController(ApplicationUserService applicationUserService) {
        this.applicationUserService = applicationUserService;
    }

    // create user REST

    @PostMapping("/createUserRestAPI")
    public ResponseEntity<String> createUserRest(@RequestBody User user) {
        try {
            applicationUserService.createNewRestUser(user); // Assuming createUser is a method in UserService that handles user creation
            return new ResponseEntity<>("User Created Successfully", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error Creating User: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PutMapping("/updateUserRestAPI/{id}")
    public ResponseEntity<String> updateUser(@PathVariable("id") Integer id, @RequestBody UserDTO userDto) {
        try {
            // Call service method to update user details
            applicationUserService.updateUser(id, userDto);
            return new ResponseEntity<>("User updated successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error updating user: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/updateUserAuthorities/{id}")
    public ResponseEntity<String> updateUserAuthorities(@PathVariable("id") Integer id, @RequestBody AuthoritiesDTO authoritiesDto) {
        try {
            // Call service method to update user authorities
            applicationUserService.updateUserAuthorities(id, authoritiesDto.getRoles());
            return new ResponseEntity<>("User authorities updated successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error updating user authorities: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

/*
    @PostMapping("/createUser")
    public ResponseEntity<String> createUser(@Valid @ModelAttribute UserDTO applicationUser, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            // Handle validation errors
            return new ResponseEntity<>("Form validation errors", HttpStatus.BAD_REQUEST);
        }

        try {
            Gson gson = new Gson();
            UserDTO newUser = gson.fromJson(json, UserDTO.class);

            applicationUserService.createNewUser(applicationUser);  // Assuming createNewUser accepts ApplicationUser
            return new ResponseEntity<>("User Added Successfully", HttpStatus.OK);
        } catch (Exception e) {
            log.info(e.getMessage());
            return new ResponseEntity<>("Error adding user: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

   @PostMapping("/createRecruiter")
    public ResponseEntity<String> createRecruiter(@Valid @ModelAttribute UserDTO applicationUser, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            // Handle validation errors
            return new ResponseEntity<>("Form validation errors", HttpStatus.BAD_REQUEST);
        }

        try {
            applicationUserService.publishApplicationUserCreateEvent(applicationUser); // Assuming publishApplicationUserCreateEvent accepts ApplicationUser
            return new ResponseEntity<>("Recruiter Added Successfully", HttpStatus.OK);
        } catch (Exception e) {
            log.info(e.getMessage());
            return new ResponseEntity<>("Error adding recruiter: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/updateApplicationUserDetails")
    public ResponseEntity<String> updateUserDetails(@Valid @ModelAttribute UserDTO applicationUser, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            // Handle validation errors
            return new ResponseEntity<>("Form validation errors", HttpStatus.BAD_REQUEST);
        }

        try {
            Gson gson = new Gson();
            UserDTO newUser = gson.fromJson(json, UserDTO.class);

            applicationUserService.createNewUser(applicationUser);  // Assuming createNewUser accepts ApplicationUser
            return new ResponseEntity<>("User Added Successfully", HttpStatus.OK);
        } catch (Exception e) {
            log.info(e.getMessage());
            return new ResponseEntity<>("Error adding user: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
*/

    // ... rest of the methods unchanged
}
