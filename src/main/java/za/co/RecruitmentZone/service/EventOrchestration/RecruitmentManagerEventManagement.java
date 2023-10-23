package za.co.RecruitmentZone.service.EventOrchestration;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import za.co.RecruitmentZone.entity.Recruiter;
import za.co.RecruitmentZone.repository.RecruitmentManagerRepository;
import za.co.RecruitmentZone.publisher.RecruitmentManagerEventPublisher;

@Service
public class RecruitmentManagerEventManagement {

    private final RecruitmentManagerRepository recruitmentManagerRepository;

    private final RecruitmentManagerEventPublisher managerEventPublisher;
    private final Logger log = LoggerFactory.getLogger(RecruitmentManagerEventManagement.class);

    public RecruitmentManagerEventManagement(RecruitmentManagerRepository recruitmentManagerRepository, RecruitmentManagerEventPublisher managerEventPublisher) {
        this.recruitmentManagerRepository = recruitmentManagerRepository;
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

    // Add more methods for recruitment manager-related operations
}

