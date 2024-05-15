package za.co.recruitmentzone.client.events;

import za.co.recruitmentzone.documents.Document;
import za.co.recruitmentzone.documents.FileUploadEvent;

public class ClientFileUploadEvent extends FileUploadEvent {

    public ClientFileUploadEvent(Long fileID, Document document) {
        super(fileID, document);
    }
}
