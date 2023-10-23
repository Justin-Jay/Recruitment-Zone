package za.co.RecruitmentZone.controller;

import com.google.gson.Gson;
import jakarta.validation.Valid;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.tika.Tika;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import za.co.RecruitmentZone.candidate.service.CandidateService;
import za.co.RecruitmentZone.entity.Application;
import za.co.RecruitmentZone.entity.ApplicationUser;
import za.co.RecruitmentZone.entity.Candidate;
import za.co.RecruitmentZone.repository.ApplicationRepository;
import za.co.RecruitmentZone.service.EventOrchestration.CandidateEventManagement;
import za.co.RecruitmentZone.storage.GoogleCloudStorageService;
import za.co.RecruitmentZone.storage.StorageController;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Controller
@RequestMapping("/candidates")
@CrossOrigin("*")
public class CandidateController {

    private ApplicationRepository applicationRepository;
    private final CandidateEventManagement candidateEventManagement;

    GoogleCloudStorageService googleCloudStorageService;
    private final Logger log = LoggerFactory.getLogger(CandidateController.class);

    public CandidateController(ApplicationRepository applicationRepository, CandidateEventManagement candidateEventManagement, GoogleCloudStorageService googleCloudStorageService) {
        this.applicationRepository = applicationRepository;
        this.candidateEventManagement = candidateEventManagement;
        this.googleCloudStorageService = googleCloudStorageService;
    }

    // Folder where uploaded files will be stored
    private static final String UPLOAD_FOLDER = "uploads/";

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file,
                             @RequestParam("name") String name,
                             RedirectAttributes redirectAttributes) {
        // Check if file is empty
        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "Please select a file to upload.");
            return "redirect:/uploadStatus";
        }

        try {

            uploadFile(file);

            redirectAttributes.addFlashAttribute("message",
                    "File '" + file.getOriginalFilename() + "' uploaded successfully!");



        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message",
                    "Failed to upload '" + file.getOriginalFilename() + "'");
        }

        return "redirect:/uploadStatus";
    }






}



