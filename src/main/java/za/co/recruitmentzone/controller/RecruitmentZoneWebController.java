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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import za.co.recruitmentzone.service.RecruitmentZoneService;


@Controller
public class RecruitmentZoneWebController {


    private final RecruitmentZoneService recruitmentZoneService;

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job vacancyJob;

    private final Logger log = LoggerFactory.getLogger(RecruitmentZoneWebController.class);

    public RecruitmentZoneWebController( RecruitmentZoneService recruitmentZoneService) {
        this.recruitmentZoneService = recruitmentZoneService;
    }

  /*  @GetMapping("/css/**")
    public String redirectToHome() {
        return "redirect:/home";
    }
*/

    @GetMapping(value = {"/", "/home"})
    public String home(Model model) {
        try {
            recruitmentZoneService.getActiveVacancies(model);
        } catch (Exception e) {
            model.addAttribute("internalServerError", e.getMessage());
        }
        return "home";
    }


    @GetMapping("/start-batch-job")
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


    @GetMapping("/aboutus")
    public String aboutUs() {
        return "fragments/info/about-us";
    }

    @GetMapping("/admin")
    public String showBatchForm() {
        return "admin";
    }

}



