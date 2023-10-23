package za.co.RecruitmentZone.controller;

import com.google.gson.Gson;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import za.co.RecruitmentZone.entity.Application;
import za.co.RecruitmentZone.entity.Vacancy;
import za.co.RecruitmentZone.service.CandidateService;
import za.co.RecruitmentZone.service.FileService;
import za.co.RecruitmentZone.service.VacancyService;

import java.util.List;

@Controller
@RequestMapping("/guest")
@CrossOrigin("*")
public class GuestController {
    private final VacancyService vacancyService;
    private final CandidateService candidateService;
    private final FileService fileService;
    private final Logger log = LoggerFactory.getLogger(GuestController.class);

    public GuestController(VacancyService vacancyService, CandidateService candidateService, FileService fileService) {
        this.vacancyService = vacancyService;
        this.candidateService = candidateService;
        this.fileService = fileService;
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
            String storedFileName = fileService.uploadFile(file);

            if (storedFileName == null) {
                redirectAttributes.addFlashAttribute("message", "File upload failed.");
                return "redirect:/uploadStatus";
            }

            application.setCvFilePath(storedFileName); // Save the stored file name in the application object

            // Other logic to process the application, such as publishing an event
            Gson gson = new Gson();
            String json = gson.toJson(application);
            candidateService.publishCandidateAppliedEvent(json);
        } catch (Exception e) {
            log.info("FAILED TO POST EVENT " + e.getMessage());
        }

        return "redirect:/vacancies";
    }

    @GetMapping("/list-active-vacancies")
    public String listVacancies(Model model) {
        // Retrieve and display a list of vacancies
        List<Vacancy> vacancies = vacancyService.getActiveVacancies();
        model.addAttribute("vacancies", vacancies);
        return "list-vacancies";
    }


}



