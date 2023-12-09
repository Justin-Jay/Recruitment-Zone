package za.co.RecruitmentZone.client.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import za.co.RecruitmentZone.service.RecruitmentZoneService;

@Controller
@CrossOrigin("*")
public class CandidateController {
    private final RecruitmentZoneService recruitmentZoneService;
    private final Logger log = LoggerFactory.getLogger(CandidateController.class);

    public CandidateController(RecruitmentZoneService recruitmentZoneService) {
        this.recruitmentZoneService = recruitmentZoneService;
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
