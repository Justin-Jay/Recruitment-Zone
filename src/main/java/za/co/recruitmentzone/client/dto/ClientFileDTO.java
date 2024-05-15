package za.co.recruitmentzone.client.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Transient;
import org.springframework.web.multipart.MultipartFile;
import za.co.recruitmentzone.util.enums.ClientDocumentType;
import java.io.Serializable;


public class ClientFileDTO implements Serializable {
    private Long clientID;

    @Enumerated(EnumType.STRING)
    private ClientDocumentType documentType; // ClientDocumentType
    @Transient
    private MultipartFile fileMultipart;

    public ClientFileDTO() {
    }

    public ClientFileDTO(Long candidateID, MultipartFile fileMultipart) {
        this.clientID = candidateID;
        this.fileMultipart = fileMultipart;
    }


    public Long getClientID() {
        return clientID;
    }

    public void setClientID(Long clientID) {
        this.clientID = clientID;
    }

    public ClientDocumentType getDocumentType() {
        return documentType;
    }

    public void setDocumentType(ClientDocumentType documentType) {
        this.documentType = documentType;
    }

    public MultipartFile getFileMultipart() {
        return fileMultipart;
    }

    public void setFileMultipart(MultipartFile fileMultipart) {
        this.fileMultipart = fileMultipart;
    }


    public String printClientFileDTO() {
        return "ClientFileDTO{" +
                "clientID=" + clientID +
                ", documentType=" + documentType +
                ", fileMultipart is empty =" + fileMultipart.isEmpty() +
                '}';
    }
}


