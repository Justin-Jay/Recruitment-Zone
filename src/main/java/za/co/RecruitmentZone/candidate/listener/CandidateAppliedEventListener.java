package za.co.RecruitmentZone.candidate.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import za.co.RecruitmentZone.candidate.CandidateAppliedEvent;
import za.co.RecruitmentZone.candidate.service.CandidateService;
import za.co.RecruitmentZone.entity.Candidate;

@Component
class CandidateAppliedEventListener {

    // Listener for the CandidateAppliedEvent

    private final CandidateService candidateService;

    @Autowired
    public CandidateAppliedEventListener(CandidateService candidateService) {
        this.candidateService = candidateService;
    }

    @EventListener
    public void handleCandidateAppliedEvent(CandidateAppliedEvent event) {
        Candidate candidate = event.getCandidate();
        String resumeFilePath = event.getResumeFilePath();

        // Call the applyForVacancy method from CandidateService
        candidateService.applyForVacancy(candidate);
    }

}
