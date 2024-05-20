package za.co.recruitmentzone.vacancy.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import za.co.recruitmentzone.util.enums.ApplicationStatus;
import za.co.recruitmentzone.util.enums.VacancyStatus;
import za.co.recruitmentzone.service.RecruitmentZoneService;
import za.co.recruitmentzone.vacancy.dto.VacancyDTO;


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
    public String vacancies(Model model, HttpServletRequest request) {

        try {
            recruitmentZoneService.getAllVacancies(model);

        } catch (Exception e) {
            log.error("<-- vacancies -->  Exception \n {}", e.getMessage());
            model.addAttribute("internalServerError", INTERNAL_SERVER_ERROR);
        }
        //  vacancyList , loadVacanciesResponse
        return "fragments/vacancy/vacancy-administration";
    }

    @PostMapping("/add-vacancy")
    public String showCreateVacancyForm(Model model) {
        try {
            recruitmentZoneService.addVacancy(model);
        } catch (Exception e) {
            log.error("<-- showCreateVacancyForm -->  Exception \n {}", e.getMessage());
            model.addAttribute("internalServerError", INTERNAL_SERVER_ERROR);
        }
        //  vacancyDTO , clients , findAllClientsResponse  , employeeList loadEmployeesResponse , internalServerError
        return "fragments/vacancy/add-vacancy";
    }

    @PostMapping("/view-vacancy")
    public String showVacancy(@RequestParam("vacancyID") Long vacancyID, Model model) {
        try {
            recruitmentZoneService.findVacancy(vacancyID, model);
        } catch (Exception e) {
            log.error("<-- showVacancy -->  Exception \n {}", e.getMessage());
            model.addAttribute("internalServerError", INTERNAL_SERVER_ERROR);
        }
        // vacancy  , findVacancyResponse
        return "fragments/vacancy/view-vacancy";
    }

    @PostMapping("/view-home-vacancy")
    public String showHomeVacancy(@RequestParam("vacancyID") Long vacancyID, Model model) {
        try {
            recruitmentZoneService.findVacancy(vacancyID, model);
        } catch (Exception e) {
            log.error("<-- showVacancy -->  Exception \n {}", e.getMessage());
            model.addAttribute("internalServerError", INTERNAL_SERVER_ERROR);
        }

        return "fragments/vacancy/view-home-vacancy";
    }

    @PostMapping("/save-vacancy")
    public String saveVacancy(@Valid @ModelAttribute("vacancy") VacancyDTO vacancy,
                              BindingResult bindingResult, Model model) {
        if (bindingResult.hasFieldErrors()) {
            recruitmentZoneService.findAllClients(model);
            recruitmentZoneService.findAllEmployees(model);
            model.addAttribute("bindingResult", INTERNAL_SERVER_ERROR);
            return "fragments/vacancy/add-vacancy";
        }
        try {
            recruitmentZoneService.saveNewVacancy(vacancy, model);
        } catch (Exception e) {
            log.error("<-- saveVacancy -->  Exception \n {}", e.getMessage());
            model.addAttribute("internalServerError", INTERNAL_SERVER_ERROR);
        }
        // saveVacancyResponse , internalServerError

        return "fragments/vacancy/view-vacancy";

    }

    @PostMapping("/update-vacancy")
    public String updateVacancy(@RequestParam("vacancyID") Long vacancyID, Model model) {
        try {
            recruitmentZoneService.findVacancy(vacancyID, model);
            //model.addAttribute("vacancyStatusValues", VacancyStatus.values());
        } catch (Exception e) {
            log.error("<-- updateVacancy -->  Exception \n {}", e.getMessage());
            model.addAttribute("internalServerError", INTERNAL_SERVER_ERROR);
        }
        // vacancy , findVacancyResponse
        return "fragments/vacancy/update-vacancy";
    }

    @PostMapping("/save-updated-vacancy")
    public String saveUpdatedVacancy(@Valid @ModelAttribute("vacancy") VacancyDTO vacancy, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "fragments/vacancy/update-vacancy";
        }
        try {
            recruitmentZoneService.updateVacancy(vacancy, model);
        } catch (Exception e) {
            log.error("<-- saveUpdatedVacancy -->  Exception \n {}", e.getMessage());
            model.addAttribute("internalServerError", INTERNAL_SERVER_ERROR);
        }
        // vacancy ,  saveVacancyResponse
        return "fragments/vacancy/view-vacancy";
    }

    @PostMapping("/view-vacancy-submission")
    public String viewVacancySubmissions(@RequestParam("vacancyID") Long vacancyID, Model model) {
        try {
            recruitmentZoneService.findVacancySubmissions(vacancyID, model);
            model.addAttribute("applicationStatus", ApplicationStatus.values());
        } catch (Exception e) {
            log.error("<-- viewVacancySubmissions -->  Exception \n {}", e.getMessage());
            model.addAttribute("internalServerError", INTERNAL_SERVER_ERROR);
        }
        return "fragments/vacancy/view-vacancy-submission";
    }

    @PostMapping("/search-vacancies")
    public String searchVacancies(@RequestParam(name = "title") String title, Model model) {
        try {
            log.info("Searching for {}", title);
            recruitmentZoneService.searchVacancyByTitle(title, model);

            log.info("Vacancy search completed");

        } catch (Exception e) {
            model.addAttribute("internalServerError", e.getMessage());
        }
        return "fragments/layout/search-results";
    }

}
