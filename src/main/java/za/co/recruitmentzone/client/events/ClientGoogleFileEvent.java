package za.co.recruitmentzone.client.events;

import za.co.recruitmentzone.documents.Document;
import za.co.recruitmentzone.documents.FileUploadEvent;

public class ClientGoogleFileEvent extends FileUploadEvent {

    public ClientGoogleFileEvent(Long fileID, Document document) {
        super(fileID, document);
    }
}
