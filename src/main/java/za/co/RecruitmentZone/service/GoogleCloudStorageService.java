package za.co.RecruitmentZone.service;

import com.google.cloud.storage.*;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class GoogleCloudStorageService {
    Logger log = LoggerFactory.getLogger(GoogleCloudStorageService.class);
    private final String BUCKET_NAME = "kiunga"; // Replace with your bucket name
    @Autowired
    private Storage storage;

    // Upload a file to Google Cloud Storage
    public String uploadFile(MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();
        BlobId blobId = BlobId.of(BUCKET_NAME, fileName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();
        byte[] bytes = file.getBytes();

        try {
            storage.create(blobInfo, bytes);
            // The upload was successful if it didn't throw an exception
        } catch (StorageException e) {
            // Handle the exception that occurred during the upload
            e.printStackTrace(); // Example: Print the exception to the console
        }
        return fileName;
    }

    //"Resume_IDNUMBER"
    // Delete a file from Google Cloud Storage
    public void deleteFile(String fileName) {
        BlobId blobId = BlobId.of(BUCKET_NAME, fileName);
        try {
            storage.delete(blobId);
        } catch (Exception e) {
            log.info(e.getLocalizedMessage());
        }
    }

    // Retrieve/download a file from Google Cloud Storage
    public byte[] downloadFile(String fileName) {
        BlobId blobId = BlobId.of(BUCKET_NAME, fileName);
        Blob blob = storage.get(blobId);
        return blob.getContent();
    }


}
