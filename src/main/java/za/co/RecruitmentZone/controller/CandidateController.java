package za.co.RecruitmentZone.controller;

import com.google.gson.Gson;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import za.co.RecruitmentZone.entity.Application;
import za.co.RecruitmentZone.service.EventOrchestration.CandidateService;
import za.co.RecruitmentZone.service.FileService;

@Controller
@RequestMapping("/apply")
@CrossOrigin("*")
public class CandidateController {


    private final CandidateService candidateEventManagement;


    private final Logger log = LoggerFactory.getLogger(CandidateController.class);

    public CandidateController(CandidateService candidateEventManagement) {
        this.candidateEventManagement = candidateEventManagement;
    }

    // Folder where uploaded files will be stored
    @PostMapping("/submitApplication")
    public String submitApplication(@Valid @ModelAttribute Application application,
                                    BindingResult bindingResult,
                                    @RequestParam("file") MultipartFile file,
                                    RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            return "applicationForm";
        }

        // Additional validations like checking for already submitted application, etc.

        try {
            // Attempt to store the file securely
            String storedFileName = FileService.uploadFile(file);

            if (storedFileName == null) {
                redirectAttributes.addFlashAttribute("message", "File upload failed.");
                return "redirect:/uploadStatus";
            }

            application.setCvFilePath(storedFileName); // Save the stored file name in the application object

            // Other logic to process the application, such as publishing an event
            Gson gson = new Gson();
            String json = gson.toJson(application);
            candidateEventManagement.publishCandidateAppliedEvent(json);

        } catch (Exception e) {
            log.info("FAILED TO POST EVENT " + e.getMessage());
        }

        return "redirect:/vacancies";
    }




}



