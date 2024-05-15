package za.co.recruitmentzone.candidate.entity;

import jakarta.persistence.*;
import za.co.recruitmentzone.documents.Document;
import za.co.recruitmentzone.util.enums.CandidateDocumentType;


import java.io.File;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "CANDIDATE_FILE")
public class CandidateFile implements Document, Serializable {
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

    @Column(name = "gcpAddress")
    private String gcpAddress;

    @Column(name = "created")
    private LocalDateTime created;
    @ManyToOne(
            cascade = {
                    CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH
            })
    @JoinColumn(name = "candidateID")
    private Candidate candidate;
    @Enumerated(EnumType.STRING)
    private CandidateDocumentType candidateDocumentType;

    //ClientDocumentType
    public CandidateFile() {
    }

    public CandidateFile(Long fileID, String file_name, String content_type, String file_size, String documentLocation, Candidate candidate, CandidateDocumentType CandidateDocumentType) {
        this.fileID = fileID;
        this.file_name = file_name;
        this.content_type = content_type;
        this.file_size = file_size;
        this.documentLocation = documentLocation;
        this.candidate = candidate;
        this.candidateDocumentType = CandidateDocumentType;
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

    @Override
    public String getBucketFolder() {
        return "CANDIDATE_FILES";
    }

    @Override
    public Long getId() {
        return this.candidate.getCandidateID();
    }

    public CandidateDocumentType getCandidateDocumentType() {
        return candidateDocumentType;
    }

    @Override
    public String getDocType() {
        return candidateDocumentType.toString();
    }




    @Override
    public File getFile() {
        return this.documentLocation.equals("") ? null : new File(this.documentLocation);
    }

    public void setCandidateDocumentType(CandidateDocumentType documentType) {
        this.candidateDocumentType = documentType;
    }

    public String getGcpAddress() {
        return gcpAddress;
    }

    public void setGcpAddress(String gcpAddress) {
        this.gcpAddress = gcpAddress;
    }

    @Override
    public String printDocument() {
        return "File{" +
                "fileID=" + fileID +
                ", filename='" + file_name + '\'' +
                ", contenttype='" + content_type + '\'' +
                ", filesize='" + file_size + '\'' +
                ", documentLocation='" + documentLocation + '\'' +
                ", candidate=" + candidate.getCandidateID() +
                ", documentType=" + candidateDocumentType +
                '}';
    }

    @Override
    public String convertDocType() {
        String returnString = "";

        switch (candidateDocumentType) {
            case CURRICULUM_VITAE:
                returnString = "Curriculum Vitae";
                break;
            case QUALIFICATION_CERTIFICATE:
                returnString = "Qualification Certificate";
                break;
            case ID_DOCUMENT:
                returnString = "ID Document";
                break;
            case DRIVERS_LICENCE:
                returnString = "Drivers Licence";
                break;
            case PAYSLIP:
                returnString = "Pay Slip";
                break;
        }

        return returnString;
    }
}
