package za.co.RecruitmentZone.storage;

import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/storage")
public class StorageController {

    @Autowired
    private GoogleCloudStorageService storageService;




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
