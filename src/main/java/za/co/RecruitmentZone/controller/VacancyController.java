package za.co.RecruitmentZone.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import za.co.RecruitmentZone.entity.Vacancy;
import za.co.RecruitmentZone.entity.VacancyDTO;
import za.co.RecruitmentZone.service.VacancyService;

import java.util.List;


@Controller
@RequestMapping("/user/recruiter")
@CrossOrigin("*")
public class VacancyController {

    private final VacancyService vacancyService;
    private final Logger log = LoggerFactory.getLogger(VacancyController.class);

    public VacancyController(VacancyService vacancyService) {
        this.vacancyService = vacancyService;
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

*/
}

