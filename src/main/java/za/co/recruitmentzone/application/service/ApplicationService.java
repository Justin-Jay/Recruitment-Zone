package za.co.recruitmentzone.application.service;

import jakarta.transaction.Transactional;
import org.checkerframework.checker.units.qual.A;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import za.co.recruitmentzone.application.exception.ApplicationsNotFoundException;
import za.co.recruitmentzone.util.enums.ApplicationStatus;
import za.co.recruitmentzone.application.entity.Application;
import za.co.recruitmentzone.application.repository.ApplicationRepository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
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

    public Application findApplicationByID(Long id) {
        Optional<Application> application = applicationRepository.findById(id);
        return application.orElseThrow(()-> new ApplicationsNotFoundException("Application Not found "+id));
    }

    public boolean saveUpdatedStatus(Long applicationID, ApplicationStatus applicationStatus){
        log.info("saveUpdatedStatus start");
        log.info("applicationID: {}",applicationID);
        log.info("applicationStatus: {}",applicationStatus);
        Optional<Application> optionalApplication = applicationRepository.findById(applicationID);
        if(optionalApplication.isPresent()){
            log.info("Found application");
            Application application = optionalApplication.get();
            log.info("Application: {}",application.printApplication());
            application.setStatus(applicationStatus);
            log.info("SAVED");
            return true;
        }
        log.info("FAILED TO SAVE");
        return false;
    }


}
