package za.co.recruitmentzone.application.service;

import jakarta.transaction.Transactional;
import org.checkerframework.checker.units.qual.A;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import za.co.recruitmentzone.application.dto.ApplicationDTO;
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
        return application.orElseThrow(() -> new ApplicationsNotFoundException("Application Not found " + id));
    }

    public boolean saveUpdatedStatus(ApplicationDTO applicationDTO) {
        log.info("saveUpdatedStatus applicationDTO: {}", applicationDTO.printApplicationDTO());
        Optional<Application> optionalApplication = applicationRepository.findById(applicationDTO.getApplicationID());
        if (optionalApplication.isPresent()) {
            log.info("Found application");
            Application application = optionalApplication.get();
            log.info("Application: {}", application.printApplication());
            log.info("Old status {} ", application.getStatus());
            log.info("New status {} ", applicationDTO.getStatus());
            application.setStatus(applicationDTO.getStatus());
            applicationRepository.save(application);
            log.info("SAVED {} ", application.printApplication());
            return true;
        }
        log.info("FAILED TO SAVE");
        return false;
    }


}
