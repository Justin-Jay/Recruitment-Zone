package za.co.RecruitmentZone.candidate.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Transient;
import org.springframework.web.multipart.MultipartFile;
import za.co.RecruitmentZone.util.Enums.DocumentType;


public class CandidateFileDTO {
    private Long candidateID;
    @Enumerated(EnumType.STRING)
    private DocumentType documentType;
    @Transient
    private MultipartFile cvFile;

    public CandidateFileDTO() {
    }

    public CandidateFileDTO(Long candidateID, MultipartFile cvFile) {
        this.candidateID = candidateID;
        this.cvFile = cvFile;
    }

    public DocumentType getDocumentType() {
        return documentType;
    }

    public void setDocumentType(DocumentType documentType) {
        this.documentType = documentType;
    }

    public Long getCandidateID() {
        return candidateID;
    }

    public void setCandidateID(Long candidateID) {
        this.candidateID = candidateID;
    }

    public MultipartFile getCvFile() {
        return cvFile;
    }

    public void setCvFile(MultipartFile cvFile) {
        this.cvFile = cvFile;
    }

}


