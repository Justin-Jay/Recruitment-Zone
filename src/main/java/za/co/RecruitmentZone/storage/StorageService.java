package za.co.RecruitmentZone.storage;

import com.google.cloud.storage.*;
import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
public class StorageService {


    private final Storage storage;

    public StorageService(Storage storage) {
        this.storage = storage;
    }

    public String uploadFile(Long vacancyID,MultipartFile multi) {
        try {

            if (multi.getSize() > 25 * 1024 * 1024) {
                return "File size exceeds the limit (25MB)";
            }


            Tika tika = new Tika();
            String contentType = tika.detect(multi.getInputStream());


            if (!isValidContentType(contentType)) {
                return "Invalid file format. Only PDF and Word documents are allowed.";
            }

            // Perform the actual file upload to storage
            String destinationFileName = "CurriculumVitae" + "/" +"Submissions"+vacancyID+"/"+ multi.getName();
            BlobId id = BlobId.of("kiunga", destinationFileName);
            BlobInfo info = BlobInfo.newBuilder(id).build();

            Blob uploadedFile = storage.create(info, multi.getBytes());

            return "File uploaded: " + uploadedFile.getName();
        } catch (IOException e) {
            return "File upload failed: " + e.getMessage();
        }
    }


    private boolean isValidContentType(String contentType) {
        return "application/pdf".equals(contentType) || "application/msword".equals(contentType);
    }

    public String testMethod() throws IOException {

        System.out.println("Test method");
        String destinationFileName = "newFolder" + "/" + "New File Name.txt";
        BlobId id = BlobId.of("kiunga", destinationFileName);
        BlobInfo info = BlobInfo.newBuilder(id).build();
// "C:\demo.txt"
        File file = new File("C:/","demo.txt");
        byte[] arr = Files.readAllBytes(Paths.get(file.toURI()));
        Blob saved = storage.create(info,arr);

        System.out.println("saved result "+saved.getName());
        return "File Uploaded Successfully"+saved.getName();
    }
}
