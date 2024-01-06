package za.co.recruitmentzone.vacancy.controller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import za.co.recruitmentzone.client.entity.Client;
import za.co.recruitmentzone.employee.entity.Employee;
import za.co.recruitmentzone.util.Enums.ApplicationStatus;
import za.co.recruitmentzone.util.Enums.VacancyStatus;
import za.co.recruitmentzone.service.RecruitmentZoneService;
import za.co.recruitmentzone.vacancy.dto.VacancyDTO;
import za.co.recruitmentzone.vacancy.entity.Vacancy;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/Vacancy")
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
        model.addAttribute("vacancy", new VacancyDTO());
        //List<Client> clients = recruitmentZoneService.getClients();
        model.addAttribute("clients", loadClients());
        model.addAttribute("employees", loadEmployees());
        return "fragments/vacancy/add-vacancy";
    }

    public List<Employee> loadEmployees(){
        return recruitmentZoneService.getEmployees();
    }

    public List<Client> loadClients(){
        //List<Client> tempClients = recruitmentZoneService.getClients();
        return recruitmentZoneService.getClients();
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
    public String saveVacancy(@Valid @ModelAttribute("vacancy")VacancyDTO vacancy,
                              BindingResult bindingResult,Model model) {
        if (bindingResult.hasFieldErrors())
        {
            log.info("VACANCY ERRORS {}",bindingResult.getAllErrors());
            model.addAttribute("clients", loadClients());
            model.addAttribute("employees", loadEmployees());
            return "fragments/vacancy/add-vacancy";
        }
        recruitmentZoneService.saveNewVacancy(vacancy);
        return "redirect:/Vacancy/vacancy-administration";
    }

    @PostMapping("/update-vacancy")
    public String updateVacancy(@RequestParam("vacancyID") Long vacancyID, Model model) {
        Vacancy vacancy = recruitmentZoneService.findVacancyById(vacancyID);
        model.addAttribute("vacancy", vacancy);
        model.addAttribute("status", VacancyStatus.values());
        return "fragments/vacancy/update-vacancy";
    }
    @PostMapping("/save-updated-vacancy")
    public String saveUpdatedVacancy(@Valid @ModelAttribute("vacancy")VacancyDTO vacancy, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "fragments/vacancy/update-vacancy";
        }
        recruitmentZoneService.saveVacancy(vacancy);
        return "redirect:/Vacancy/vacancy-administration";
    }


    @PostMapping("/view-vacancy-submission")
    public String viewVacancySubmissions(@RequestParam("vacancyID") Long vacancyID, Model model) {
        Vacancy vacancy = recruitmentZoneService.findVacancyById(vacancyID);
        model.addAttribute("vacancy", vacancy);
        model.addAttribute("applicationStatus", ApplicationStatus.values());
        return "fragments/vacancy/view-vacancy-submission";
    }


}
