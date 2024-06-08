package za.co.recruitmentzone.candidate.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class CandidateNoteDTO implements Serializable {
    private Long candidateID;
    private LocalDateTime dateCaptured;

    @NotNull(message = "Please enter a note")
    @NotBlank(message = "Note is blank")
    private String comment;

    public CandidateNoteDTO() {
    }
    public CandidateNoteDTO(Long candidateID, LocalDateTime dateCaptured, String comment) {
        this.candidateID = candidateID;
        this.dateCaptured = dateCaptured;
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
