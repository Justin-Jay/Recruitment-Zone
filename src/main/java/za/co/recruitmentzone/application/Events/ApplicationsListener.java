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
import za.co.recruitmentzone.candidate.service.CandidateFileService;
import za.co.recruitmentzone.storage.StorageService;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static za.co.recruitmentzone.util.enums.ApplicationStatus.PENDING;

@Component
public class ApplicationsListener {

    private final Logger log = LoggerFactory.getLogger(ApplicationsListener.class);

    private final ApplicationService applicationService;

    private final CandidateFileService candidateFileService;

    private final CandidateService candidateService;
    private final StorageService storageService;



    public ApplicationsListener(ApplicationService applicationService, CandidateFileService candidateFileService, CandidateService candidateService, StorageService storageService) {
        this.applicationService = applicationService;
        this.candidateFileService = candidateFileService;
        this.candidateService = candidateService;
        this.storageService = storageService;
    }

    @EventListener
    public void onSaveSubmissionEvent(SaveSubmissionEvent event) {
        log.info("onSaveSubmissionEvent started ");
        Candidate newCandidate = event.getCandidate();
        newCandidate = candidateService.save(newCandidate);
        log.info("newCandidate = {}",newCandidate);
        Application application = event.getApplication();
        application.setDate_received(Timestamp.valueOf(LocalDateTime.now()).toString());
        application.setStatus(PENDING);
        application.setCandidate(newCandidate);
        log.info("application = {}",application);
        applicationService.save(application);

        log.info("onSaveSubmissionEvent finished ");
    }






}
