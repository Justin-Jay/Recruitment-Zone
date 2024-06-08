package za.co.recruitmentzone.candidate.events;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import za.co.recruitmentzone.documents.Document;

@Component
public class CandidateEventPublisher {
    private final ApplicationEventPublisher eventPublisher;
    private final Logger log = LoggerFactory.getLogger(CandidateEventPublisher.class);

    public CandidateEventPublisher(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }


    public boolean publishCandidateGoogleFileEvent(Long fileID, Document document) {
        CandidateGoogleFileEvent candidateFileUploadEvent = new CandidateGoogleFileEvent(fileID,document);
        log.info("Executing publishCandidateFileUploadedEvent");
        try {
            eventPublisher.publishEvent(candidateFileUploadEvent);
            log.info("EVENT publishCandidateFileUploadedEvent POSTED");
            return true;
        } catch (Exception e) {
            log.error("Unable to post event");
            return false;
        }

    }


}
