package za.co.RecruitmentZone.controller.web;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import za.co.RecruitmentZone.entity.Enums.ApplicationStatus;
import za.co.RecruitmentZone.entity.Enums.VacancyStatus;
import za.co.RecruitmentZone.entity.domain.*;
import za.co.RecruitmentZone.service.RecruitmentZoneService;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Controller
@CrossOrigin()
public class VacancyController {
    private final RecruitmentZoneService recruitmentZoneService;
    private final Logger log = LoggerFactory.getLogger(VacancyController.class);

    public VacancyController(RecruitmentZoneService recruitmentZoneService) {
        this.recruitmentZoneService = recruitmentZoneService;
    }
    @GetMapping("/vacancy-administration")
    public String vacancies(Model model) {
        List<Vacancy> allVacancies = new ArrayList<>();
        try {
            allVacancies = recruitmentZoneService.getAllVacancies();
        } catch (Exception e) {
            log.info("Exception trying to retrieve vacancies, retrieving all vacancies ");
        }
        model.addAttribute("vacancies", allVacancies);
        return "fragments/vacancy/vacancy-administration";
    }
    @GetMapping("/add-vacancy")
    public String showCreateVacancyForm(Model model) {
        model.addAttribute("vacancy", new Vacancy());
        List<Client> clients = recruitmentZoneService.getClients();
        model.addAttribute("clients", clients);
        return "fragments/vacancy/add-vacancy";
    }
    @PostMapping("/view-vacancy")
    public String showVacancy(@RequestParam("vacancyID") Long vacancyID, Model model) {
        Vacancy optionalVacancy = recruitmentZoneService.findVacancyById(vacancyID);
        log.info("Looking for {}", vacancyID);
        log.info(optionalVacancy.toString());
        model.addAttribute("vacancy", optionalVacancy);
        return "fragments/vacancy/view-vacancy";
    }
    @PostMapping("/save-vacancy")
    public String saveVacancy(@Valid @ModelAttribute("vacancy")Vacancy vacancy, @RequestParam("name")String employeeUserName,BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "fragments/vacancy/add-vacancy";
        }
        recruitmentZoneService.saveNewVacancy(employeeUserName,vacancy);
        return "redirect:/vacancy-administration";
    }
    @PostMapping("/update-vacancy")
    public String updateVacancy(@RequestParam("vacancyID") Long vacancyID, Model model) {
        Vacancy vacancy = recruitmentZoneService.findVacancyById(vacancyID);
        model.addAttribute("vacancy", vacancy);
        model.addAttribute("status", VacancyStatus.values());
        return "fragments/vacancy/update-vacancy";
    }
    @PostMapping("/save-updated-vacancy")
    public String saveUpdatedVacancy(@Valid @ModelAttribute("vacancy")Vacancy vacancy, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "fragments/vacancy/update-vacancy";
        }
        recruitmentZoneService.saveVacancy(vacancy);
        return "redirect:/vacancy-administration";
    }

    @PostMapping("/view-candidate-notes")
    public String viewCandidateNotes(@RequestParam("candidateID")Long candidateID, Model model) {
        // Logic to fetch candidate notes based on candidateID
        // Add candidateNotes to the model
        Candidate candidate = recruitmentZoneService.getCandidateById(candidateID);
        Set<CandidateNote> candidateNotes = candidate.getNotes();
        model.addAttribute("candidateNotes", candidateNotes);
        return "fragments/vacancy/candidate-notes-fragment";
    }
    @PostMapping("/view-vacancy-submission")
    public String viewVacancySubmissions(@RequestParam("vacancyID") Long vacancyID, Model model) {
        Vacancy vacancy = recruitmentZoneService.findVacancyById(vacancyID);
        model.addAttribute("vacancy", vacancy);
        model.addAttribute("applicationStatus", ApplicationStatus.values());
        return "fragments/vacancy/view-vacancy-submission";
    }


}
