package za.co.RecruitmentZone.client.dto;

import java.time.LocalDateTime;

public class ClientNoteDTO {
    private Long clientID;
    private LocalDateTime dateCaptured;
    private String comment;

    public ClientNoteDTO() {
    }

    public ClientNoteDTO(Long clientID, LocalDateTime dateCaptured, String comment) {
        this.clientID = clientID;
        this.dateCaptured = dateCaptured;
        this.comment = comment;
    }

    public Long getClientID() {
        return clientID;
    }

    public void setClientID(Long clientID) {
        this.clientID = clientID;
    }

    public LocalDateTime getDateCaptured() {
        return dateCaptured;
    }

    public void setDateCaptured(LocalDateTime dateCaptured) {
        this.dateCaptured = dateCaptured;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
