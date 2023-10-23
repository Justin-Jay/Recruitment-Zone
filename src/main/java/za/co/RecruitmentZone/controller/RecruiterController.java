package za.co.RecruitmentZone.controller;

import com.google.gson.Gson;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import za.co.RecruitmentZone.entity.Vacancy;
import za.co.RecruitmentZone.entity.VacancyDTO;
import za.co.RecruitmentZone.service.EventOrchestration.RecruiterService;
import za.co.RecruitmentZone.service.VacancyService;

import java.util.List;


@Controller
@RequestMapping("/user/recruiter")
@CrossOrigin("*")
class RecruiterController {

    private final VacancyService vacancyService;
    private final Logger log = LoggerFactory.getLogger(RecruiterController.class);

    public RecruiterController(VacancyService vacancyService) {
        this.vacancyService = vacancyService;
    }

    @GetMapping("/add")
    public String showAddRecruiterForm(Model model) {
        // Display a form to add a new recruiter
        // You can add necessary attributes to the model
        return "add-recruiter";
    }

    @GetMapping("/create")
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

    @PostMapping("/amendVacancy")
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


    @PostMapping("/expire-vacancy/{vacancyID}")
    public String expireVacancy(@PathVariable Integer vacancyID) {
        // Retrieve and display a list of vacancies
        vacancyService.setVacancyStatusToExpired(vacancyID);
        //model.addAttribute("vacancies", vacancies);
        return "redirect:/vacancies";
    }


}

