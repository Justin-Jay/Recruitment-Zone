package za.co.recruitmentzone.client.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
import za.co.recruitmentzone.util.enums.ClientDocumentType;
import java.io.Serializable;


@Data
public class ClientFileDTO implements Serializable {
    private Long clientID;
    private Long vacancyID;

    @Enumerated(EnumType.STRING)
    private ClientDocumentType documentType;
    @Transient
    @NotNull(message = "Please attach file")
    private MultipartFile fileMultipart;

    public ClientFileDTO() {
    }

    public ClientFileDTO(Long candidateID, MultipartFile fileMultipart) {
        this.clientID = candidateID;
        this.fileMultipart = fileMultipart;
    }

    public String printClientFileDTO() {
        return "ClientFileDTO{" +
                "clientID=" + clientID +
                ", vacancyID=" + vacancyID +
                ", documentType=" + documentType +
                ", fileMultipart is empty =" + fileMultipart.isEmpty() +
                '}';
    }
}


