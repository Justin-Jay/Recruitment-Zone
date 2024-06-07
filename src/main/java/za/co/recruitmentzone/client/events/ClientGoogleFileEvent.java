package za.co.recruitmentzone.client.events;

import za.co.recruitmentzone.documents.ClientFileUploadEvent;
import za.co.recruitmentzone.documents.Document;
import za.co.recruitmentzone.documents.FileUploadEvent;

public class ClientGoogleFileEvent extends ClientFileUploadEvent {

    public ClientGoogleFileEvent(Long fileID, String clientName,Document document) {
        super(fileID, clientName,document);
    }
}
