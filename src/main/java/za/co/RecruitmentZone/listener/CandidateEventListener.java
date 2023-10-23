package za.co.RecruitmentZone.listener;

import jakarta.persistence.criteria.CriteriaBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import za.co.RecruitmentZone.entity.Application;
import za.co.RecruitmentZone.entity.Candidate;
import za.co.RecruitmentZone.events.Candidate.CandidateAppliedEvent;

@Component
public class CandidateEventListener {

    // Listener for the CandidateAppliedEvent

    private final Logger log = LoggerFactory.getLogger(CandidateEventListener.class);

    @EventListener
    public void onCandidateAppliedEvent(CandidateAppliedEvent event) {
        Application newApplication = event.getApplication();
        Integer candidateID = newApplication.getCandidateID();
        Integer vacancyID = newApplication.getApplicationVacancyID();
        String cvFilePth = newApplication.getCvFilePath();

        // Call the applyForVacancy method from CandidateService

    }

    // send emailNotification to candidate




}
