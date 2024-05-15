package za.co.recruitmentzone.client.entity;

import jakarta.persistence.*;
import za.co.recruitmentzone.documents.Document;
import za.co.recruitmentzone.util.enums.ClientDocumentType;

import java.io.File;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "CLIENT_FILE")
public class ClientFile implements Document,Serializable {
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
    @Enumerated(EnumType.STRING)
    private ClientDocumentType documentType;
    @Column(name = "gcpAddress")
    private String gcpAddress;

    @Column(name="created")
    private LocalDateTime created;
    @ManyToOne(
            cascade = {
                    CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH
            })
    @JoinColumn(name = "clientID")
    private Client client;


    public ClientFile() {
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

    public void setFile_name(String file_name) {
        this.file_name = file_name;
    }

    public String getContent_type() {
        return content_type;
    }

    public void setContent_type(String content_type) {
        this.content_type = content_type;
    }

    public String getFile_size() {
        return file_size;
    }

    public void setFile_size(String file_size) {
        this.file_size = file_size;
    }

    public byte[] getFile_data() {
        return file_data;
    }

    public void setFile_data(byte[] file_data) {
        this.file_data = file_data;
    }

    public String getDocumentLocation() {
        return documentLocation;
    }

    public void setDocumentLocation(String documentLocation) {
        this.documentLocation = documentLocation;
    }

    public String getGcpAddress() {
        return gcpAddress;
    }

    public void setGcpAddress(String gcpAddress) {
        this.gcpAddress = gcpAddress;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public ClientDocumentType getDocumentType() {
        return documentType;
    }

    public void setDocumentType(ClientDocumentType documentType) {
        this.documentType = documentType;
    }

    @Override
    public String getBucketFolder() {
        return "CLIENT_DOCUMENTS";
    }
    @Override
    public Long getId() {
        return this.client.getClientID();
    }
    @Override
    public File getFile() {
        return this.documentLocation.equals("") ? null : new File(this.documentLocation);
    }

    @Override
    public String getDocType(){

        return documentType.toString();
    }

    @Override
    public String convertDocType() {
        String returnString = "";
        switch (documentType) {
            case ROLE_PROFILE:
                returnString = "Role Profile";
                break;
        }
        return returnString;
    }

    @Override
    public String printDocument() {
        return "File{" +
                "fileID=" + fileID +
                ", filename='" + file_name + '\'' +
                ", contenttype='" + content_type + '\'' +
                ", filesize='" + file_size + '\'' +
                ", documentLocation='" + documentLocation + '\'' +
                ", client=" + client.getClientID() +
                ", documentType=" + documentType +
                '}';
    }

}
