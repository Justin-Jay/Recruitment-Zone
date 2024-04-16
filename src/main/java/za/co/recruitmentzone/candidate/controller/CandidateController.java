package za.co.recruitmentzone.candidate.controller;

import jakarta.validation.Valid;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import za.co.recruitmentzone.application.dto.NewApplicationDTO;
import za.co.recruitmentzone.candidate.dto.CandidateFileDTO;
import za.co.recruitmentzone.candidate.dto.CandidateNoteDTO;
import za.co.recruitmentzone.candidate.exception.CandidateException;
import za.co.recruitmentzone.candidate.exception.CandidateNotFoundException;
import za.co.recruitmentzone.documents.CandidateFile;
import za.co.recruitmentzone.service.RecruitmentZoneService;
import za.co.recruitmentzone.vacancy.exception.VacancyException;

import static za.co.recruitmentzone.util.Constants.ErrorMessages.INTERNAL_SERVER_ERROR;


@Controller
@RequestMapping("/Candidate")
public class CandidateController {
    private final RecruitmentZoneService recruitmentZoneService;
    private final Logger log = LoggerFactory.getLogger(CandidateController.class);

    public CandidateController(RecruitmentZoneService recruitmentZoneService) {
        this.recruitmentZoneService = recruitmentZoneService;
    }


    //  model = candidate, candidateNote,addCandidateNoteResponse, candidate , existingNotes , candidateNote internalServerError

    @PostMapping("/view-candidate")
    public String viewCandidate(Model model, @RequestParam("candidateID") Long candidateID) {
        try {
            recruitmentZoneService.addCandidateNote(candidateID, model);
            recruitmentZoneService.findCandidateNotes(candidateID, model);
        } catch (Exception e) {
            log.info("<-- viewCandidate -->  Exception \n {}",e.getMessage());
            model.addAttribute("internalServerError", INTERNAL_SERVER_ERROR);
        }

        return "fragments/candidate/view-candidate";
    }

    // model = candidate , existingNotes , candidateNote , findCandidateNotesResponse, noteSaved , saveCandidateResponse
    @PostMapping("/save-candidate-note")
    public String saveNote(@Valid @ModelAttribute("candidateNote") CandidateNoteDTO candidateNote,
                           BindingResult bindingResult, Model model) {
        if (bindingResult.hasFieldErrors()) {
            log.info("HAS ERRORS");
            model.addAttribute("noteSaved", Boolean.FALSE);
            return "fragments/candidate/view-candidate";
        }
        try {
            recruitmentZoneService.saveCandidateNote(candidateNote, model);
            recruitmentZoneService.findCandidateNotes(candidateNote.getCandidateID(), model);

        } catch (Exception e) {
            log.info("<-- saveNote -->  Exception \n {}",e.getMessage());
            model.addAttribute("internalServerError", e.getMessage());
        }
        return "fragments/candidate/view-candidate";
    }

    @PostMapping("/view-candidate-notes")
    public String candidateNotes(@RequestParam("candidateID") Long candidateID, Model model) {
        try {
            recruitmentZoneService.findCandidateNotes(candidateID, model);
        } catch (Exception e) {
            log.info("<-- candidateNotes -->  Exception \n {}",e.getMessage());
            model.addAttribute("internalServerError", e.getMessage());
        }
        log.info("<-- candidateNotes --> model: \n {}", model);
        return "fragments/candidate/candidate-note-fragment";
    }

    @PostMapping("/view-candidate-documents")
    public String candidateDocs(@RequestParam("candidateID") Long candidateID, Model model) {
        try {
            recruitmentZoneService.findCandidateDocuments(model, candidateID);
        } catch (Exception e) {
            log.info("<-- candidateDocs -->  Exception \n {}",e.getMessage());
            model.addAttribute("internalServerError", INTERNAL_SERVER_ERROR);
        }
        return "fragments/candidate/candidate-documents";

    }

    @PostMapping("/upload-candidate-document")
    public String saveDocument(@Valid @ModelAttribute("candidateFileDTO") CandidateFileDTO candidateFileDTO,
                               BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            recruitmentZoneService.findCandidateDocuments(model, candidateFileDTO.getCandidateID());
            model.addAttribute("message", "Binding Result Failed");
            return "fragments/applications/apply-now";
        }
        try {
            boolean validFile = false;
            try {
                validFile = recruitmentZoneService.validateFile(candidateFileDTO.getCvFile(), model);
            } catch (FileUploadException fileUploadException) {
                model.addAttribute("message", fileUploadException.getMessage());
            }
            if (validFile) {
                try {
                    CandidateFile file = recruitmentZoneService.createCandidateFile(candidateFileDTO);
                    if (file != null) {
                        model.addAttribute("createCandidateFileResponse", "File Upload Successful");
                        recruitmentZoneService.findCandidateDocuments(model, candidateFileDTO.getCandidateID());
                    }
                } catch (CandidateNotFoundException candidateNotFoundException) {
                    log.info(candidateNotFoundException.getMessage());
                    // model.addAttribute("createCandidateFileResponse", "File Upload Successful");
                    model.addAttribute("saveDocumentResponse", candidateNotFoundException.getMessage());
                }
            }
        } catch (Exception e) {
            log.info("<-- saveDocument -->  Exception \n {}",e.getMessage());
            model.addAttribute("internalServerError", INTERNAL_SERVER_ERROR);
        }
        return "fragments/candidate/candidate-documents";
    }

    @GetMapping("/candidate-administration")
    public String candidateAdministration(Model model,@ModelAttribute("createCandidateResponse")String createCandidateResponse) {
        try {
            recruitmentZoneService.findAllCandidates(model);
            model.addAttribute("createCandidateResponse",createCandidateResponse);
        } catch (Exception e) {
            log.info("<-- candidateAdministration -->  Exception \n {}",e.getMessage());
            model.addAttribute("internalServerError", INTERNAL_SERVER_ERROR);
        }
        return "fragments/candidate/candidate-administration";
    }

    @GetMapping("/add-candidate")
    public String showAddCandidateForm(Model model) {
        try {
            recruitmentZoneService.getAllVacancies(model);
            model.addAttribute("newApplicationDTO", new NewApplicationDTO());
        } catch (Exception e) {
            log.info("<-- showAddCandidateForm -->  Exception \n {}",e.getMessage());
            model.addAttribute("internalServerError", INTERNAL_SERVER_ERROR);
        }
        return "fragments/candidate/add-candidate";
    }

    @PostMapping("/create-application")
    public String saveSubmission(@Valid @ModelAttribute("newApplicationDTO") NewApplicationDTO newApplicationDTO,
                                 BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            recruitmentZoneService.getAllVacancies(model);
            return "fragments/candidate/add-candidate";
        }
        try {
            boolean validFIle = recruitmentZoneService.validateFile(newApplicationDTO.getCvFile(), model);
            if (validFIle) {
                try {
                    recruitmentZoneService.createCandidateApplication(newApplicationDTO, model,redirectAttributes);
                } catch (CandidateException candidateException) {
                    candidateFormAttributes(model, candidateException.getMessage(), false);
                }
            }
        } catch (Exception e) {
            log.info("<-- saveSubmission -->  Exception \n {}",e.getMessage());

            model.addAttribute("internalServerError", INTERNAL_SERVER_ERROR);
        }
        return "redirect:/Candidate/candidate-administration";
    }

    private void candidateFormAttributes(Model model, String message, boolean newApplication) {
        try {
            recruitmentZoneService.getAllVacancies(model);
            model.addAttribute("fileUploadError", message);
            if (newApplication) {
                model.addAttribute("newApplicationDTO", new NewApplicationDTO());
            }
        } catch (VacancyException vacancyException) {
            log.info("<-- candidateFormAttributes -->  Exception \n {}",vacancyException.getMessage());
            model.addAttribute("createCandidateResponse", vacancyException.getMessage());
        }

    }





}