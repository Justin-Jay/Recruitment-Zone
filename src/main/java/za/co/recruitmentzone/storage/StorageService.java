package za.co.recruitmentzone.storage;

import com.google.cloud.storage.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.w3c.dom.DocumentType;
import za.co.recruitmentzone.candidate.service.CandidateFileService;
import za.co.recruitmentzone.documents.ClientFileUploadEvent;
import za.co.recruitmentzone.documents.FileUploadEvent;
import za.co.recruitmentzone.candidate.exception.CandidateNotFoundException;
import za.co.recruitmentzone.candidate.service.CandidateService;
import za.co.recruitmentzone.util.enums.CandidateDocumentType;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class StorageService {
    private final Logger log = LoggerFactory.getLogger(StorageService.class);

    private final Storage storage;

    @Value("${storage.bucket}")
    private String BUCKET_NAME;

    @Value("${google.storage.pre}")
    String GSTORAGE_PRE;


    public StorageService(Storage storage) {
        this.storage = storage;
    }


    public String uploadFile(FileUploadEvent event) {
        log.info("<--- uploadFile event {} ---> ", event.printEvent());
        String filename = "";
        try {
            String docType = event.getDocument().getDocType();
            String UserID = event.getDocument().getId().toString();
            String formattedDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy_MM_dd"));
            String fileName = event.getDocument().getFile().getName();
            String filePathSplit = "/";
            String folderName = event.getDocument().getBucketFolder(); //  "CANDIDATE_FILES" / "CLIENT_FILES"
            String destinationFileName = "files" + filePathSplit + folderName + filePathSplit + UserID + filePathSplit + docType + filePathSplit + formattedDate + filePathSplit + fileName;

            BlobId id = BlobId.of(BUCKET_NAME, destinationFileName);
            BlobInfo info = BlobInfo.newBuilder(id).build();

            File f = event.getDocument().getFile();
            Blob uploadedFile = storage.create(info, convertFileToByteArray(f));
            // GSTORAGE_PRE+BUCKET_NAME+filePathSplit+
            filename = GSTORAGE_PRE + BUCKET_NAME + filePathSplit + uploadedFile.getName();
            log.info("Google Cloud Storage File name: {}", filename);

        } catch (CandidateNotFoundException | IOException candidateNotFoundException) {
            return "File upload failed: " + candidateNotFoundException.getMessage();
        }
        return filename;
    }

    public String uploadClientFile(ClientFileUploadEvent event) {
        log.info("<--- uploadFile event {} ---> ", event.printEvent());
        String filename = "";
        try {
            String docType = event.getDocument().getDocType();
            String UserID = event.getDocument().getId().toString();
            String formattedDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy_MM_dd"));
            String fileName = event.getDocument().getFile().getName();
            String filePathSplit = "/";
            String folderName = event.getDocument().getBucketFolder(); //  "CANDIDATE_FILES" / "CLIENT_FILES"
            String destinationFileName = "files" + filePathSplit + folderName + filePathSplit + event.getClientName() + filePathSplit + docType + filePathSplit + formattedDate + filePathSplit + fileName;
  /*
   String directory = fileDirectory + "/CLIENT_FILES/" + client.getName() + "/" + docType + "/" + formattedDate;

  * */
            BlobId id = BlobId.of(BUCKET_NAME, destinationFileName);
            BlobInfo info = BlobInfo.newBuilder(id).build();

            File f = event.getDocument().getFile();
            Blob uploadedFile = storage.create(info, convertFileToByteArray(f));
            // GSTORAGE_PRE+BUCKET_NAME+filePathSplit+
            filename = GSTORAGE_PRE + BUCKET_NAME + filePathSplit + uploadedFile.getName();
            log.info("Google Cloud Storage File name: {}", filename);

        } catch (CandidateNotFoundException | IOException candidateNotFoundException) {
            return "File upload failed: " + candidateNotFoundException.getMessage();
        }
        return filename;
    }

    public static byte[] convertFileToByteArray(File file) throws IOException {
        FileInputStream fis = new FileInputStream(file);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buffer = new byte[4096]; // Increased buffer size to 4096 bytes (4 KB)
        int bytesRead;

        while ((bytesRead = fis.read(buffer)) != -1) {
            bos.write(buffer, 0, bytesRead);
        }

        fis.close();
        bos.close();

        return bos.toByteArray();
    }


    public String testMethod() throws IOException {

        log.info("Test method");
        String destinationFileName = "newFolder" + "/" + "New File Name.txt";
        BlobId id = BlobId.of("kiunga", destinationFileName);
        BlobInfo info = BlobInfo.newBuilder(id).build();
// "C:\demo.txt"
        File file = new File("C:/", "demo.txt");
        byte[] arr = Files.readAllBytes(Paths.get(file.toURI()));
        Blob saved = storage.create(info, arr);

        log.info("saved result {}", saved.getName());
        return "File Uploaded Successfully" + saved.getName();
    }

}
