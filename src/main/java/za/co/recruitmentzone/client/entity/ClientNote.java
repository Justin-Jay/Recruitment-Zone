package za.co.recruitmentzone.client.entity;

import jakarta.persistence.*;
import za.co.recruitmentzone.employee.entity.Employee;
import za.co.recruitmentzone.util.Note;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "CLIENT_NOTE")
public class ClientNote implements Note, Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "noteID")
    private Long noteID;
    @ManyToOne( )
    @JoinColumn(name = "clientID")
    private Client client;

    @Column(name = "created")
    private LocalDateTime dateCaptured;
    @Column(name = "comment", length = 65535)
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


    public String printClientNote() {
        return "ClientNote{" +
                "noteID=" + noteID +
                ", client id =" + client.getClientID() +
                ", dateCaptured =" + dateCaptured +
                ", comment ='" + comment + '\'' +
                '}';
    }
}
