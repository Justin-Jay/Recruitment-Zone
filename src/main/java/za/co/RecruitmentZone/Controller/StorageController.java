package za.co.RecruitmentZone.Controller;



import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import za.co.RecruitmentZone.service.GoogleCloudStorageService;

import java.io.IOException;

@RestController
@RequestMapping("/storage")
@CrossOrigin("*")
public class StorageController {

    @Autowired
    private GoogleCloudStorageService storageService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            // Check if the file is too large (25MB limit)
            if (file.getSize() > 25 * 1024 * 1024) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("File size exceeds the limit (25MB).");
            }

            // Use Apache Tika to detect the file's content type
            Tika tika = new Tika();
            String contentType = tika.detect(file.getInputStream());

            // Check if the content type is valid (PDF or DOCX)
            if (!isValidContentType(contentType)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Invalid file format. Only PDF and Word documents are allowed.");
            }

            // Perform the actual file upload to storage
            String fileName = storageService.uploadFile(file);

            return ResponseEntity.ok("File uploaded: " + fileName);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("File upload failed: " + e.getMessage());
        }
    }


/*    @DeleteMapping("/delete/{fileName}")
    public void deleteFile(@PathVariable String fileName) {
        storageService.deleteFile(fileName);
    }*/

/*    @GetMapping("/download/{fileName}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable String fileName) {
        byte[] fileContent = storageService.downloadFile(fileName);

        // Set appropriate headers for the response
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", fileName);

        return new ResponseEntity<>(fileContent, headers, HttpStatus.OK);
    }*/

    // ... (Other methods for delete and download)

    private boolean isValidContentType(String contentType) {
        return "application/pdf".equals(contentType) || "application/msword".equals(contentType);
    }
}
