package za.co.recruitmentzone.client.events;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import za.co.recruitmentzone.client.service.ClientFileService;
import za.co.recruitmentzone.client.entity.ClientFile;
import za.co.recruitmentzone.storage.StorageService;

@Component
public class ClientEventListener {

    private final Logger log = LoggerFactory.getLogger(ClientEventListener.class);

    private final StorageService storageService;

    private final ClientFileService cLientFileService;


    public ClientEventListener(StorageService storageService, ClientFileService cLientFileService) {
        this.storageService = storageService;
        this.cLientFileService = cLientFileService;
    }

    // ClientFileUploadEvent
    @EventListener
    public void onClientGoogleFileEvent(ClientGoogleFileEvent event) {
        log.info("<--  onClientFileUploadEvent event {} -->", event.getFileID());
        try {
            String fileResponse = storageService.uploadFile(event);
            if (!fileResponse.contains("File upload failed")) {
                log.info("<--  uploadToGCloud fileResponse {} -->", fileResponse);

                // set fileResponse to be gcpAddress on the CandidateFile in DB , event.getFileID()
                ClientFile file = cLientFileService.findFileById(event.getFileID());
                 // recruitmentzoneapplication-dev ,

                file.setGcpAddress(fileResponse);
                cLientFileService.saveClientFile(file);

            } else {
                log.debug("File Upload to GCP failed {}", event.printEvent());

                ClientFile file = cLientFileService.findFileById(event.getFileID());
                file.setGcpAddress("FILE UPLOAD FAILED");
                cLientFileService.saveClientFile(file);

            }

        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }


}
