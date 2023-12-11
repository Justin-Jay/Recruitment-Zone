package za.co.RecruitmentZone.candidate.dto;

import java.time.LocalDateTime;

public class CandidateNoteDTO {
    private Long candidateID;
    private LocalDateTime dateCaptured;
    private String comment;

    public CandidateNoteDTO() {
    }
    public CandidateNoteDTO(Long candidateID, LocalDateTime dateCaptured, String comment) {
        this.candidateID = candidateID;
        this.dateCaptured = dateCaptured;
        this.comment = comment;
    }

    public Long getCandidateID() {
        return candidateID;
    }

    public void setCandidateID(Long candidateID) {
        this.candidateID = candidateID;
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
