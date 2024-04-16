package za.co.recruitmentzone.documents;

import jakarta.persistence.*;
import za.co.recruitmentzone.candidate.entity.Candidate;
import za.co.recruitmentzone.util.enums.DocumentType;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "DOCUMENT")
public class CandidateFile implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fileID")
    private Long fileID;
    @Column(name = "file_name")
    private String file_name;
    @Column(name = "content_type")
    private String content_type;
    @Column(name = "file_size")
    private String file_size;
    @Column(name = "file_data")
    private byte[] file_data;
    @Column(name = "path")
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

    public CandidateFile(Long fileID, String file_name, String content_type, String file_size, String documentLocation, Candidate candidate, DocumentType documentType) {
        this.fileID = fileID;
        this.file_name = file_name;
        this.content_type = content_type;
        this.file_size = file_size;
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

    public String getFile_name() {
        return file_name;
    }

    public void setFile_name(String filename) {
        this.file_name = filename;
    }

    public String getContent_type() {
        return content_type;
    }

    public void setContent_type(String contenttype) {
        this.content_type = contenttype;
    }

    public String getFile_size() {
        return file_size;
    }

    public void setFile_size(String filesize) {
        this.file_size = filesize;
    }

    public byte[] getFile_data() {
        return file_data;
    }

    public void setFile_data(byte[] filedata) {
        this.file_data = filedata;
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
                ", filename='" + file_name + '\'' +
                ", contenttype='" + content_type + '\'' +
                ", filesize='" + file_size + '\'' +
                ", documentLocation='" + documentLocation + '\'' +
                ", candidate=" + candidate +
                ", documentType=" + documentType +
                '}';
    }
}
