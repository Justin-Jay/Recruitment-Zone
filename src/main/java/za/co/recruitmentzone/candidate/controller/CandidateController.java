package za.co.recruitmentzone.candidate.controller;

import jakarta.validation.Valid;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import za.co.recruitmentzone.application.dto.NewApplicationDTO;
import za.co.recruitmentzone.candidate.dto.CandidateFileDTO;
import za.co.recruitmentzone.candidate.dto.CandidateNoteDTO;
import za.co.recruitmentzone.candidate.exception.CandidateException;
import za.co.recruitmentzone.candidate.entity.CandidateFile;
import za.co.recruitmentzone.documents.SaveFileException;
import za.co.recruitmentzone.service.RecruitmentZoneService;
import za.co.recruitmentzone.vacancy.exception.VacancyException;

import java.security.Principal;

import static za.co.recruitmentzone.util.Constants.ErrorMessages.INTERNAL_SERVER_ERROR;


@Controller
@RequestMapping("/Candidate")
public class CandidateController {
    private final RecruitmentZoneService recruitmentZoneService;
    private final Logger log = LoggerFactory.getLogger(CandidateController.class);

    public CandidateController(RecruitmentZoneService recruitmentZoneService) {
        this.recruitmentZoneService = recruitmentZoneService;
    }

    @GetMapping("/candidate-administration")
    public String candidateAdministration(Model model) {
        try {
            recruitmentZoneService.findAllCandidates(model);
        } catch (Exception e) {
            log.error("<-- candidateAdministration -->  Exception \n {}", e.getMessage());
            model.addAttribute("internalServerError", INTERNAL_SERVER_ERROR);
        }
        //   candidateList , findAllCandidatesResponse , internalServerError
        return "fragments/candidate/candidate-administration";
    }


    @PostMapping("/view-candidate")
    public String viewCandidate(Model model, @RequestParam("candidateID") Long candidateID) {
        try {
            recruitmentZoneService.addCandidateNote(candidateID, model);
        } catch (Exception e) {
            log.error("<-- viewCandidate -->  Exception \n {}", e.getMessage());
            model.addAttribute("internalServerError", INTERNAL_SERVER_ERROR);
        }
        //  candidate ,existingNotes , candidateNoteDTO , addCandidateNoteResponse , internalServerError
        return "fragments/candidate/view-candidate";
    }

    @PostMapping("/save-candidate-note")
    public String saveNote(@Valid @ModelAttribute("candidateNote") CandidateNoteDTO candidateNote,
                           BindingResult bindingResult, Model model) {
        if (bindingResult.hasFieldErrors()) {
            log.error("HAS ERRORS");
            model.addAttribute("noteSaved", Boolean.FALSE);
            recruitmentZoneService.addCandidateNote(candidateNote.getCandidateID(), model);
            //  candidate ,existingNotes , candidateNoteDTO , addCandidateNoteResponse , internalServerError , noteSaved
            return "fragments/candidate/view-candidate";
        }
        try {
            recruitmentZoneService.saveCandidateNote(candidateNote, model);
        } catch (Exception e) {
            log.error("<-- saveNote -->  Exception \n {}", e.getMessage());
            model.addAttribute("internalServerError", e.getMessage());
        }
        //  candidate ,existingNotes , candidateNoteDTO , addCandidateNoteResponse , internalServerError ,  noteSaved , saveCandidateResponse
        return "fragments/candidate/view-candidate";
    }

    @PostMapping("/view-candidate-notes")
    public String candidateNotes(@RequestParam("candidateID") Long candidateID, Model model) {
        try {
            recruitmentZoneService.findCandidateNotes(candidateID, model);
        } catch (Exception e) {
            log.error("<-- candidateNotes -->  Exception \n {}", e.getMessage());
            model.addAttribute("internalServerError", e.getMessage());
        }
        // candidate , existingNotes , findCandidateNotesResponse , internalServerError
        return "fragments/candidate/candidate-note-fragment";
    }


    @PostMapping("/view-candidate-documents")
    public String candidateDocs(@RequestParam("candidateID") Long candidateID, Model model) {
        try {
            recruitmentZoneService.findCandidateDocuments(model, candidateID);
        } catch (Exception e) {
            log.error("<-- candidateDocs -->  Exception \n {}", e.getMessage());
            model.addAttribute("internalServerError", INTERNAL_SERVER_ERROR);
        }

        // candidate, candidateFileDTO , existingDocuments,  internalServerError , findCandidateDocumentsResponse
        return "fragments/candidate/candidate-documents";

    }

    @PostMapping("/upload-candidate-document")
    public String saveDocument(@Valid @ModelAttribute("candidateFileDTO") CandidateFileDTO candidateFileDTO,
                               BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            log.error("<-- saveDocument  hasErrors --> ");
            recruitmentZoneService.findCandidateDocuments(model, candidateFileDTO.getCandidateID());
            model.addAttribute("message", "Binding Result Failed");
            return "fragments/candidate/candidate-documents";
        }
        try {
            boolean validFile = false;
            try {
                validFile = recruitmentZoneService.validateFile(candidateFileDTO.getDocumentAttachment());
            } catch (FileUploadException fileUploadException) {
                log.error("<-- fileUploadException  {} --> ", fileUploadException.getMessage());
                model.addAttribute("invalidFileUpload", fileUploadException.getMessage());
            }
            if (validFile) {
                try {
                    CandidateFile file = recruitmentZoneService.createCandidateFile(candidateFileDTO);
                    if (file != null) {
                        log.info("saveDocument file is not null");
                        model.addAttribute("createCandidateFileResponse", "File Upload Successful");
                        recruitmentZoneService.findCandidateDocuments(model, candidateFileDTO.getCandidateID());
                    }

                } catch (SaveFileException saveFileException) {
                    log.error(saveFileException.getMessage());
                    model.addAttribute("saveDocumentResponse", saveFileException.getMessage());
                }
            }

        } catch (Exception e) {
            log.error("<-- saveDocument -->  Exception \n {}", e.getMessage());
            model.addAttribute("internalServerError", INTERNAL_SERVER_ERROR);
        }
        // invalidFileUpload , createCandidateFileResponse , saveDocumentResponse , internalServerError
        return "fragments/candidate/candidate-documents";
    }



    @PostMapping("/add-candidate")
    public String showAddCandidateForm(Model model) {
        try {
            recruitmentZoneService.getAllVacancies(model);
            model.addAttribute("newApplicationDTO", new NewApplicationDTO());
        } catch (Exception e) {
            log.error("<-- showAddCandidateForm -->  Exception \n {}", e.getMessage());
            model.addAttribute("internalServerError", INTERNAL_SERVER_ERROR);
        }
        //  vacancyList , loadVacanciesResponse , newApplicationDTO , internalServerError
        return "fragments/candidate/add-candidate";
    }


    @PostMapping("/create-application")
    public String saveSubmission(@Valid @ModelAttribute("newApplicationDTO") NewApplicationDTO newApplicationDTO,
                                 BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            log.info("Binding Failed \n ");
            log.info(bindingResult.toString());
            log.info(bindingResult.getAllErrors().toString());
            recruitmentZoneService.getAllVacancies(model);
            model.addAttribute("newApplicationDTO", new NewApplicationDTO());
            model.addAttribute("bindingResult",INTERNAL_SERVER_ERROR);
            //  vacancyList , loadVacanciesResponse , newApplicationDTO , internalServerError , fileUploadError
            return "fragments/candidate/add-candidate";
        }
        try {
            try {
                boolean validFIle = recruitmentZoneService.validateFile(newApplicationDTO.getDocumentAttachment());
                if (validFIle) {
                    try {
                        // applicationOutcome , createCandidateApplicationResponse
                        recruitmentZoneService.createCandidateApplication(newApplicationDTO, model);

                        // candidateList , findAllCandidatesResponse
                        recruitmentZoneService.findAllCandidates(model);

                    } catch (CandidateException candidateException) {
                        log.error("<-- candidateException -->   \n {}", candidateException.getMessage());
                        //candidateFormAttributes(model, candidateException.getMessage(), false);
                        model.addAttribute("createCandidateResponse", candidateException.getMessage());
                        model.addAttribute("newApplicationDTO", new NewApplicationDTO());
                        recruitmentZoneService.getAllVacancies(model);
                        return "fragments/candidate/add-candidate";
                    }
                }

            } catch (FileUploadException fileUploadException) {
                log.error("<-- saveSubmission -->  FileUploadException \n {}", fileUploadException.getMessage());
                model.addAttribute("fileUploadError", fileUploadException.getMessage());
                model.addAttribute("newApplicationDTO", new NewApplicationDTO());
                recruitmentZoneService.getAllVacancies(model);
                return "fragments/candidate/add-candidate";
            }
            // fileUploadError
        } catch (Exception e) {
            log.error("<-- saveSubmission -->  Exception \n {}", e.getMessage());
            model.addAttribute("internalServerError", INTERNAL_SERVER_ERROR);
            model.addAttribute("newApplicationDTO", new NewApplicationDTO());
            recruitmentZoneService.getAllVacancies(model);
            return "fragments/candidate/add-candidate";
        }

        return "fragments/candidate/candidate-administration";
    }


}