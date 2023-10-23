package za.co.RecruitmentZone.service.EventOrchestration;

import com.google.gson.Gson;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.tika.Tika;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import za.co.RecruitmentZone.entity.Application;
import za.co.RecruitmentZone.entity.Vacancy;
import za.co.RecruitmentZone.publisher.CandidateEventPublisher;
import za.co.RecruitmentZone.repository.CandidateRepository;
import za.co.RecruitmentZone.entity.Candidate;
import za.co.RecruitmentZone.storage.GoogleCloudStorageService;

import java.io.ByteArrayInputStream;
import java.io.IOException;

@Service
public class CandidateEventManagement {

    private final CandidateRepository candidateRepository;

    private final CandidateEventPublisher candidateEventPublisher;
    private final GoogleCloudStorageService googleCloudStorageService;
    private final Logger log = LoggerFactory.getLogger(CandidateEventManagement.class);

    public CandidateEventManagement(CandidateRepository candidateRepository, CandidateEventPublisher candidateEventPublisher, GoogleCloudStorageService googleCloudStorageService) {
        this.candidateRepository = candidateRepository;
        this.candidateEventPublisher = candidateEventPublisher;
        this.googleCloudStorageService = googleCloudStorageService;
    }

    public void applyForVacancy(Candidate candidate) {
        // Your existing logic for candidate application
        // ...

        // Save the candidate's application details to the database
        candidateRepository.save(candidate);
    }



    public boolean publishCandidateAppliedEvent(String json){
        Gson gson = new Gson();
        Application newApplication = gson.fromJson(json, Application.class);
        newApplication.get

        newApplication.setCvFilePath();
        log.info("User Saved");
        return candidateEventPublisher.publishCandidateAppliedEvent(newApplication);
    }



    // NEEDS TO BE SYNCHRONIS
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

    private boolean isValidContentType(String contentType) {
        return "application/pdf".equals(contentType) || "application/msword".equals(contentType);
    }


}
