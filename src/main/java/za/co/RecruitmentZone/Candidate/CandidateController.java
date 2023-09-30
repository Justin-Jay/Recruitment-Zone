package za.co.RecruitmentZone.Candidate;

import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import za.co.RecruitmentZone.Storage.StorageController;

@Controller
@RequestMapping("/candidates")
public class CandidateController {

    private final CandidateService candidateService;
    private final StorageController storageController;

    @Autowired
    public CandidateController(CandidateService candidateService, StorageController storageController) {
        this.candidateService = candidateService;
        this.storageController = storageController;
    }

    @GetMapping("/apply")
    public String showApplyForm(Model model) {
        // Display a form for candidates to apply for a vacancy
        // You can add necessary attributes to the model
        return "apply-for-vacancy";
    }

    @PostMapping("/apply")
    public ResponseEntity<String> applyForVacancy(
            @ModelAttribute Candidate candidate,
            @RequestParam("resume") MultipartFile resumeFile
    ) {
        // Call the StorageController to upload the file
        ResponseEntity<String> uploadResponse = storageController.uploadFile(resumeFile);
        if (uploadResponse.getStatusCode() != HttpStatus.OK) {
            // Handle the file upload error and return an appropriate response
            return uploadResponse;
        }

        // Update the candidate's resume URL
        String resumeFilePath = uploadResponse.getBody();
        candidate.setCvFilePath(resumeFilePath);

        // Process the candidate's application for a vacancy
        candidateService.applyForVacancy(candidate);

        // Return a success response
        return ResponseEntity.ok("Candidate applied successfully.");
    }

    // Add other controller methods for candidate management as needed
}



