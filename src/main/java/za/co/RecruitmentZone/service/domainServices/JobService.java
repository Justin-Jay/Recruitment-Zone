package za.co.RecruitmentZone.service.domainServices;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import za.co.RecruitmentZone.entity.domain.Job;
import za.co.RecruitmentZone.repository.JobRepository;

import java.util.List;

@Service
public class JobService {
    private final JobRepository jobRepository;
    private final Logger log = LoggerFactory.getLogger(JobService.class);

    public JobService(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }
    public List<Job> getJobs(){
        return jobRepository.findAll();
    }


}
