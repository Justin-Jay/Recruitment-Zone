package za.co.RecruitmentZone.application.controller;

import jakarta.validation.Valid;
import org.apache.tika.Tika;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import za.co.RecruitmentZone.application.dto.NewApplicationDTO;
import za.co.RecruitmentZone.util.Enums.ApplicationStatus;
import za.co.RecruitmentZone.application.entity.Application;
import za.co.RecruitmentZone.candidate.entity.Candidate;
import za.co.RecruitmentZone.service.RecruitmentZoneService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;


@Controller
@CrossOrigin("*")
public class ApplicationsController {
    private final RecruitmentZoneService recruitmentZoneService;
    private final Logger log = LoggerFactory.getLogger(ApplicationsController.class);

    public ApplicationsController(RecruitmentZoneService recruitmentZoneService) {
        this.recruitmentZoneService = recruitmentZoneService;
    }

    @PostMapping("/apply-now")
    public String showVacancyApplicationForm(@RequestParam("vacancyID") Long vacancyID,
                                             Model model) {
      //  model.addAttribute("candidate", new Candidate());
        String vacancyName = recruitmentZoneService.getVacancyName(vacancyID);
        model.addAttribute("newApplicationDTO",new NewApplicationDTO());
        model.addAttribute("vacancyName", vacancyName);
        model.addAttribute("vacancyID", vacancyID);
        log.info("vacancyName {}",vacancyName);
        log.info("VacancyID {}",vacancyID);
        return "fragments/applications/apply-now";
    }



    @PostMapping("/save-application")
    public String saveSubmission(@Valid @ModelAttribute("newApplicationDTO") NewApplicationDTO newApplicationDTO,
                                 BindingResult bindingResult, Model model,
                                 RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("vacancyName",newApplicationDTO.getVacancyName());
            model.addAttribute("vacancyID",newApplicationDTO.getVacancyID());
            return "fragments/applications/apply-now";
        }
        else if (newApplicationDTO.getCvFile().isEmpty()) {
            model.addAttribute("vacancyName",newApplicationDTO.getVacancyName());
            model.addAttribute("vacancyID",newApplicationDTO.getVacancyID());
            model.addAttribute("message", "Please select a file to upload.");
            return "fragments/applications/apply-now";
        }

        // Security check: Ensure the file name is not a path that could be exploited
        else if (newApplicationDTO.getCvFile().getOriginalFilename().contains("..")) {
            model.addAttribute("vacancyName",newApplicationDTO.getVacancyName());
            model.addAttribute("vacancyID",newApplicationDTO.getVacancyID());
            model.addAttribute("message", "Invalid file name.");
            return "fragments/applications/apply-now";
        }

        else if (newApplicationDTO.getCvFile().getSize() > 1024 * 1024 * 25) { // 25MB
            model.addAttribute("vacancyName",newApplicationDTO.getVacancyName());
            model.addAttribute("vacancyID",newApplicationDTO.getVacancyID());
            model.addAttribute("message", "File size exceeds the maximum limit (25MB).");
            return "fragments/applications/apply-now";
        }

        // Security check: Ensure the file content is safe (detect content type)
        else if (!isValidContentType(newApplicationDTO.getCvFile())) {
            model.addAttribute("vacancyName",newApplicationDTO.getVacancyName());
            model.addAttribute("vacancyID",newApplicationDTO.getVacancyID());
            model.addAttribute("message", "Invalid file type. Only Word (docx) and PDF files are allowed.");
            return "fragments/applications/apply-now";
        }

        try {

            String fileDestination = "C:/uploads";

            // Create the directory if it doesn't exist
            Path uploadPath = Path.of(fileDestination);
            if (Files.notExists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // Copy the file to the target location
            Path targetLocation = uploadPath.resolve(newApplicationDTO.getCvFile().getOriginalFilename());
            Files.copy(newApplicationDTO.getCvFile().getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            // store on google cloud
            // String storageLocation = recruitmentZoneService.saveFile(vacancyID,cvFile);
            // candidate.setCvFilePath(storageLocation);

            String storageLocation = targetLocation.toString();


            Long candidateID = recruitmentZoneService.createCandidateApplication(newApplicationDTO,storageLocation);
            // publish file upload event and give the file

            recruitmentZoneService.publishFileUploadedEvent(candidateID,newApplicationDTO);

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
    private boolean isValidContentType(MultipartFile file) {
        try {
            String detectedContentType = new Tika().detect(file.getInputStream());
            return detectedContentType.equals("application/pdf") || detectedContentType.equals("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
        } catch (IOException e) {
            //log.info(e.getMessage());
            return false;
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

        return "redirect:/applications-administration";
    }



}
