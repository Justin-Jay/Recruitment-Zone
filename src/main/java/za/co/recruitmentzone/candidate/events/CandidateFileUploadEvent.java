package za.co.recruitmentzone.candidate.events;

import za.co.recruitmentzone.documents.Document;
import za.co.recruitmentzone.documents.FileUploadEvent;


public class CandidateFileUploadEvent extends FileUploadEvent {
    public CandidateFileUploadEvent(Long fileID, Document document) {
        super(fileID, document);
    }


}