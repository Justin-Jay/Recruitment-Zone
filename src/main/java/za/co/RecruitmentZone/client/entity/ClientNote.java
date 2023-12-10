package za.co.RecruitmentZone.client.entity;

import jakarta.persistence.*;
import za.co.RecruitmentZone.util.Note;

import java.time.LocalDateTime;

@Entity
@Table(name = "candidateNote")
public class ClientNote implements Note {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "noteID")
    private Long noteID;

    @ManyToOne(
            cascade = {
                    CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH
            })
    @JoinColumn(name = "clientid")
    private Client client;

    private LocalDateTime dateCaptured;
    private String comment;

    public ClientNote() {
    }

    public ClientNote(String comment) {
        this.comment = comment;
    }

    public ClientNote(Client client, String comment) {
        this.client = client;
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

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    @Override
    public String toString() {
        return "ClientNote{" +
                "noteID=" + noteID +
                ", client=" + client +
                ", dateCaptured=" + dateCaptured +
                ", comment='" + comment + '\'' +
                '}';
    }
}
