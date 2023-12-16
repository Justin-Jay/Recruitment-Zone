package za.co.RecruitmentZone.candidate.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.apache.tika.Tika;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import za.co.RecruitmentZone.application.dto.NewApplicationDTO;
import za.co.RecruitmentZone.application.dto.NewAssistedApplicationDTO;
import za.co.RecruitmentZone.candidate.dto.CandidateFileDTO;
import za.co.RecruitmentZone.candidate.dto.CandidateNoteDTO;
import za.co.RecruitmentZone.candidate.entity.Candidate;
import za.co.RecruitmentZone.candidate.entity.CandidateNote;
import za.co.RecruitmentZone.documents.CandidateFile;
import za.co.RecruitmentZone.service.RecruitmentZoneService;
import za.co.RecruitmentZone.vacancy.entity.Vacancy;

import java.io.IOException;
import java.util.List;
import java.util.Set;


@Controller
@RequestMapping("/Candidate")
public class CandidateController {
    private final RecruitmentZoneService recruitmentZoneService;
    private final Logger log = LoggerFactory.getLogger(CandidateController.class);

    public CandidateController(RecruitmentZoneService recruitmentZoneService) {
        this.recruitmentZoneService = recruitmentZoneService;
    }

    @PostMapping("/view-candidate")
    public String viewCandidate(Model model, @RequestParam("candidateID") Long candidateID) {
        Candidate candidate = recruitmentZoneService.findCandidateByID(candidateID);
        model.addAttribute("candidate", candidate);
        model.addAttribute("existingNotes", candidate.getNotes());
        CandidateNoteDTO dto = new CandidateNoteDTO();
        dto.setCandidateID(candidateID);
        log.info("CandidateID: {}",candidateID);
        model.addAttribute("candidateNote",dto);
        return "fragments/candidate/view-candidate";
    }

    @PostMapping("/save-candidate-note")
    public String saveNote(@Valid @ModelAttribute("candidateNote")CandidateNoteDTO candidateNote,
                           BindingResult bindingResult, Model model) {
        if (bindingResult.hasFieldErrors()) {
            log.info("HAS ERRORS");
            model.addAttribute("noteSaved", Boolean.FALSE);
            return "fragments/candidate/view-candidate";
        }

        Long candidateID = candidateNote.getCandidateID();
        Candidate candidate = recruitmentZoneService.findCandidateByID(candidateID);
        candidate.addNote(candidateNote);

        boolean noteSaved = recruitmentZoneService.saveCandidate(candidate);
        model.addAttribute("noteSaved", noteSaved);

        model.addAttribute("candidate", candidate);
        Set<CandidateNote> notes = candidate.getNotes();
    // sort the set according to date
        model.addAttribute("existingNotes",notes );
        CandidateNoteDTO dto = new CandidateNoteDTO();
        dto.setCandidateID(candidateID);
        log.info("CandidateID: {}",candidateID);
        model.addAttribute("candidateNote",dto);
        return "fragments/candidate/view-candidate";
    }


    @PostMapping("/view-candidate-notes")
    public String candidateNotes(@RequestParam("candidateID") Long candidateID,Model model) {
        Candidate candidate = recruitmentZoneService.findCandidateByID(candidateID);
        Set<CandidateNote> existingNotes = candidate.getNotes();
        model.addAttribute("existingNotes", existingNotes);
        return "fragments/candidate/candidate-note-fragment";
    }

    @PostMapping("/view-candidate-documents")
    public String candidateDocs(@RequestParam("candidateID") Long candidateID,Model model) {
        documentAttributes(model,candidateID);
        return "fragments/candidate/candidate-documents";

    }

    @PostMapping("/upload-candidate-document")
    public String saveDocument(@Valid @ModelAttribute("candidateFileDTO") CandidateFileDTO candidateFileDTO,
                               BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            documentAttributes(model,candidateFileDTO,"Binding Failed");
            return "fragments/applications/apply-now";
        }
        else if (candidateFileDTO.getCvFile().isEmpty()) {
            documentAttributes(model,candidateFileDTO,"Please select a file to upload.");
            return "fragments/applications/apply-now";
        }
        // Security check: Ensure the file name is not a path that could be exploited
        else if (candidateFileDTO.getCvFile().getOriginalFilename().contains("..")) {
            documentAttributes(model,candidateFileDTO,"Invalid file name.");

            return "fragments/applications/apply-now";
        }
        else if (candidateFileDTO.getCvFile().getSize() > 1024 * 1024 * 25) { // 25MB
            documentAttributes(model,candidateFileDTO,"File size exceeds the maximum limit (25MB).");
            return "fragments/applications/apply-now";
        }
        // Security check: Ensure the file content is safe (detect content type)
        else if (!isValidContentType(candidateFileDTO.getCvFile())) {
            documentAttributes(model,candidateFileDTO,"Invalid file type. Only Word (docx) and PDF files are allowed.");
            return "fragments/applications/apply-now";
        }
        try {
            //   recruitmentZoneService.createCandidateApplication(newApplicationDTO);

            recruitmentZoneService.createFile(candidateFileDTO);
            // publish file upload event and give the file
            //recruitmentZoneService.publishFileUploadedEvent(candidateID,newApplicationDTO);

            documentAttributes(model,candidateFileDTO,"File Upload Successfully!");
            return "fragments/candidate/candidate-documents";
        } catch (Exception e) {
            log.info(e.getMessage());
            log.info("Failed to save file");
            documentAttributes(model,candidateFileDTO,"File upload failed. Please try again.");
            return "fragments/candidate/candidate-documents";
        }
    }



    @GetMapping("/candidate-administration")
    public String viewAllCandidates(Model model) {
        List<Candidate> candidates = recruitmentZoneService.getCandidates();
        model.addAttribute("candidates", candidates);
        return "fragments/candidate/candidate-administration";
    }
    @GetMapping("/add-candidate")
    public String showAddCandidateForm(Model model) {
        applicationAttributes(model,"",true);
        return "fragments/candidate/add-candidate";
    }


    @PostMapping("/create-application")
    public String saveSubmission(@Valid @ModelAttribute("newApplicationDTO") NewAssistedApplicationDTO newApplicationDTO,
                                 BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            applicationAttributes(model,"",false);
            return "fragments/candidate/add-candidate";
        }
        else if (newApplicationDTO.getCvFile().isEmpty()) {
            applicationAttributes(model,"Please select a file to upload.",false);
            return "fragments/candidate/add-candidate";
        }
        // Security check: Ensure the file name is not a path that could be exploited
        else if (newApplicationDTO.getCvFile().getOriginalFilename().contains("..")) {
            applicationAttributes(model,"Invalid file name.",false);
            return "fragments/candidate/add-candidate";
        }
        else if (newApplicationDTO.getCvFile().getSize() > 1024 * 1024 * 25) { // 25MB
            applicationAttributes(model,"File size exceeds the maximum limit (25MB).",false);
            return "fragments/candidate/add-candidate";
        }
        // Security check: Ensure the file content is safe (detect content type)
        else if (!isValidContentType(newApplicationDTO.getCvFile())) {
            applicationAttributes(model,"Invalid file type. Only Word (docx) and PDF files are allowed.",false);
            return "fragments/candidate/add-candidate";
        }
        try {
            recruitmentZoneService.createCandidateApplication(newApplicationDTO);
            // publish file upload event and give the file
            //recruitmentZoneService.publishFileUploadedEvent(candidateID,newApplicationDTO);
            model.addAttribute("message",
                    "Application Submitted Successfully!");
            return "fragments/candidate/add-candidate";
        } catch (Exception e) {
            log.info(e.getMessage());
            log.info("Failed to save file");
            applicationAttributes(model,"File upload failed. Please try again.",false);
            return "fragments/candidate/add-candidate";
        }
    }

    private void applicationAttributes(Model model,String message,boolean newApplication) {
        model.addAttribute("vacancies", loadVacancies());
        model.addAttribute("message", message);
        if(newApplication)
        {
            model.addAttribute("newApplicationDTO", new NewAssistedApplicationDTO());
        }
    }

    private boolean isValidContentType(MultipartFile file) {
        try {
            String detectedContentType = new Tika().detect(file.getInputStream());
            return detectedContentType.equals("application/pdf") || detectedContentType.equals("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
        } catch (IOException e) {
            //log.info(e.getMessage());
            return false;
        }
    }
    private void documentAttributes(Model model,CandidateFileDTO candidateFileDTO,String message) {
        Candidate candidate = recruitmentZoneService.findCandidateByID(candidateFileDTO.getCandidateID());
        Set<CandidateFile> candidateDocuments = candidate.getDocuments();
        CandidateFileDTO fileDTO = new CandidateFileDTO();
        fileDTO.setCandidateID(candidateFileDTO.getCandidateID());
        model.addAttribute("candidateFileDTO", fileDTO);
        model.addAttribute("existingDocuments", candidateDocuments);
        model.addAttribute("candidateID",candidateFileDTO.getCandidateID());
        model.addAttribute("message", message);
    }

    private void documentAttributes(Model model,Long candidateID) {
        Candidate candidate = recruitmentZoneService.findCandidateByID(candidateID);
        Set<CandidateFile> candidateDocuments = candidate.getDocuments();
        CandidateFileDTO fileDTO = new CandidateFileDTO();
        fileDTO.setCandidateID(candidateID);
        model.addAttribute("candidateFileDTO", fileDTO);
        model.addAttribute("existingDocuments", candidateDocuments);
        model.addAttribute("candidateID",candidateID);
    }

    private List<Vacancy> loadVacancies(){
        return recruitmentZoneService.getAllVacancies();
    }

}
