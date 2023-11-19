package za.co.RecruitmentZone.service.domainServices;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import za.co.RecruitmentZone.entity.domain.Application;
import za.co.RecruitmentZone.entity.domain.Vacancy;
import za.co.RecruitmentZone.repository.ApplicationRepository;

import java.util.List;

@Service
public class ApplicationService {
    private final Logger log = LoggerFactory.getLogger(ApplicationService.class);

    private final ApplicationRepository applicationRepository;

    public ApplicationService(ApplicationRepository applicationRepository) {
        this.applicationRepository = applicationRepository;
    }
    public Application save(Application application){
        return applicationRepository.save(application);
    }

    public List<Application> findApplications(){
        return applicationRepository.findAll();
    }

}
