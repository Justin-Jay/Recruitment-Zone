package za.co.RecruitmentZone.controller;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import za.co.RecruitmentZone.entity.Vacancy;
import za.co.RecruitmentZone.entity.VacancyDTO;
import za.co.RecruitmentZone.service.VacancyService;
import jakarta.validation.Valid;

import java.util.List;

@Controller
@RequestMapping("/api/vacancies")
@CrossOrigin("*")
public class VacancyController {

    VacancyService vacancyService;
    Logger log = LoggerFactory.getLogger(AdminController.class);

    public VacancyController(VacancyService vacancyService) {
        this.vacancyService = vacancyService;
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


    @GetMapping("/list-active-vacancies")
    public String listVacancies(Model model) {
        // Retrieve and display a list of vacancies
        List<Vacancy> vacancies = vacancyService.getActiveVacancies();
        model.addAttribute("vacancies", vacancies);
        return "list-vacancies";
    }


    @PostMapping("/expire-vacancy/{vacancyID}")
    public String expireVacancy(@PathVariable Integer vacancyID) {
        // Retrieve and display a list of vacancies
        vacancyService.setVacancyStatusToExpired(vacancyID);
        //model.addAttribute("vacancies", vacancies);
        return "redirect:/vacancies";
    }




}

