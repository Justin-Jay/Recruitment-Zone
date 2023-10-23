package za.co.RecruitmentZone.listener;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.tika.Tika;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import za.co.RecruitmentZone.entity.Candidate;
import za.co.RecruitmentZone.events.Candidate.CandidateAppliedEvent;
import za.co.RecruitmentZone.events.Candidate.FileUploadEvent;
import za.co.RecruitmentZone.service.EventOrchestration.CandidateEventManagement;

import java.io.ByteArrayInputStream;
import java.io.IOException;

@Component
public class CandidateEventListener {

    // Listener for the CandidateAppliedEvent

    private final Logger log = LoggerFactory.getLogger(CandidateEventListener.class);

    @EventListener
    public void onCandidateAppliedEvent(CandidateAppliedEvent event) {
        Candidate candidate = event.getCandidate();
        String resumeFilePath = event.getResumeFilePath();

        // Call the applyForVacancy method from CandidateService
       // CandidateEventManagement.publishCandidateAppliedEvent(candidate);
    }




}
