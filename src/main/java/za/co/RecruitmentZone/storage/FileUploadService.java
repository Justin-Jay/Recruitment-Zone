package za.co.RecruitmentZone.storage;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.tika.Tika;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import za.co.RecruitmentZone.storage.GoogleCloudStorageService;

import java.io.ByteArrayInputStream;
import java.io.IOException;

@Service
public class FileUploadService {


    private GoogleCloudStorageService googleCloudStorageService;

    public FileUploadService(GoogleCloudStorageService googleCloudStorageService) {
        this.googleCloudStorageService = googleCloudStorageService;
    }

    @Bean
    public String uploadFile(MultipartFile file) {
        try {
            // Check if the file is too large (25MB limit)
            if (file.getSize() > 25 * 1024 * 1024) {
                return "File size exceeds the limit (25MB).";
            }

            // Use Apache Tika to detect the file's content type
            Tika tika = new Tika();
            String contentType = tika.detect(file.getInputStream());

            // Check if the content type is valid (PDF or DOCX)
            if (!isValidContentType(contentType)) {
                return "Invalid file format. Only PDF and Word documents are allowed.";
            }

            // Validate the file contents
            byte[] fileBytes = file.getBytes();
            if ("application/pdf".equals(contentType) && !validatePdfDocument(fileBytes)) {
                return "Invalid PDF document.";
            } else if (("application/vnd.openxmlformats-officedocument.wordprocessingml.document".equals(contentType) || "application/msword".equals(contentType))
                    && !validateWordDocument(fileBytes)) {
                return "Invalid Word document.";
            }

            // Perform the actual file upload to storage
            return googleCloudStorageService.uploadFile(file);
        } catch (IOException e) {
            return "File upload failed: " + e.getMessage();
        }
    }

    public boolean validateWordDocument(byte[] fileBytes) {
        try (XWPFDocument doc = new XWPFDocument(new ByteArrayInputStream(fileBytes))) {
            // Check if the Word document is valid based on your criteria
            // For example, check for specific content, formatting, etc.
            // You can also extract and analyze the document's content as needed

            return true; // Valid Word document
        } catch (Exception e) {
            // Handle exceptions if the document is not valid
            return false;
        }
    }

    public boolean validatePdfDocument(byte[] fileBytes) {
        try (PDDocument doc = Loader.loadPDF(fileBytes)) {
            // Check if the PDF document is valid based on your criteria
            // For example, check for specific content, formatting, etc.
            // You can also extract and analyze the document's content as needed

            return true; // Valid PDF document
        } catch (IOException e) {
            // Handle exceptions if the document is not valid
            return false;
        }
    }

    private boolean isValidContentType(String contentType) {
        return "application/pdf".equals(contentType) ||
                "application/vnd.openxmlformats-officedocument.wordprocessingml.document".equals(contentType) ||
                "application/msword".equals(contentType);
    }
}
