package za.co.RecruitmentZone.publisher;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import za.co.RecruitmentZone.entity.Recruiter;
import za.co.RecruitmentZone.events.Recruiter.RecruiterCreateEvent;

import java.time.Clock;

@Service
public class RecruitmentManagerEventPublisher {

    private final ApplicationEventPublisher eventPublisher;
    private final Logger log = LoggerFactory.getLogger(RecruitmentManagerEventPublisher.class);

    public RecruitmentManagerEventPublisher(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    public boolean publishRecruiterCreateEvent(String json){
        try {
            Gson gson = new Gson();
            Recruiter newRecruiter = gson.fromJson(json, Recruiter.class);
            Clock baseClock = Clock.systemDefaultZone();
            RecruiterCreateEvent event = new RecruiterCreateEvent(newRecruiter,baseClock);
            eventPublisher.publishEvent(event);
            return true;
        } catch (Exception e) {
            log.info("");
            return false;
        }
    }



    // Add Recruiter
    // Amend Recruiter details
    // Suspend Recruiter

}
