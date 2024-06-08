package za.co.recruitmentzone.candidate.events;

import za.co.recruitmentzone.documents.Document;
import za.co.recruitmentzone.documents.FileUploadEvent;


public class CandidateGoogleFileEvent extends FileUploadEvent {
    public CandidateGoogleFileEvent(Long fileID, Document document) {
        super(fileID, document);
    }


}