package za.co.RecruitmentZone.application.controller;

import jakarta.validation.Valid;
import org.apache.tika.Tika;
import org.checkerframework.checker.units.qual.N;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import za.co.RecruitmentZone.application.dto.NewApplicationDTO;
import za.co.RecruitmentZone.util.Enums.ApplicationStatus;
import za.co.RecruitmentZone.application.entity.Application;
import za.co.RecruitmentZone.service.RecruitmentZoneService;

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
        return "fragments/applications/apply-now";
    }


    @PostMapping("/save-application")
    public String saveSubmission(@Valid @ModelAttribute("newApplicationDTO") NewApplicationDTO newApplicationDTO,
                                 BindingResult bindingResult, Model model,
                                 RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            FileSubmissionAttributes(model,newApplicationDTO);
            return "fragments/applications/apply-now";
        }
        else if (newApplicationDTO.getCvFile().isEmpty()) {
            FileSubmissionAttributes(model,newApplicationDTO,"Please select a file to upload.");
            return "fragments/applications/apply-now";
        }
        // Security check: Ensure the file name is not a path that could be exploited
        else if (newApplicationDTO.getCvFile().getOriginalFilename().contains("..")) {
            FileSubmissionAttributes(model,newApplicationDTO,"Invalid file name.");
            return "fragments/applications/apply-now";
        }
        else if (newApplicationDTO.getCvFile().getSize() > 1024 * 1024 * 25) { // 25MB
            FileSubmissionAttributes(model,newApplicationDTO,"File size exceeds the maximum limit (25MB).");
            return "fragments/applications/apply-now";
        }
        // Security check: Ensure the file content is safe (detect content type)
        else if (!isValidContentType(newApplicationDTO.getCvFile())) {
            FileSubmissionAttributes(model,newApplicationDTO,"Invalid file type. Only Word (docx) and PDF files are allowed.");
            return "fragments/applications/apply-now";
        }
        try {
            recruitmentZoneService.createCandidateApplication(newApplicationDTO);
            // publish file upload event and give the file
            //recruitmentZoneService.publishFileUploadedEvent(candidateID,newApplicationDTO);
            redirectAttributes.addFlashAttribute("message",
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
        model.addAttribute("vacancyName", vacancyName);
        model.addAttribute("vacancyID", vacancyID);
    }

    private void FileSubmissionAttributes(Model model, NewApplicationDTO newApplicationDTO) {
        model.addAttribute("vacancyName",newApplicationDTO.getVacancyName());
        model.addAttribute("vacancyID",newApplicationDTO.getVacancyID());
    }


    private void FileSubmissionAttributes(Model model, NewApplicationDTO newApplicationDTO, String message) {
        model.addAttribute("vacancyName",newApplicationDTO.getVacancyName());
        model.addAttribute("vacancyID",newApplicationDTO.getVacancyID());
        model.addAttribute("message", message);
    }

    private boolean isValidContentType(MultipartFile file) {
        try {
            String detectedContentType = new Tika().detect(file.getInputStream());
            return detectedContentType.equals("application/pdf") || detectedContentType.equals("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
        } catch (IOException e) {
            //log.info(e.getMessage());
            return false;
        }
    }

}
