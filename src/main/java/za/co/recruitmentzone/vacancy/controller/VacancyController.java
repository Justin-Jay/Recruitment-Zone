package za.co.recruitmentzone.vacancy.controller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import za.co.recruitmentzone.util.enums.ApplicationStatus;
import za.co.recruitmentzone.util.enums.VacancyStatus;
import za.co.recruitmentzone.service.RecruitmentZoneService;
import za.co.recruitmentzone.vacancy.dto.VacancyDTO;
import za.co.recruitmentzone.vacancy.entity.Vacancy;

import java.util.List;

import static za.co.recruitmentzone.util.Constants.ErrorMessages.INTERNAL_SERVER_ERROR;

@Controller
@RequestMapping("/Vacancy")
public class VacancyController {

    private final RecruitmentZoneService recruitmentZoneService;
    private final Logger log = LoggerFactory.getLogger(VacancyController.class);

    public VacancyController(RecruitmentZoneService recruitmentZoneService) {
        this.recruitmentZoneService = recruitmentZoneService;
    }

    @GetMapping("/vacancy-administration")
    public String vacancies(Model model, @ModelAttribute("internalServerError") String internalServerError,
                            @ModelAttribute("saveVacancyResponse") String saveVacancyResponse) {
        try {
            recruitmentZoneService.getAllVacancies(model);
            model.addAttribute("saveVacancyResponse", saveVacancyResponse);
            model.addAttribute("internalServerError", internalServerError);
        } catch (Exception e) {
            log.info("<-- vacancies -->  Exception \n {}",e.getMessage());
            model.addAttribute("internalServerError", INTERNAL_SERVER_ERROR);
        }
       // log.info("<-- vacancies --> model: \n {}",model.asMap().toString());
        return "fragments/vacancy/vacancy-administration";
    }

    @GetMapping("/add-vacancy")
    public String showCreateVacancyForm(Model model) {
        try {
            recruitmentZoneService.findAllClients(model);
            recruitmentZoneService.findAllEmployees(model);
            model.addAttribute("vacancy", new VacancyDTO());
        } catch (Exception e) {
            log.info("<-- showCreateVacancyForm -->  Exception \n {}",e.getMessage());
            model.addAttribute("internalServerError", INTERNAL_SERVER_ERROR);
        }
        log.info("<-- showCreateVacancyForm --> model: \n {}",model.asMap().toString());
        return "fragments/vacancy/add-vacancy";
    }

    @PostMapping("/view-vacancy")
    public String showVacancy(@RequestParam("vacancyID") Long vacancyID, Model model) {
        try {
            recruitmentZoneService.findVacancyById(vacancyID,model);
        } catch (Exception e) {
            log.info("<-- showVacancy -->  Exception \n {}",e.getMessage());
            model.addAttribute("internalServerError",INTERNAL_SERVER_ERROR);
        }
        log.info("<-- showVacancy --> model: \n {}",model.asMap().toString());
        return "fragments/vacancy/view-vacancy";
    }



    @PostMapping("/save-vacancy")
    public String saveVacancy(@Valid @ModelAttribute("vacancy") VacancyDTO vacancy,
                              BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasFieldErrors()) {
            recruitmentZoneService.findAllClients(model);
            recruitmentZoneService.findAllEmployees(model);
            log.info("VACANCY ERRORS {}", bindingResult.getAllErrors());
            return "fragments/vacancy/add-vacancy";
        }
        try {
            recruitmentZoneService.saveNewVacancy(vacancy, redirectAttributes);
        } catch (Exception e) {
            log.info("<-- saveVacancy -->  Exception \n {}",e.getMessage());
            model.addAttribute("internalServerError", INTERNAL_SERVER_ERROR);
        }
        log.info("<-- saveVacancy --> model: \n {}",model.asMap().toString());
        return "redirect:/Vacancy/vacancy-administration";

    }
    @PostMapping("/update-vacancy")
    public String updateVacancy(@RequestParam("vacancyID") Long vacancyID, Model model) {
        try {
            recruitmentZoneService.findVacancyById(vacancyID,model);
            model.addAttribute("vacancyStatusValues", VacancyStatus.values());
        } catch (Exception e) {
            log.info("<-- updateVacancy -->  Exception \n {}",e.getMessage());
            model.addAttribute("internalServerError", INTERNAL_SERVER_ERROR);
        }
        log.info("<-- updateVacancy --> model: \n {}",model.asMap().toString());
        return "fragments/vacancy/update-vacancy";
    }

    @PostMapping("/save-updated-vacancy")
    public String saveUpdatedVacancy(@Valid @ModelAttribute("vacancy") VacancyDTO vacancy, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "fragments/vacancy/update-vacancy";
        }
        try {
            recruitmentZoneService.updateVacancy(vacancy,redirectAttributes);
        } catch (Exception e) {
            log.info("<-- saveUpdatedVacancy -->  Exception \n {}",e.getMessage());
            redirectAttributes.addFlashAttribute("internalServerError", INTERNAL_SERVER_ERROR);
        }
        log.info("<-- saveUpdatedVacancy --> redirectAttributes: \n {}",redirectAttributes.asMap().toString());
        return "redirect:/Vacancy/vacancy-administration";
    }

    @PostMapping("/view-vacancy-submission")
    public String viewVacancySubmissions(@RequestParam("vacancyID") Long vacancyID, Model model) {
        try {
            recruitmentZoneService.findVacancyById(vacancyID, model);
            model.addAttribute("applicationStatus", ApplicationStatus.values());
        } catch (Exception e) {
            log.info("<-- viewVacancySubmissions -->  Exception \n {}",e.getMessage());
            model.addAttribute("internalServerError", INTERNAL_SERVER_ERROR);
        }
        log.info("<-- viewVacancySubmissions --> model: \n {}",model.asMap().toString());
        return "fragments/vacancy/view-vacancy-submission";
    }

}
