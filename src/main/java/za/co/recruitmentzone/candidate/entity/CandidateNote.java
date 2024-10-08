package za.co.recruitmentzone.candidate.entity;

import jakarta.persistence.*;
import za.co.recruitmentzone.util.Note;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "CANDIDATE_NOTE")
public class CandidateNote implements Note, Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "noteID")
    private Long noteID;

    @ManyToOne()
    @JoinColumn(name = "candidateID")
    private Candidate candidate;
    @Column(name = "created")
    private LocalDateTime dateCaptured;
    @Column(name = "comment", length = 65535)
    private String comment;

    public CandidateNote() {
    }

    public CandidateNote(String comment) {
        this.comment = comment;
    }

    public CandidateNote(Long noteID, Candidate candidate, LocalDateTime dateCaptured, String comment) {
        this.noteID = noteID;
        this.candidate = candidate;
        this.dateCaptured = dateCaptured;
        this.comment = comment;
    }

    @Override
    public LocalDateTime getDateCaptured() {
        return this.dateCaptured;
    }

    @Override
    public void setDateCaptured(LocalDateTime dateCaptured) {
        this.dateCaptured = LocalDateTime.now();
    }

    @Override
    public String getComment() {
        return this.comment;
    }

    @Override
    public void setComment(String comment) {
        this.comment=comment;
    }

    public Long getNoteID() {
        return noteID;
    }

    public void setNoteID(Long noteID) {
        this.noteID = noteID;
    }

    public Candidate getCandidate() {
        return candidate;
    }

    public void setCandidate(Candidate candidate) {
        this.candidate = candidate;
    }


    public String printCandidateNote() {
        return "CandidateNote{" +
                "noteID=" + noteID +
                ", candidate=" + candidate.getCandidateID() +
                ", dateCaptured=" + dateCaptured +
                ", comment='" + comment + '\'' +
                '}';
    }
}
