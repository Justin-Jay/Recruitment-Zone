package za.co.RecruitmentZone.candidate.controller;

import jakarta.validation.Valid;
import org.apache.tika.Tika;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import za.co.RecruitmentZone.application.dto.NewApplicationDTO;
import za.co.RecruitmentZone.application.entity.Application;
import za.co.RecruitmentZone.blog.dto.BlogDTO;
import za.co.RecruitmentZone.candidate.dto.CandidateNoteDTO;
import za.co.RecruitmentZone.candidate.entity.Candidate;
import za.co.RecruitmentZone.candidate.entity.CandidateNote;
import za.co.RecruitmentZone.service.RecruitmentZoneService;
import za.co.RecruitmentZone.util.Enums.ApplicationStatus;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Collections;
import java.util.List;
import java.util.Set;


@Controller
@CrossOrigin("*")
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



//
/*    @PostMapping("/view-candidate-notes")
    public String candidateNotes(@RequestParam("candidateID") Long candidateID,Model model) {
        // Add any model attributes if needed
        Candidate candidate = recruitmentZoneService.getCandidateById(candidateID);
        Set<CandidateNote> candidateNotes = candidate.getNotes();
       // model.addAttribute("candidate", candidate);
        model.addAttribute("candidateNotes", candidateNotes);
        //model.addAttribute("applicationStatus", ApplicationStatus.values());
        return "fragments/vacancy/candidate-notes-fragment";
    }*/

}
