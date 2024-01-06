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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.co.recruitmentzone.vacancy.entity.Vacancy;
import za.co.recruitmentzone.service.RecruitmentZoneService;

import java.util.List;

@RestController
@RequestMapping("/RecruitmentZone/api")
public class RecruitmentZoneAPIController {

    private final Logger log = LoggerFactory.getLogger(RecruitmentZoneAPIController.class);

    private final RecruitmentZoneService vacancyService;
    private final JobLauncher jobLauncher;
    private final Job vacancyJob;

    public RecruitmentZoneAPIController(RecruitmentZoneService vacancyService, JobLauncher jobLauncher, Job vacancyJob) {
        this.vacancyService = vacancyService;
        this.jobLauncher = jobLauncher;
        this.vacancyJob = vacancyJob;
    }

    @GetMapping("/getAllVacancies")
    public ResponseEntity<List<Vacancy>> getAllVacancies() {
        List<Vacancy> vacancies = vacancyService.getAllVacancies();
        if (!vacancies.isEmpty()){
            return new ResponseEntity<>(vacancies,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/start-batch-job")
    public ResponseEntity<String>  batchJobEntry() {
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("created", System.currentTimeMillis())
                .toJobParameters();
        try {
            jobLauncher.run(vacancyJob, jobParameters);
            return new ResponseEntity<>("Batch Started Successfully", HttpStatus.OK);
        } catch (JobInstanceAlreadyCompleteException |
                 JobExecutionAlreadyRunningException |
                 JobParametersInvalidException |
                 JobRestartException
                e) {
            log.info("Failed {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
/*    @DeleteMapping("/deleteVacancy/{id}")
    public ResponseEntity<String> deleteVacancy(@PathVariable Long id) {
        boolean vacancyDeleted = vacancyService.deleteVacancy(id);
        if (vacancyDeleted) {
            return new ResponseEntity<>("Vacancy Deleted", HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }*/




}
