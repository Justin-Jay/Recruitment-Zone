package za.co.recruitmentzone.candidate.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Transient;
import org.springframework.web.multipart.MultipartFile;
import za.co.recruitmentzone.util.enums.CandidateDocumentType;

import java.io.Serializable;


public class CandidateFileDTO implements Serializable {
    private Long candidateID;
    private String candidateIDNumber;
    @Enumerated(EnumType.STRING)
    private CandidateDocumentType documentType;
    @Transient
    private MultipartFile documentAttachment;

    public CandidateFileDTO() {
    }

    public CandidateFileDTO(Long candidateID, MultipartFile documentAttachment) {
        this.candidateID = candidateID;
        this.documentAttachment = documentAttachment;
    }

    public String getCandidateIDNumber() {
        return candidateIDNumber;
    }

    public void setCandidateIDNumber(String candidateIDNumber) {
        this.candidateIDNumber = candidateIDNumber;
    }


    public CandidateDocumentType getDocumentType() {
        return documentType;
    }

    public Long getCandidateID() {
        return candidateID;
    }

    public void setDocumentType(CandidateDocumentType documentType) {
        this.documentType = documentType;
    }

    public void setCandidateID(Long candidateID) {
        this.candidateID = candidateID;
    }

    public MultipartFile getDocumentAttachment() {
        return documentAttachment;
    }

    public void setDocumentAttachment(MultipartFile documentAttachment) {
        this.documentAttachment = documentAttachment;
    }

    public String printCandidateFileDTO() {
        return "CandidateFileDTO{" +
                "candidateID=" + candidateID +
                ", candidateIDNumber='" + candidateIDNumber + '\'' +
                ", documentType=" + documentType +
                ", documentAttachment is not empty =" + documentAttachment.isEmpty() +
                '}';
    }
}


