package za.co.recruitmentzone.client.events;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import za.co.recruitmentzone.documents.Document;

@Service
public class ClientEventPublisher {
    private final ApplicationEventPublisher eventPublisher;
    private final Logger log = LoggerFactory.getLogger(ClientEventPublisher.class);

    public ClientEventPublisher(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }


    public boolean publishClientFileUploadEvent(Long fileID, Document document) {
        ClientFileUploadEvent clientFileUploadEvent = new ClientFileUploadEvent(fileID,document);
        log.info("Executing publishClientFileUploadEvent");
        try {
            eventPublisher.publishEvent(clientFileUploadEvent);
            log.info("EVENT publishClientFileUploadEvent POSTED");
            return true;
        } catch (Exception e) {
            log.error("Unable to post event");
            return false;
        }
    }


}
