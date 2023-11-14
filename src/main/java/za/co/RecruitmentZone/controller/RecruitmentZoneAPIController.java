/*
package za.co.RecruitmentZone.controller;

import com.google.gson.Gson;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import za.co.RecruitmentZone.entity.*;

import java.util.List;

@Controller
@RequestMapping("/RecruitmentZone/api")
@CrossOrigin("*")
public class RecruitmentZoneAPIController {

    @GetMapping("/view-vacancies")
    public String viewActiveVacancies(Model model) {
        List<Vacancy> vacancies = vacancyService.getActiveVacancies();
        model.addAttribute("vacancies", vacancies);
        return "vacancies";
    }
    @GetMapping("/vacancies")
    public ResponseEntity<List<Vacancy>> getActiveVacancies() {
        List<Vacancy> vacancies = vacancyService.getActiveVacancies();
        if (vacancies.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(vacancies, HttpStatus.OK);
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

    @PutMapping("/updateVacancyDetails/{id}")
    public ResponseEntity<Vacancy> updateVacancyDetails(@PathVariable Integer id, @RequestBody VacancyDTO vacancyDTO) {
        try {
            vacancyService.updateDetails(id, vacancyDTO);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PostMapping("/submitApplication")
    public String submitApplication(@Valid @ModelAttribute Application application,
                                    BindingResult bindingResult,
                                    @RequestParam("file") MultipartFile file,
                                    RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            return "applicationForm";
        }

        try {
            String storedFileName = fileService.uploadFile(file);
            if (storedFileName == null) {
                redirectAttributes.addFlashAttribute("message", "File upload failed.");
                return "redirect:/uploadStatus";
            }

            application.setCvFilePath(storedFileName);
            Gson gson = new Gson();
            String json = gson.toJson(application);
            candidateService.publishCandidateAppliedEvent(json);

        } catch (Exception e) {
            log.info("FAILED TO POST EVENT " + e.getMessage());
        }

        return "redirect:/vacancies";
    }


    // webView
    @GetMapping("/view-vacancies")
    public String viewActiveVacancies(Model model) {
        // Retrieve and display a list of vacancies
        List<Vacancy> vacancies = vacancyService.getActiveVacancies();
        model.addAttribute("vacancies", vacancies);
        return "vacancies";
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

    @PutMapping("/updateVacancyDetails/{id}")
    public ResponseEntity<Vacancy> updateVacancyDetails(@PathVariable Integer id, @RequestBody VacancyDTO vacancyDTO) {
        try {
            vacancyService.updateDetails(id, vacancyDTO);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    // Web-view method to show a vacancy creation form
    @GetMapping("/create")
    public String showCreateVacancyForm(Model model) {
        model.addAttribute("vacancy", new Vacancy());
        return "create-vacancy";
    }

    // Web-view method to create a new vacancy
    @PostMapping("/create")
    public String createVacancy(@Valid @ModelAttribute Vacancy vacancy, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "create-vacancy";
        }
        vacancyService.createVacancy(vacancy);
        return "redirect:/vacancies";
    }

    // Web-view method to show a list of vacancies
    @GetMapping("/view-recruiter-vacancies")
    public String viewRecruiterVacancies(Model model) {
        List<Vacancy> vacancies = vacancyService.getActiveVacancies();
        model.addAttribute("vacancies", vacancies);
        return "list-vacancies";
    }

    // Web-view method to update a vacancy
    @PostMapping("/update-vacancy-details")
    public String amendVacancy(@Valid @ModelAttribute VacancyDTO vacancyDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "update-vacancy";
        }
        vacancyService.updateVacancy(vacancyDTO);
        return "redirect:/vacancies";
    }

    // Web-view method to expire a vacancy
    @PostMapping("/update-vacancy-status/{vacancyID}")
    public String expireVacancy(@PathVariable Integer vacancyID) {
        vacancyService.setVacancyStatusToExpired(vacancyID);
        return "redirect:/vacancies";
    }


    // Folder where uploaded files will be stored
    @PostMapping("/submitApplication")
    public String submitApplication(@Valid @ModelAttribute Application application,
                                    BindingResult bindingResult,
                                    @RequestParam("file") MultipartFile file,
                                    RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            return "applicationForm";
        }

        // Additional validations like checking for already submitted application, etc.

        try {
            // Attempt to store the file securely
            String storedFileName = fileService.uploadFile(file);

            if (storedFileName == null) {
                redirectAttributes.addFlashAttribute("message", "File upload failed.");
                return "redirect:/uploadStatus";
            }

            application.setCvFilePath(storedFileName); // Save the stored file name in the application object

            // Other logic to process the application, such as publishing an event
            Gson gson = new Gson();
            String json = gson.toJson(application);
            candidateService.publishCandidateAppliedEvent(json);
        } catch (Exception e) {
            log.info("FAILED TO POST EVENT " + e.getMessage());
        }

        return "redirect:/vacancies";
    }



    @PostMapping("/createUser")
    public String createUser(@Valid @ModelAttribute("user") UserDTO userDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "errorPage";
        }

        try {
            applicationUserService.createNewUser(userDto);
            return "successPage";
        } catch (Exception e) {
            log.error("Error Creating User: ", e);
            return "errorPage";
        }
    }

    @PostMapping("/createRecruiter")
    public String createRecruiter(@Valid @ModelAttribute("user") UserDTO userDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "errorPage";
        }

        try {
            applicationUserService.publishApplicationUserCreateEvent(userDto);
            return "successPage";
        } catch (Exception e) {
            log.error("Error Adding Recruiter: ", e);
            return "errorPage";
        }
    }

    @PostMapping("/updateUser")
    public String updateUser(@Valid @ModelAttribute("user") UserDTO userDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "errorPage";
        }

        try {
            applicationUserService.updateUser(userDto);
            return "successPage";
        } catch (Exception e) {
            log.error("Error Updating User: ", e);
            return "errorPage";
        }
    }

    // restAPI
    @GetMapping("/vacancies")
    public ResponseEntity<List<Vacancy>> restActiveVacancies() {
        List<Vacancy> vacancies = vacancyService.getActiveVacancies();

        if (vacancies.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(vacancies, HttpStatus.OK);
    }


    @PostMapping("/createUser")
    public ResponseEntity<String> createUser(@RequestBody User user) {
        try {
            applicationUserService.createNewRestUser(user);
            return new ResponseEntity<>("User Created Successfully", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error Creating User: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/updateUser/{id}")
    public ResponseEntity<String> updateUser(@PathVariable("id") Integer id, @RequestBody UserDTO userDto) {
        try {
            applicationUserService.updateUser(id, userDto);
            return new ResponseEntity<>("User Updated Successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error Updating User: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/updateUserAuthorities/{id}")
    public ResponseEntity<String> updateUserAuthorities(@PathVariable("id") Integer id, @RequestBody AuthoritiesDTO authoritiesDto) {
        try {
            applicationUserService.updateUserAuthorities(id, authoritiesDto.getRoles());
            return new ResponseEntity<>("User Authorities Updated Successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error Updating User Authorities: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
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

*/
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
*//*


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


    @PostMapping("/view-vacancies")
    public ResponseEntity<String> viewVacancy(){

        boolean succsss = true;

        if (succsss)
        {
            return new ResponseEntity<>("User Added Successfully", HttpStatus.OK);

        }

        else return new ResponseEntity<>("Error adding user: ", HttpStatus.INTERNAL_SERVER_ERROR);

    }
    // API method to create a new vacancy

    @PostMapping("/createVacancyREST")
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
  */
/*  @GetMapping("/create")
    public String showCreateVacancyForm(Model model) {
        // Create a new Vacancy object and add it to the model
        // This object will hold the form input
        model.addAttribute("vacancy", new Vacancy());

        // You can add more attributes to the model if needed
        // model.addAttribute("attributeName", attributeValue);

        // Return the view name for the add-vacancy form
        return "create-vacancy";
    }

    @PostMapping("/create")
    public String createVacancy(@Valid @ModelAttribute Vacancy vacancy, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            // Validation errors found. You can return to the form page or handle errors as appropriate.
            return "vacancyForm"; // replace with your form view name
        }

        try {
            // Process form submission to add a new vacancy

            Gson gson = new Gson();
            String json = gson.toJson(vacancy);

            vacancyService.publishVacancyCreateEvent(json);

        } catch (Exception e) {
            log.info("FAILED TO POST EVENT " + e.getMessage());
        }
        return "redirect:/vacancies";
    }
    // get the vacancies according to the ID of the recruiter
    @GetMapping("/view-recruiter-vacancies")
    public String viewRecruiterVacancies(Model model) {
        // Retrieve and display a list of vacancies
        List<Vacancy> vacancies = vacancyService.getActiveVacancies();
        model.addAttribute("vacancies", vacancies);
        return "list-vacancies";
    }

    @PostMapping("/update-vacancy-details")
    public String AmendVacancy(@Valid @ModelAttribute VacancyDTO vacancyDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            // Validation errors found. You can return to the form page or handle errors as appropriate.
            return "vacancyForm"; // replace with your form view name
        }

        try {
            // Process form submission to add a new vacancy

            Gson gson = new Gson();
            String json = gson.toJson(vacancyDTO);

            vacancyService.publishAmendedVacancyEvent(json);

        } catch (Exception e) {
            log.info("FAILED TO AMEND EVENT " + e.getMessage());
        }
        return "redirect:/vacancies";
    }


    @PostMapping("/update-vacancy-status/{vacancyID}")
    public String expireVacancy(@PathVariable Integer vacancyID) {
        // Retrieve and display a list of vacancies
        vacancyService.setVacancyStatusToExpired(vacancyID);
        //model.addAttribute("vacancies", vacancies);
        return "redirect:/vacancies";
    }

*//*

}
*/
