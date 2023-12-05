package za.co.RecruitmentZone.events.listener.Applications;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import za.co.RecruitmentZone.entity.domain.Application;
import za.co.RecruitmentZone.entity.domain.Candidate;
import za.co.RecruitmentZone.events.EventStore.Candidate.SaveSubmissionEvent;
import za.co.RecruitmentZone.service.domainServices.ApplicationService;
import za.co.RecruitmentZone.service.domainServices.CandidateService;

import java.time.LocalDate;

import static za.co.RecruitmentZone.entity.Enums.ApplicationStatus.PENDING;

@Component
public class ApplicationsListener {

    private final Logger log = LoggerFactory.getLogger(ApplicationsListener.class);

    private final ApplicationService applicationService;

    private final CandidateService candidateService;


    public ApplicationsListener(ApplicationService applicationService, CandidateService candidateService) {
        this.applicationService = applicationService;
        this.candidateService = candidateService;
    }

    @EventListener
    public void onSaveSubmissionEvent(SaveSubmissionEvent event) {
        log.info("onSaveSubmissionEvent started ");
        Candidate newCandidate = event.getCandidate();
        newCandidate = candidateService.save(newCandidate);
        log.info("newCandidate = {}",newCandidate);
        Application application = event.getApplication();
        LocalDate date = LocalDate.now();
        application.setDate_received(date.toString());
        application.setSubmission_date(date.toString());
        application.setStatus(PENDING);
        application.setCandidate(newCandidate);
        log.info("application = {}",application);
        applicationService.save(application);

        log.info("onSaveSubmissionEvent finished ");
    }


}
