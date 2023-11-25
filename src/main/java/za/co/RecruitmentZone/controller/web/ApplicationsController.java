package za.co.RecruitmentZone.controller.web;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import za.co.RecruitmentZone.entity.domain.Candidate;
import za.co.RecruitmentZone.service.RecruitmentZoneService;


@Controller
@CrossOrigin("*")
public class ApplicationsController {
    private final RecruitmentZoneService recruitmentZoneService;
    private final Logger log = LoggerFactory.getLogger(ApplicationsController.class);

    public ApplicationsController(RecruitmentZoneService recruitmentZoneService) {
        this.recruitmentZoneService = recruitmentZoneService;
    }

    @PostMapping("/apply-now")
    public String showVacancyApplicationForm(Model model) {
        model.addAttribute("submission", new Candidate());
        return "fragments/applications/apply-now";
    }


    @PostMapping("/save-application")
    public String saveSubmission(@Valid @ModelAttribute("submission")Candidate candidate, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "fragments/applications/apply-now";
        }
        String filePath = "C://FILE";
        candidate.setCvFilePath(filePath);
        boolean result = recruitmentZoneService.saveSubmission(candidate);
        if (result) {
            redirectAttributes.addAttribute("successMessage", "success");
        }
        return "redirect:/vacancy-page";
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



}
