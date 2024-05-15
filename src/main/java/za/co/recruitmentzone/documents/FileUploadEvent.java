package za.co.recruitmentzone.documents;

import org.springframework.context.ApplicationEvent;

public class FileUploadEvent extends ApplicationEvent {
    private final Long fileID;

    private final Document document;

    public FileUploadEvent(Long fileID, Document document) {
        super(document);
        this.fileID=fileID;
        this.document=document;
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

