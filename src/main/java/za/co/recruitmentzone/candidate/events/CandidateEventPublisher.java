package za.co.recruitmentzone.candidate.events;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import za.co.recruitmentzone.application.entity.Application;
import za.co.recruitmentzone.communication.entity.AdminContactMessage;
import za.co.recruitmentzone.communication.entity.ContactMessage;
import za.co.recruitmentzone.communication.events.AdminMessageEvent;
import za.co.recruitmentzone.communication.events.WebsiteMessageEvent;
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
            log.info("Event publishCandidateFileUploadedEvent published");
            return true;
        } catch (Exception e) {
            log.info("<--- publishCandidateGoogleFileEvent exception ---> \n",e);
            log.info("Unable to post event");
            return false;
        }

    }


    public void publishCandidateAppliedEvent(Application application, AdminContactMessage adminContactMessage, ContactMessage contactMessage)  {
        CandidateAppliedEvent candidateAppliedEvent = new CandidateAppliedEvent(application,adminContactMessage,contactMessage);
        log.info("<--- Publishing publishCandidateAppliedEvent ---> ");
        try {
            eventPublisher.publishEvent(candidateAppliedEvent);
            log.info("<--- Event publishCandidateAppliedEvent published ---> ");
        } catch (Exception e) {
            log.info("<--- Unable to publish event --->");
            log.info("<--- publishCandidateAppliedEvent exception ---> \n",e);

        }
    }
}
