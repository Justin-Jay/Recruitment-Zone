package za.co.recruitmentzone.documents;


import java.io.File;

public interface Document {

    String getBucketFolder();
    Long getId();
    File getFile();
    String getDocType();

    String printDocument();

    String convertDocType();
}
// ClientDocumentType