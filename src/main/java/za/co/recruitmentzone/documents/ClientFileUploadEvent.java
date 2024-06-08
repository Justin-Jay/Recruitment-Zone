package za.co.recruitmentzone.documents;

import org.springframework.context.ApplicationEvent;

public class ClientFileUploadEvent extends ApplicationEvent {
    private final Long fileID;

    private final String clientName;

    private final Document document;

    public ClientFileUploadEvent(Long fileID, String clientName, Document document) {
        super(document);
        this.fileID=fileID;
        this.clientName = clientName;
        this.document=document;
    }

    public String getClientName() {
        return clientName;
    }

    public Long getFileID() {
        return fileID;
    }

    public Document getDocument() {
        return document;
    }

    public String printEvent() {
        return "Event{" +
                "fileID=" + fileID +
                "document=" + document.printDocument() + '\'' +
                ", destination bucket folder ='" + document.getBucketFolder() + '\'' +
                '}';
    }


}

