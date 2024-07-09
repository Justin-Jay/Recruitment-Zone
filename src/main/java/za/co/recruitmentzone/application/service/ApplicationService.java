package za.co.recruitmentzone.application.service;

import jakarta.transaction.Transactional;
import org.checkerframework.checker.units.qual.A;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import za.co.recruitmentzone.application.dto.ApplicationDTO;
import za.co.recruitmentzone.application.exception.ApplicationsNotFoundException;
import za.co.recruitmentzone.util.enums.ApplicationStatus;
import za.co.recruitmentzone.application.entity.Application;
import za.co.recruitmentzone.application.repository.ApplicationRepository;
import za.co.recruitmentzone.vacancy.entity.Vacancy;

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

    public Application findApplicationByID(Long id) throws ApplicationsNotFoundException {
        Optional<Application> application = applicationRepository.findById(id);
        return application.orElseThrow(() -> new ApplicationsNotFoundException("Application Not found " + id));
    }

    public boolean saveUpdatedStatus(ApplicationDTO applicationDTO) throws ApplicationsNotFoundException {
        log.info("saveUpdatedStatus applicationDTO: {}", applicationDTO.printApplicationDTO());
        Optional<Application> optionalApplication = applicationRepository.findById(applicationDTO.getApplicationID());
        if (optionalApplication.isPresent()) {
            Application application = optionalApplication.get();
            application.setStatus(applicationDTO.getStatus());
            applicationRepository.save(application);
            return true;
        }
        else throw new ApplicationsNotFoundException("Application Not found " + applicationDTO.getApplicationID());

    }
    public Page<Application> findPaginatedApplications(int pageNo, int pageSize, String sortField, String sortDirection){
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name())? Sort.by(sortField).ascending() : Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(pageNo-1, pageSize, sort);
        return applicationRepository.findAll(pageable);
    }
    public Page<Application> findPaginatedApplications(int pageNo, int pageSize, String sortField, String sortDirection, Vacancy vacancy){
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name())? Sort.by(sortField).ascending() : Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(pageNo-1, pageSize, sort);
        return applicationRepository.findAllByVacancy(pageable,vacancy);
    }
}
