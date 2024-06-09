package za.co.recruitmentzone.application.controller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import za.co.recruitmentzone.application.dto.ApplicationDTO;
import za.co.recruitmentzone.application.dto.NewApplicationDTO;
import za.co.recruitmentzone.util.enums.ApplicationStatus;
import za.co.recruitmentzone.application.entity.Application;
import za.co.recruitmentzone.service.RecruitmentZoneService;

import java.util.List;

import static za.co.recruitmentzone.util.Constants.ErrorMessages.INTERNAL_SERVER_ERROR;


@Controller
@RequestMapping("/Application")
public class ApplicationsController {
    private final RecruitmentZoneService recruitmentZoneService;


    private final Logger log = LoggerFactory.getLogger(ApplicationsController.class);

    public ApplicationsController(RecruitmentZoneService recruitmentZoneService) {
        this.recruitmentZoneService = recruitmentZoneService;
    }


    @GetMapping("/applications-administration")
    public String applications(Model model) {
        try {
            int pageSize = 10;
            recruitmentZoneService.findAllApplications(model,1, pageSize, "created", "desc");
        } catch (Exception e) {
            log.error("<-- applications -->  Exception \n {}", e.getMessage());
            model.addAttribute("internalServerError", INTERNAL_SERVER_ERROR);
        }
        // applicationsList , findAllApplicationsResponse , internalServerError
        return "fragments/applications/application-administration";
    }

    @GetMapping("/paginatedApplications/{pageNo}")
    public String findPaginatedApplications(@PathVariable(value = "pageNo") int pageNo,
                                     @RequestParam("sortField") String sortField, @RequestParam("sortDir") String sortDirection, Model model) {
        int pageSize = 10;
        log.info("Page number  {}", pageNo);
        log.info("sortField {}", sortField);
        log.info("sortDirection {}", sortDirection);
        recruitmentZoneService.findAllApplications(model,pageNo, pageSize, sortField, sortDirection);
        return "fragments/applications/application-administration :: applications-admin-table";
    }


    @PostMapping("/apply-now")
    public String showVacancyApplicationForm(@RequestParam("vacancyID") Long vacancyID,
                                             Model model) {
        try {
            recruitmentZoneService.vacancyApplicationForm(model, vacancyID);
        } catch (Exception e) {
            log.info("<-- showVacancyApplicationForm -->  Exception \n {}", e.getMessage());
            model.addAttribute("internalServerError", INTERNAL_SERVER_ERROR);
        }
        //  vacancyName , vacancyID , newApplicationDTO , internalServerError , vacancyApplicationFormResponse , newApplicationDTO
        return "fragments/applications/apply-now";
    }

    @PostMapping("/save-application")
    public String saveSubmission(@Valid @ModelAttribute("newApplicationDTO") NewApplicationDTO newApplicationDTO,
                                 BindingResult bindingResult, Model model ) {
        if (bindingResult.hasFieldErrors()) {

            //recruitmentZoneService.vacancyApplicationForm(model, newApplicationDTO.getVacancyID());


            //  vacancyName , vacancyID , newApplicationDTO , vacancyApplicationFormResponse , newApplicationDTO , bindingResult
            return "fragments/applications/apply-now";
        }

        else {
            try {
                recruitmentZoneService.saveApplication(newApplicationDTO, model);
                // applicationOutcome , createCandidateApplication , invalidFileException
                if (model.getAttribute("invalidFileException") != null) {
                    log.info("<-- saveSubmission -->  invalidFileException Result : {}", model.getAttribute("invalidFileException"));
                    recruitmentZoneService.vacancyApplicationForm(model, newApplicationDTO.getVacancyID());
                    //  vacancyName , vacancyID , newApplicationDTO , vacancyApplicationFormResponse , newApplicationDTO , invalidFileException
                    return "fragments/applications/apply-now";
                }

            } catch (Exception e) {
                log.error("<-- saveSubmission -->  Exception \n {}", e.getMessage());
                model.addAttribute("internalServerError", INTERNAL_SERVER_ERROR);
                recruitmentZoneService.vacancyApplicationForm(model, newApplicationDTO.getVacancyID());
                // ContactMessage  =   emailEventPublisher.publishWebsiteQueryReceivedEvent(message);

                return "fragments/applications/apply-now";
            }
        }

        //
        return "fragments/applications/application-submitted";
    }

    @PostMapping("/view-application")
    public String viewApplication(Model model, @RequestParam("applicationID") Long applicationID) {
        try {

            recruitmentZoneService.findApplicationByID(applicationID, model);

        } catch (Exception e) {
            log.error("<-- viewApplication -->  Exception \n {}", e.getMessage());
            model.addAttribute("internalServerError", INTERNAL_SERVER_ERROR);
        }
        // vacancyApplication , findApplicationByIDResponse , internalServerError

        return "fragments/applications/view-application";
    }

    @PostMapping("/update-application")
    public String updateApplication(Model model, @RequestParam("applicationID") Long applicationID) {
        try {
            recruitmentZoneService.findApplicationByID(applicationID, model);

        } catch (Exception e) {
            log.error("<-- updateApplication -->  Exception \n {}", e.getMessage());
            model.addAttribute("internalServerError", INTERNAL_SERVER_ERROR);
        }
        //vacancyApplication  findApplicationByIDResponse , internalServerError
        return "fragments/applications/update-application";
    }

    @PostMapping("/save-updated-application")
    public String saveUpdatedApplicationStatus(
            @Valid @ModelAttribute("vacancyApplication") ApplicationDTO applicationDTO,
            BindingResult bindingResult, Model model) {
        log.info("save-updated-application start");
        if (bindingResult.hasErrors()) {
            model.addAttribute("bindingResult", INTERNAL_SERVER_ERROR);
            recruitmentZoneService.findApplicationByID(applicationDTO.getApplicationID(), model);
            return "fragments/applications/update-application";
        }
        try {
            recruitmentZoneService.saveUpdatedApplicationStatus(model, applicationDTO);
            recruitmentZoneService.findApplicationByID(applicationDTO.getApplicationID(), model);

        } catch (Exception e) {
            log.error("<-- saveUpdatedApplicationStatus -->  Exception \n {}", e.getMessage());
            model.addAttribute("internalServerError", INTERNAL_SERVER_ERROR);
        }
        // saveApplicationStatusResponse , saveApplicationStatusResponse
        //applicationsList ,findAllApplicationsResponse
        return "fragments/applications/update-application";
    }



}