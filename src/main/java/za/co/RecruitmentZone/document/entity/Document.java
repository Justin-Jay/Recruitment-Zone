package za.co.RecruitmentZone.document.entity;

import jakarta.persistence.*;
import za.co.RecruitmentZone.candidate.entity.Candidate;
import za.co.RecruitmentZone.util.DocumentType;

@Entity
@Table(name = "document")
public class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long docID;
    private String documentLocation;
    @ManyToOne(
            cascade = {
                    CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH
            })
    @JoinColumn(name = "candidateID")
    private Candidate candidate;
    @Enumerated(EnumType.STRING)
    private DocumentType documentType;

    public Document() {
    }

    public Document(Long docID, String documentLocation, Candidate candidate, DocumentType documentType) {
        this.docID = docID;
        this.documentLocation = documentLocation;
        this.candidate = candidate;
        this.documentType = documentType;
    }

    public String getDocumentLocation() {
        return documentLocation;
    }

    public void setDocumentLocation(String documentLocation) {
        this.documentLocation = documentLocation;
    }

    public Long getDocID() {
        return docID;
    }

    public void setDocID(Long docID) {
        this.docID = docID;
    }

    public Candidate getCandidate() {
        return candidate;
    }

    public void setCandidate(Candidate candidate) {
        this.candidate = candidate;
    }

    public DocumentType getDocumentType() {
        return documentType;
    }

    public void setDocumentType(DocumentType documentType) {
        this.documentType = documentType;
    }
}
