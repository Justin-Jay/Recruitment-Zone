package za.co.recruitmentzone.documents;

import jakarta.persistence.*;
import org.springframework.data.redis.core.RedisHash;
import za.co.recruitmentzone.candidate.entity.Candidate;
import za.co.recruitmentzone.util.enums.DocumentType;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "document")
public class CandidateFile implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "docid")
    private Long fileID;
    private String filename;
    private String contenttype;
    private String filesize;
    private byte[] filedata;
    private String documentLocation;
    @Column(name="created")
    private LocalDateTime created;
    @ManyToOne(
            cascade = {
                    CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH
            })
    @JoinColumn(name = "candidateID")
    private Candidate candidate;
    @Enumerated(EnumType.STRING)
    private DocumentType documentType;
    public CandidateFile() {
    }

    public CandidateFile(Long fileID, String filename, String contenttype, String filesize,  String documentLocation, Candidate candidate, DocumentType documentType) {
        this.fileID = fileID;
        this.filename = filename;
        this.contenttype = contenttype;
        this.filesize = filesize;
        this.documentLocation = documentLocation;
        this.candidate = candidate;
        this.documentType = documentType;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public Long getFileID() {
        return fileID;
    }

    public void setFileID(Long fileID) {
        this.fileID = fileID;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getContenttype() {
        return contenttype;
    }

    public void setContenttype(String contenttype) {
        this.contenttype = contenttype;
    }

    public String getFilesize() {
        return filesize;
    }

    public void setFilesize(String filesize) {
        this.filesize = filesize;
    }

    public byte[] getFiledata() {
        return filedata;
    }

    public void setFiledata(byte[] filedata) {
        this.filedata = filedata;
    }

    public String getDocumentLocation() {
        return documentLocation;
    }

    public void setDocumentLocation(String documentLocation) {
        this.documentLocation = documentLocation;
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

    @Override
    public String toString() {
        return "File{" +
                "fileID=" + fileID +
                ", filename='" + filename + '\'' +
                ", contenttype='" + contenttype + '\'' +
                ", filesize='" + filesize + '\'' +
                ", documentLocation='" + documentLocation + '\'' +
                ", candidate=" + candidate +
                ", documentType=" + documentType +
                '}';
    }
}
