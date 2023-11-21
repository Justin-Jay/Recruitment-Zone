package za.co.RecruitmentZone.controller.web;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import za.co.RecruitmentZone.entity.domain.Application;
import za.co.RecruitmentZone.entity.domain.Candidate;
import za.co.RecruitmentZone.entity.domain.Vacancy;
import za.co.RecruitmentZone.entity.domain.VacancySubmission;
import za.co.RecruitmentZone.service.RecruitmentZoneService;

import java.util.ArrayList;
import java.util.List;

@Controller
@CrossOrigin("*")
public class ApplicationsController {
    private final RecruitmentZoneService recruitmentZoneService;
    private final Logger log = LoggerFactory.getLogger(ApplicationsController.class);

    public ApplicationsController(RecruitmentZoneService recruitmentZoneService) {
        this.recruitmentZoneService = recruitmentZoneService;
    }

    // Vacancies
 /*   @GetMapping("/apply-now")
    public String applyNow(Model model) {
        List<Vacancy> allVacancies = new ArrayList<>();
        try {
            allVacancies = recruitmentZoneService.getAllVacancies();
        } catch (Exception e) {
            log.info("Exception trying to retrieve vacancies, retrieving all vacancies ");
        }
        model.addAttribute("vacancies", allVacancies);
        return "vacancies";
    }*/

    @PostMapping("/apply-now")
    public String showVacancyApplicationForm(Model model) {
        model.addAttribute("submission", new VacancySubmission());
        return "fragments/applications/apply-now";
    }

    /*    @PostMapping("/view-vacancy")
        public String showVacancy(@RequestParam("vacancyID") Long vacancyID, Model model) {
            Vacancy optionalVacancy = recruitmentZoneService.findVacancyById(vacancyID);
            log.info("Looking for {}", vacancyID);
            log.info(optionalVacancy.toString());
            model.addAttribute("vacancy", optionalVacancy);
            return "fragments/vacancy/view-vacancy";
        }*/
    @PostMapping("/save-application")
    public String saveSubmission(@Valid @ModelAttribute("submission") VacancySubmission submission, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "fragments/applications/apply-now";
        }
        boolean result = recruitmentZoneService.saveSubmission(submission);
        if (result) {
            redirectAttributes.addAttribute("successMessage", "success");
        }
        return "redirect:/";
    }


/*    @PostMapping("/update-vacancy")
    public String updateVacancy(@RequestParam("vacancyID") Long vacancyID, Model model) {
        Vacancy vacancy = recruitmentZoneService.findVacancyById(vacancyID);
        model.addAttribute("vacancy", vacancy);
        return "fragments/vacancy/update-vacancy";
    }*/

 /*   @PostMapping("/save-updated-vacancy")
    public String saveUpdatedVacancy(@Valid @ModelAttribute("vacancy")Vacancy vacancy, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "fragments/vacancy/update-vacancy";
        }
        recruitmentZoneService.saveVacancy(vacancy);
        return "redirect:vacancies";
    }*/

/*    @GetMapping("/manageVacancies")
    public String manageVacancies(Model model) {
        List<Vacancy> vacancies = new ArrayList<>();
        try {
            vacancies = recruitmentZoneService.getAllVacancies();
        } catch (Exception e) {
            log.info("Exception trying to retrieve employee vacancies, retrieving all active vacancies ");
        }
        model.addAttribute("Vacancies", vacancies);
        return "fragments/vacancy/manage-vacancies";
    }*/

}
