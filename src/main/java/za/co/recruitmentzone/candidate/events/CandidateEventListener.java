package za.co.recruitmentzone.candidate.events;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import za.co.recruitmentzone.candidate.entity.CandidateFile;
import za.co.recruitmentzone.candidate.service.CandidateFileService;
import za.co.recruitmentzone.communication.entity.AdminContactMessage;
import za.co.recruitmentzone.communication.events.publisher.EmailEventPublisher;
import za.co.recruitmentzone.storage.StorageService;


@Component
public class CandidateEventListener {

    private final StorageService storageService;

    private final CandidateFileService candidateFileService;

    private final EmailEventPublisher emailEventPublisher;

    private final Logger log = LoggerFactory.getLogger(CandidateEventListener.class);

    public CandidateEventListener(StorageService storageService, CandidateFileService candidateFileService, EmailEventPublisher emailEventPublisher) {
        this.storageService = storageService;
        this.candidateFileService = candidateFileService;
        this.emailEventPublisher = emailEventPublisher;
    }

    @EventListener
    public void onCandidateGoogleFileEvent(CandidateGoogleFileEvent event) {
        log.info("<--  FileUploadEvent event {} -->", event.getFileID());
        String message = "";
        try {
            String fileResponse = storageService.uploadFile(event);
            if (!fileResponse.contains("File upload failed")) {
                log.info("<--  File Upload Success {} -->", fileResponse);

                CandidateFile file = candidateFileService.findFileById(event.getFileID());
                file.setGcpAddress(fileResponse);
                candidateFileService.saveCandidateFile(file);

                message = " onCandidateFileUploadEvent \n" +
                        "   File Upload to GCP Success \n " +
                        event.printEvent() + '\n' +
                        "fileResponse: " + fileResponse;

            } else {
                log.debug("File Upload to GCP failed {}", event.printEvent());
                log.debug("<--  uploadToGCloud fileResponse {} -->", fileResponse);

                CandidateFile file = candidateFileService.findFileById(event.getFileID());
                file.setGcpAddress("FILE UPLOAD FAILED");
                candidateFileService.saveCandidateFile(file);

                message = " onCandidateFileUploadEvent \n" +
                        "   File Upload to GCP Failed \n " +
                        event.printEvent();
            }

        } catch (Exception e) {
            log.error(e.getMessage());
            message = " onCandidateFileUploadEvent \n" +
                    "   File Upload to GCP Failed \n " +
                    e.getMessage();
        }


        if (!message.isEmpty()) {
            emailEventPublisher.publishFileUpload( new AdminContactMessage("CANDIDATE_FILE_UPLOAD",message));
        }

    }

}





