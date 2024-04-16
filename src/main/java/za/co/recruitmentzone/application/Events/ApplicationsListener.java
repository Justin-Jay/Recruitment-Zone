package za.co.recruitmentzone.application.Events;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import za.co.recruitmentzone.application.entity.Application;
import za.co.recruitmentzone.candidate.entity.Candidate;
import za.co.recruitmentzone.candidate.events.SaveSubmissionEvent;
import za.co.recruitmentzone.application.service.ApplicationService;
import za.co.recruitmentzone.candidate.service.CandidateService;

import java.time.LocalDate;

import static za.co.recruitmentzone.util.enums.ApplicationStatus.PENDING;

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
    // FileUploadEvent


}
