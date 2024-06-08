package za.co.recruitmentzone.client.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class ClientNoteDTO implements Serializable {
    private Long clientID;
    private LocalDateTime dateCaptured;

    @NotNull(message = "Please enter a note")
    @NotBlank(message = "Note is blank")
    private String comment;
    private Long employeeID;

    public ClientNoteDTO() {
    }

    public ClientNoteDTO(Long clientID, LocalDateTime dateCaptured, String comment) {
        this.clientID = clientID;
        this.dateCaptured = dateCaptured;
        this.comment = comment;
    }

    public String printClientNoteDTO() {
        return "ClientNoteDTO{" +
                "clientID=" + clientID +
                ", dateCaptured=" + dateCaptured +
                ", comment='" + comment + '\'' +
                ", employeeID=" + employeeID +
                '}';
    }
}
