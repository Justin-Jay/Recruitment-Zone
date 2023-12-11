package za.co.RecruitmentZone.application.service;

import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import za.co.RecruitmentZone.documents.CandidateFile;
import za.co.RecruitmentZone.util.Enums.ApplicationStatus;
import za.co.RecruitmentZone.application.entity.Application;
import za.co.RecruitmentZone.application.repository.ApplicationRepository;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class ApplicationService {
    private final Logger log = LoggerFactory.getLogger(ApplicationService.class);

    private final ApplicationRepository applicationRepository;

    public ApplicationService(ApplicationRepository applicationRepository) {
        this.applicationRepository = applicationRepository;
    }

    public Application save(Application application) {
        return applicationRepository.save(application);
    }



    public List<Application> findApplications() {
        return applicationRepository.findAll();
    }

    public Optional<Application> findApplicationByID(Long id) {
        return applicationRepository.findById(id);
    }

    @Transactional
    public boolean saveUpdatedStatus(Long applicationID, ApplicationStatus applicationStatus){
        log.info("saveUpdatedStatus start");
        log.info("applicationID: {}",applicationID);
        log.info("applicationStatus: {}",applicationStatus);
        Optional<Application> optionalApplication = applicationRepository.findById(applicationID);
        if(optionalApplication.isPresent()){
            log.info("Found application");
            Application application = optionalApplication.get();
            log.info("Application: {}",application);
            application.setStatus(applicationStatus);
            log.info("SAVED");
            return true;
        }
        log.info("FAILED TO SAVE");
        return false;
    }


}
