package za.co.recruitmentzone.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import za.co.recruitmentzone.service.RecruitmentZoneService;
import za.co.recruitmentzone.vacancy.entity.Vacancy;

import java.security.Principal;
import java.util.List;

@Controller
public class RecruitmentZoneWebController {
    private final RecruitmentZoneService recruitmentZoneService;
    private final JobLauncher jobLauncher;
    private final Job vacancyJob;
    private final Logger log = LoggerFactory.getLogger(RecruitmentZoneWebController.class);

    public RecruitmentZoneWebController(RecruitmentZoneService recruitmentZoneService, JobLauncher jobLauncher, Job vacancyJob) {
        this.recruitmentZoneService = recruitmentZoneService;
        this.jobLauncher = jobLauncher;
        this.vacancyJob = vacancyJob;
    }

    /*     @GetMapping("/")
         public String redirectToHome(Principal principal, RedirectAttributes redirectAttributes){
             redirectAttributes.addAttribute(principal);
             redirectAttributes.addAttribute(principal);
             return "redirect:/home";
         }*/
    // Home pages
    @GetMapping(value = {"/", "/home"})
    public String home(@RequestParam(name = "name", required = false) String title, Model model, Principal principal,
                       @ModelAttribute("createCandidateApplicationResponse") String createCandidateApplicationResponse, @ModelAttribute("internalServerError") String internalServerError,
                       @ModelAttribute("searchByTitleResponse") String searchByTitleResponse, @ModelAttribute("saveSubmissionFormResponse") String saveSubmissionFormResponse) {
        try {
            if (title != null) {
                recruitmentZoneService.searchVacancyByTitle(title, model);
            } else {
                recruitmentZoneService.getActiveVacancies(model);
            }
            log.info("<-- User Name -->: {} ", principal.getName());

            model.addAttribute("createCandidateApplicationResponse", createCandidateApplicationResponse);
            model.addAttribute("title", title);
            model.addAttribute("saveSubmissionFormResponse", saveSubmissionFormResponse);
            model.addAttribute("internalServerError", internalServerError);
        } catch (Exception e) {
            model.addAttribute("internalServerError", e.getMessage());
        }
        return "home";
    }

    @GetMapping("/aboutus")
    public String aboutUs() {
        return "fragments/info/about-us";
    }

    @GetMapping("/admin")
    public String showBatchForm() {
        return "admin";
    }

    @PostMapping("/start-batch-job")
    public String batchJobEntry(Model model) {
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("created", System.currentTimeMillis())
                .toJobParameters();
        String batchMessage = "";
        try {
            jobLauncher.run(vacancyJob, jobParameters);
            batchMessage = "Batch Successfully Launched";
            model.addAttribute("batchMessage", batchMessage);
            return "admin";
        } catch (JobInstanceAlreadyCompleteException |
                 JobExecutionAlreadyRunningException |
                 JobParametersInvalidException |
                 JobRestartException
                e) {
            log.info("Failed {}", e.getMessage());
            batchMessage = "Batch Failed to launch";
            model.addAttribute("batchMessage", batchMessage);
            return "admin";
        }
    }
}



