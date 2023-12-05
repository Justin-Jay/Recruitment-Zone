package za.co.RecruitmentZone.entity.domain;

import jakarta.persistence.*;
import za.co.RecruitmentZone.entity.domain.Client;

import java.time.LocalDateTime;

@Entity
@Table(name = "candidateNote")
public class CandidateNote implements Note  {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "noteID")
    private Long noteID;

    @ManyToOne(
            cascade = {
                    CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH
            })
    @JoinColumn(name = "candidateID")
    private Candidate candidate;

    private LocalDateTime dateCaptured;
    private String comment;

    public CandidateNote() {
    }

    public CandidateNote(Candidate candidate, String comment) {
        this.candidate = candidate;
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

    @Override
    public String toString() {
        return "CandidateNote{" +
                "noteID=" + noteID +
                ", candidate=" + candidate +
                ", dateCaptured=" + dateCaptured +
                ", comment='" + comment + '\'' +
                '}';
    }
}
