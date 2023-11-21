package za.co.RecruitmentZone.controller.web;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import za.co.RecruitmentZone.entity.domain.Vacancy;
import za.co.RecruitmentZone.service.RecruitmentZoneService;

import java.util.ArrayList;
import java.util.List;

@Controller
@CrossOrigin("*")
public class VacancyController {
    private final RecruitmentZoneService recruitmentZoneService;
    private final Logger log = LoggerFactory.getLogger(VacancyController.class);

    public VacancyController(RecruitmentZoneService recruitmentZoneService) {
        this.recruitmentZoneService = recruitmentZoneService;
    }

    // Vacancies
    @GetMapping("/vacancies")
    public String vacancies(Model model) {
        List<Vacancy> allVacancies = new ArrayList<>();
        try {
            allVacancies = recruitmentZoneService.getAllVacancies();
        } catch (Exception e) {
            log.info("Exception trying to retrieve vacancies, retrieving all vacancies ");
        }
        model.addAttribute("vacancies", allVacancies);
        return "vacancies";
    }

    @GetMapping("/add-vacancy")
    public String showCreateVacancyForm(Model model) {
        model.addAttribute("vacancy", new Vacancy());
        return "fragments/vacancy/add-vacancy";
    }

    @PostMapping("/view-vacancy")
    public String showVacancy(@RequestParam("vacancyID") Long vacancyID, Model model) {
        Vacancy optionalVacancy = recruitmentZoneService.findVacancyById(vacancyID);
        log.info("Looking for {}", vacancyID);
        log.info(optionalVacancy.toString());
        model.addAttribute("vacancy", optionalVacancy);
        return "fragments/vacancy/view-vacancy";
    }
    @PostMapping("/save-vacancy")
    public String saveVacancy(@Valid @ModelAttribute("vacancy")Vacancy vacancy, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "fragments/vacancy/add-vacancy";
        }
        recruitmentZoneService.saveVacancy(vacancy);
        return "redirect:vacancies";
    }
    @PostMapping("/update-vacancy")
    public String updateVacancy(@RequestParam("vacancyID") Long vacancyID, Model model) {
        Vacancy vacancy = recruitmentZoneService.findVacancyById(vacancyID);
        model.addAttribute("vacancy", vacancy);
        return "fragments/vacancy/update-vacancy";
    }

    @PostMapping("/save-updated-vacancy")
    public String saveUpdatedVacancy(@Valid @ModelAttribute("vacancy")Vacancy vacancy, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "fragments/vacancy/update-vacancy";
        }
        recruitmentZoneService.saveVacancy(vacancy);
        return "redirect:vacancies";
    }



}
