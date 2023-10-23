package za.co.RecruitmentZone.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import za.co.RecruitmentZone.repository.ApplicationUserRepository;
import za.co.RecruitmentZone.publisher.RecruitmentManagerEventPublisher;

@Service
public class RecruitmentManagerService {
    private final ApplicationUserRepository applicationUserRepository;

    private final RecruitmentManagerEventPublisher managerEventPublisher;
    private final Logger log = LoggerFactory.getLogger(RecruitmentManagerService.class);

    public RecruitmentManagerService(ApplicationUserRepository applicationUserRepository, RecruitmentManagerEventPublisher managerEventPublisher) {
        this.applicationUserRepository = applicationUserRepository;
        this.managerEventPublisher = managerEventPublisher;
    }

    public boolean publishRecruiterCreateEvent(String json) {
        boolean result = false;
        try {

            result = managerEventPublisher.publishRecruiterCreateEvent(json);

        } catch (Exception e) {
            log.info("Unable to save" + e.getMessage());
        }
        return result;
    }


    public boolean publishRecruiterSuspendedEvent(Integer recruiterID) {
        boolean result = false;
    /*    try {

            result = managerEventPublisher.publishRecruiterCreateEvent(json);

        } catch (Exception e) {
            log.info("Unable to save" + e.getMessage());
        }*/
        return result;
    }

    public boolean publishRecruiterAmendEvent(String json) {
        boolean result = false;
    /*    try {

            result = managerEventPublisher.publishRecruiterCreateEvent(json);

        } catch (Exception e) {
            log.info("Unable to save" + e.getMessage());
        }*/
        return result;
    }


    // Add more methods for recruitment manager-related operations
}

