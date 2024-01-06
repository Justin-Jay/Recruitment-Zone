package za.co.recruitmentzone.application.controller;

import jakarta.validation.Valid;
import org.apache.tika.Tika;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import za.co.recruitmentzone.application.dto.NewApplicationDTO;
import za.co.recruitmentzone.util.StringConstants;
import za.co.recruitmentzone.util.WebPageURL;
import za.co.recruitmentzone.util.enums.ApplicationStatus;
import za.co.recruitmentzone.application.entity.Application;
import za.co.recruitmentzone.service.RecruitmentZoneService;

import java.io.IOException;
import java.util.List;



@Controller
@RequestMapping("/Application")
public class ApplicationsController {
    private final RecruitmentZoneService recruitmentZoneService;
    private final Logger log = LoggerFactory.getLogger(ApplicationsController.class);

    public ApplicationsController(RecruitmentZoneService recruitmentZoneService) {
        this.recruitmentZoneService = recruitmentZoneService;
    }

    @PostMapping("/apply-now")
    public String showVacancyApplicationForm(@RequestParam("vacancyID") Long vacancyID,
                                             Model model) {
        String vacancyName = recruitmentZoneService.getVacancyName(vacancyID);
        newFileSubmission(model,vacancyName,vacancyID);
        log.info("vacancyName {}",vacancyName);
        log.info("VacancyID {}",vacancyID);
        return WebPageURL.APPLY_NOW_URL;
    }


    @PostMapping("/save-application")
    public String saveSubmission(@Valid @ModelAttribute("newApplicationDTO") NewApplicationDTO newApplicationDTO,
                                 BindingResult bindingResult, Model model,
                                 RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            fileSubmissionAttributes(model,newApplicationDTO);
            return WebPageURL.APPLY_NOW_URL;
        }
        else if (newApplicationDTO.getCvFile().isEmpty()) {
            fileSubmissionAttributes(model,newApplicationDTO,"Please select a file to upload.");
            return WebPageURL.APPLY_NOW_URL;
        }
        // Security check: Ensure the file name is not a path that could be exploited
        else if (newApplicationDTO.getCvFile().getOriginalFilename().contains("..")) {
            fileSubmissionAttributes(model,newApplicationDTO,"Invalid file name.");
            return WebPageURL.APPLY_NOW_URL;
        }
        else if (newApplicationDTO.getCvFile().getSize() > 1024 * 1024 * 25) { // 25MB
            fileSubmissionAttributes(model,newApplicationDTO,"File size exceeds the maximum limit (25MB).");
            return WebPageURL.APPLY_NOW_URL;
        }
        // Security check: Ensure the file content is safe (detect content type)
        else if (!isValidContentType(newApplicationDTO.getCvFile())) {
            fileSubmissionAttributes(model,newApplicationDTO,"Invalid file type. Only Word (docx) and PDF files are allowed.");
            return "fragments/applications/apply-now";
        }
        try {
            recruitmentZoneService.createCandidateApplication(newApplicationDTO);
            /**
             * publish file upload event and give the file
             *
             * // recruitmentZoneService.publishFileUploadedEvent(candidateID,newApplicationDTO);
             */

            redirectAttributes.addFlashAttribute(StringConstants.MESSAGE,
                    "Application Submitted Successfully!");
            return "redirect:/home";
        } catch (Exception e) {
            log.info(e.getMessage());
            log.info("Failed to save file");
            model.addAttribute("message", "File upload failed. Please try again.");
            return "fragments/applications/apply-now";
        }
    }


    @GetMapping("/applications-administration")
    public String applications(Model model) {
        List<Application> allApplications = recruitmentZoneService.getApplications();
        try {
            allApplications = recruitmentZoneService.getApplications();
        } catch (Exception e) {
            log.info("Exception trying to retrieve applications, retrieving all applications ");
        }
        model.addAttribute("applications", allApplications);
        return "fragments/applications/application-administration";
    }

    @PostMapping("/view-application")
    public String viewApplication(Model model, @RequestParam("applicationID") Long applicationID) {
        Application application = recruitmentZoneService.findApplicationByID(applicationID);
        model.addAttribute("vacancyApplication", application);
        return "fragments/applications/view-application";
    }

    @PostMapping("/update-application")
    public String updateApplication(Model model, @RequestParam("applicationID") Long applicationID) {
        Application application = recruitmentZoneService.findApplicationByID(applicationID);
        model.addAttribute("vacancyApplication", application);
        model.addAttribute("status", ApplicationStatus.values());
        return "fragments/applications/update-application";
    }

    @PostMapping("/save-updated-application")
    public String saveUpdatedApplicationStatus(
            @RequestParam("applicationID") Long applicationID,
            @RequestParam("status") ApplicationStatus status,
            @Valid @ModelAttribute("application") Application application,
            BindingResult bindingResult) {
        log.info("save-updated-application start");
        if (bindingResult.hasErrors()) {
            return "fragments/applications/update-application";
        }
        recruitmentZoneService.saveUpdatedApplicationStatus(applicationID, status);
        return "redirect:/Application/applications-administration";
    }


    private void newFileSubmission(Model model,String vacancyName,Long vacancyID) {
        model.addAttribute("newApplicationDTO",new NewApplicationDTO());
        model.addAttribute(StringConstants.VACANCY_NAME, vacancyName);
        model.addAttribute(StringConstants.VACANCY_ID, vacancyID);
    }

    private void fileSubmissionAttributes(Model model, NewApplicationDTO newApplicationDTO) {
        model.addAttribute(StringConstants.VACANCY_NAME,newApplicationDTO.getVacancyName());
        model.addAttribute(StringConstants.VACANCY_ID,newApplicationDTO.getVacancyID());
    }


    private void fileSubmissionAttributes(Model model, NewApplicationDTO newApplicationDTO, String message) {
        model.addAttribute(StringConstants.VACANCY_NAME,newApplicationDTO.getVacancyName());
        model.addAttribute(StringConstants.VACANCY_ID,newApplicationDTO.getVacancyID());
        model.addAttribute(StringConstants.MESSAGE, message);
    }

    private boolean isValidContentType(MultipartFile file) {
        try {
            String detectedContentType = new Tika().detect(file.getInputStream());
            return detectedContentType.equals("application/pdf") || detectedContentType.equals("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
        } catch (IOException e) {
            log.info(e.getMessage());
            return false;
        }
    }

}
