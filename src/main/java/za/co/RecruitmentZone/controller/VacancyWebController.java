package za.co.RecruitmentZone.controller;

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
@RequestMapping("/web/user/recruiter")
public class VacancyWebController {

    private final VacancyService vacancyService;
    private final Logger log = LoggerFactory.getLogger(VacancyWebController.class);

    public VacancyWebController(VacancyService vacancyService) {
        this.vacancyService = vacancyService;
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
}
