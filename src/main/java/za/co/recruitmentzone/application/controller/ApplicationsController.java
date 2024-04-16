package za.co.recruitmentzone.application.controller;

import jakarta.validation.Valid;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import za.co.recruitmentzone.application.dto.NewApplicationDTO;
import za.co.recruitmentzone.util.enums.ApplicationStatus;
import za.co.recruitmentzone.application.entity.Application;
import za.co.recruitmentzone.service.RecruitmentZoneService;
import za.co.recruitmentzone.vacancy.exception.VacancyException;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import static za.co.recruitmentzone.util.Constants.ErrorMessages.INTERNAL_SERVER_ERROR;


@Controller
@RequestMapping("/Application")
public class ApplicationsController {
    private final RecruitmentZoneService recruitmentZoneService;
    private final Logger log = LoggerFactory.getLogger(ApplicationsController.class);

    public ApplicationsController(RecruitmentZoneService recruitmentZoneService) {
        this.recruitmentZoneService = recruitmentZoneService;
    }

    // MODEL =  vacancyName, vacancyID ,  newApplicationDTO , vacancyApplicationFormResponse ,internalServerError
    @PostMapping("/apply-now")
    public String showVacancyApplicationForm(@RequestParam("vacancyID") Long vacancyID,
                                             Model model) {
        try {
            recruitmentZoneService.vacancyApplicationForm(model, vacancyID);
        } catch (Exception e) {
            log.info("<-- showVacancyApplicationForm -->  Exception \n {}",e.getMessage());
            model.addAttribute("internalServerError", INTERNAL_SERVER_ERROR);
        }
        return "fragments/applications/apply-now";
    }

    // redirect = vacancyName , vacancyID , saveSubmissionFormResponse , createCandidateApplicationResponse
    // model = vacancyName , vacancyID , saveSubmissionFormResponse , createCandidateApplicationResponse
    @PostMapping("/save-application")
    public String saveSubmission(@Valid @ModelAttribute("newApplicationDTO") NewApplicationDTO newApplicationDTO,
                                 BindingResult bindingResult, Model model,
                                 RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("vacancyName", newApplicationDTO.getVacancyName());
            model.addAttribute("vacancyID", newApplicationDTO.getVacancyID());
            return "fragments/applications/apply-now";
        }
        try {
            recruitmentZoneService.saveSubmissionForm(newApplicationDTO, model, redirectAttributes);
        } catch (Exception e) {
            log.info("<-- saveSubmission -->  Exception \n {}",e.getMessage());
            model.addAttribute("internalServerError", INTERNAL_SERVER_ERROR);
            log.info("<-- saveSubmission --> model: \n {}", model.asMap().toString());
            return "fragments/applications/apply-now";
        }
        return "redirect:/home";
    }

    // model = saveApplicationStatusResponse , internalServerError, applications, findAllApplicationsResponse
    @GetMapping("/applications-administration")
    public String applications(Model model, @ModelAttribute("saveApplicationStatusResponse") String saveApplicationStatusResponse) {
        try {
            recruitmentZoneService.findAllApplications(model);
            model.addAttribute("saveApplicationStatusResponse", saveApplicationStatusResponse);
        } catch (Exception e) {
            log.info("<-- applications -->  Exception \n {}",e.getMessage());
            model.addAttribute("internalServerError", INTERNAL_SERVER_ERROR);
            return "fragments/applications/apply-now";
        }
        return "fragments/applications/application-administration";
    }

    // model = findApplicationByIDResponse , internalServerError, application
    @PostMapping("/view-application")
    public String viewApplication(Model model, @RequestParam("applicationID") Long applicationID) {
        try {
            recruitmentZoneService.findApplicationByID(applicationID, model);
        } catch (Exception e) {
            log.info("<-- viewApplication -->  Exception \n {}",e.getMessage());
            model.addAttribute("internalServerError", INTERNAL_SERVER_ERROR);
        }
        return "fragments/applications/view-application";
    }

    // model = vacancyApplication , findApplicationByIDResponse
    @PostMapping("/update-application")
    public String updateApplication(Model model, @RequestParam("applicationID") Long applicationID) {
        try {
            recruitmentZoneService.findApplicationByID(applicationID, model);
            model.addAttribute("status", ApplicationStatus.values());
        } catch (Exception e) {
            log.info("<-- updateApplication -->  Exception \n {}",e.getMessage());
            model.addAttribute("internalServerError", INTERNAL_SERVER_ERROR);
        }

        return "fragments/applications/update-application";
    }


    // redirectAttributes = internalServerError , saveApplicationStatusResponse , saveApplicationStatusResponse ,

    @PostMapping("/save-updated-application")
    public String saveUpdatedApplicationStatus(
            @RequestParam("applicationID") Long applicationID,
            @RequestParam("status") ApplicationStatus status,
            @Valid @ModelAttribute("application") Application application,
            BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        log.info("save-updated-application start");
        if (bindingResult.hasErrors()) {
            return "fragments/applications/update-application";
        }
        try {
            recruitmentZoneService.saveUpdatedApplicationStatus(redirectAttributes, applicationID, status);
        } catch (Exception e) {
            log.info("<-- saveUpdatedApplicationStatus -->  Exception \n {}",e.getMessage());
            redirectAttributes.addFlashAttribute("internalServerError", INTERNAL_SERVER_ERROR);
        }

        return "redirect:/Application/applications-administration";
    }


}