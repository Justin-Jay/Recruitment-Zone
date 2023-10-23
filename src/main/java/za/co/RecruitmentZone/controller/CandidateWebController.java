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
import za.co.RecruitmentZone.service.VacancyService;
import za.co.RecruitmentZone.storage.FileUploadService;

import java.util.List;

@Controller
@RequestMapping("/candidate/web")
@CrossOrigin("*")
public class CandidateWebController {

    private final VacancyService vacancyService;
    private final CandidateService candidateService;
    private final FileUploadService fileService;
    private final Logger log = LoggerFactory.getLogger(CandidateWebController.class);

    public CandidateWebController(VacancyService vacancyService, CandidateService candidateService, FileUploadService fileService) {
        this.vacancyService = vacancyService;
        this.candidateService = candidateService;
        this.fileService = fileService;
    }

    @GetMapping("/view-vacancies")
    public String viewActiveVacancies(Model model) {
        List<Vacancy> vacancies = vacancyService.getActiveVacancies();
        model.addAttribute("vacancies", vacancies);
        return "vacancies";
    }

    @PostMapping("/submitApplication")
    public String submitApplication(@Valid @ModelAttribute Application application,
                                    BindingResult bindingResult,
                                    @RequestParam("file") MultipartFile file,
                                    RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            return "applicationForm";
        }

        try {
            String storedFileName = fileService.uploadFile(file);
            if (storedFileName == null) {
                redirectAttributes.addFlashAttribute("message", "File upload failed.");
                return "redirect:/uploadStatus";
            }

            application.setCvFilePath(storedFileName);
            Gson gson = new Gson();
            String json = gson.toJson(application);
            candidateService.publishCandidateAppliedEvent(json);

        } catch (Exception e) {
            log.info("FAILED TO POST EVENT " + e.getMessage());
        }

        return "redirect:/vacancies";
    }
}

