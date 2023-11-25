/*
package za.co.RecruitmentZone.events.listener.Applications;


import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import za.co.RecruitmentZone.entity.domain.Application;
import za.co.RecruitmentZone.entity.domain.Candidate;
import za.co.RecruitmentZone.entity.domain.CandidateApplication;
import za.co.RecruitmentZone.service.domainServices.ApplicationService;
import za.co.RecruitmentZone.service.domainServices.CandidateApplicationService;
import za.co.RecruitmentZone.service.domainServices.CandidateService;

import java.time.LocalDate;

@Component
public class ApplicationsListener {

    private final ApplicationService applicationService;

    private final CandidateService candidateService;

    private final CandidateApplicationService candidateApplicationService;
    public ApplicationsListener(ApplicationService applicationService, CandidateService candidateService, CandidateApplicationService candidateApplicationService) {
        this.applicationService = applicationService;
        this.candidateService = candidateService;
        this.candidateApplicationService = candidateApplicationService;
    }

    @EventListener
    public void onApplicationSubmitted(ApplicationSubmittedEvent event) {
        Candidate newCandidate = event.getCandidate();
        newCandidate = candidateService.save(newCandidate);

        Application application = event.getApplication();
        LocalDate date = LocalDate.now();
        application.setDate_received(date.toString());
        application.setSubmission_date(date.toString());
        application.setStatus(1);
        application.setCandidateID(newCandidate.getCandidateID());
        application.setVacancyID(event.getApplication().getVacancyID());

        application = applicationService.save(application);

        CandidateApplication ca = new CandidateApplication();

        ca.setApplicationID(application.getApplicationID());
        ca.setCandidateID(newCandidate.getCandidateID());

        candidateApplicationService.save(ca);
    }


}
*/
