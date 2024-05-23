package za.co.recruitmentzone.candidate.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
import za.co.recruitmentzone.util.enums.CandidateDocumentType;

import java.io.Serializable;


@Data
public class CandidateFileDTO implements Serializable {
    private Long candidateID;
    private String candidateIDNumber;
    
    @NotNull(message = "Make selection")
    @Enumerated(EnumType.STRING)
    private CandidateDocumentType documentType;

    @NotNull(message = "Please attach file")
    @Transient
    private MultipartFile documentAttachment;

    public CandidateFileDTO() {
    }

    public CandidateFileDTO(Long candidateID, MultipartFile documentAttachment) {
        this.candidateID = candidateID;
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


