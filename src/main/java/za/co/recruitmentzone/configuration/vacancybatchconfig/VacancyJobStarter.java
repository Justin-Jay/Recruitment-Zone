package za.co.recruitmentzone.configuration.vacancybatchconfig;

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
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class VacancyJobStarter {

    private final Logger log = LoggerFactory.getLogger(VacancyJobStarter.class);

    private final JobLauncher jobLauncher;
    private final Job vacancyJob;

    public VacancyJobStarter(JobLauncher jobLauncher, Job vacancyJob) {
        this.jobLauncher = jobLauncher;
        this.vacancyJob = vacancyJob;
    }


    // This method will be executed every 5 minutes
    //@Scheduled(cron = "0 */5 * * * *")
    // This method will be executed every day at 1 AM
    //  @Scheduled(cron = "0 0 1 * * ?")
    // This method executes every minute
    //@Scheduled(cron = "0 * * * * ?")
    public void vacancyJob() {
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("jobStartTime", System.currentTimeMillis())
                .toJobParameters();
        try {
            jobLauncher.run(vacancyJob, jobParameters);
        } catch (JobInstanceAlreadyCompleteException |
                 JobExecutionAlreadyRunningException |
                 JobParametersInvalidException |
                 JobRestartException
                e) {
            log.info("Failed {}", e.getMessage());
        }
    }


}
