package za.co.RecruitmentZone.controller.web;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import za.co.RecruitmentZone.entity.Enums.ApplicationStatus;
import za.co.RecruitmentZone.entity.domain.Application;
import za.co.RecruitmentZone.entity.domain.Candidate;
import za.co.RecruitmentZone.service.RecruitmentZoneService;

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
    public String showVacancyApplicationForm(Model model, @RequestParam("vacancyID") Long vacancyID) {
        model.addAttribute("candidate", new Candidate());
        String vacancyName = recruitmentZoneService.getVacancyName(vacancyID);
        model.addAttribute("vacancyName", vacancyName);
        model.addAttribute("vacancyID", vacancyID);
        return "fragments/applications/apply-now";
    }


    @PostMapping("/save-application")
    public String saveSubmission(@Valid @ModelAttribute("candidate") Candidate candidate,
                                 @RequestParam("vacancyID")Long vacancyID,
                                 @RequestParam("cvFile") MultipartFile cvFile,
                                 BindingResult bindingResult,Model model) {
        if (bindingResult.hasErrors()) {
            return "fragments/applications/apply-now";
        }
        else if (cvFile.isEmpty()) {
            model.addAttribute("message", "Please select a file to upload.");
            return "fragments/applications/apply-now";
        }
        log.info("Received vacancyID: " + vacancyID);

        if (!cvFile.isEmpty()) {
            // Process the CV file
            // Additional validations like checking for already submitted application, etc.

            try {
             //   filePath = recruitmentZoneService.saveFile(vacancyID,cvFile);

                String fileDestination = "C:/uploads";

                // Create the directory if it doesn't exist
                Path uploadPath = Path.of(fileDestination);
                if (Files.notExists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }

                // Copy the file to the target location
                Path targetLocation = uploadPath.resolve(cvFile.getOriginalFilename());
                Files.copy(cvFile.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

               // store on google cloud
                // String storageLocation = recruitmentZoneService.saveFile(vacancyID,cvFile);
                // candidate.setCvFilePath(storageLocation);


                candidate.setCvFilePath(targetLocation.toString());

                model.addAttribute("message",
                        "File '" + cvFile.getOriginalFilename() + "' uploaded successfully!");

                Long candidateID = recruitmentZoneService.createCandidateApplication(vacancyID,candidate);
                // publish file upload event and give the file

                recruitmentZoneService.publishFileUploadedEvent(cvFile,candidateID,vacancyID);
                return "fragments/applications/file-uploaded";
            } catch (Exception e) {
                log.info("Failed to save file");
                candidate.setCvFilePath("Failed to save file");
                model.addAttribute("message", "File upload failed. Please try again.");
                //redirectAttributes.addFlashAttribute("message", "File upload failed. Please try again.");
                return "fragments/applications/apply-now";
            }

        }

        return "fragments/applications/apply-now";
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
        Application application = recruitmentZoneService.getApplicationByID(applicationID);
        model.addAttribute("vacancyApplication", application);
        return "fragments/applications/view-application";
    }

    @PostMapping("/update-application")
    public String updateApplication(Model model, @RequestParam("applicationID") Long applicationID) {
        Application application = recruitmentZoneService.getApplicationByID(applicationID);
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
