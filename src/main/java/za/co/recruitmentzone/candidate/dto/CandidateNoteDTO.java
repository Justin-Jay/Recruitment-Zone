package za.co.recruitmentzone.candidate.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

public class CandidateNoteDTO implements Serializable {
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


    public String printCandidateNote() {
        return "CandidateNoteDTO{" +
                "candidateID=" + candidateID +
                ", dateCaptured=" + dateCaptured +
                ", comment='" + comment + '\'' +
                '}';
    }
}
