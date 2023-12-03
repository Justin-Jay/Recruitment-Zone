package za.co.RecruitmentZone.controller.web;

import ch.qos.logback.core.joran.spi.ElementSelector;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import za.co.RecruitmentZone.entity.domain.*;
import za.co.RecruitmentZone.service.RecruitmentZoneService;

import java.util.ArrayList;
import java.util.List;

@Controller
@CrossOrigin("*")
public class RecruitmentZoneWebController {
    private final RecruitmentZoneService recruitmentZoneService;
    private final Logger log = LoggerFactory.getLogger(RecruitmentZoneWebController.class);
    public RecruitmentZoneWebController(RecruitmentZoneService recruitmentZoneService) {
        this.recruitmentZoneService = recruitmentZoneService;
    }
    @GetMapping("/")
    public String redirectToHome() {
        return "redirect:/home";
    }

    // Home pages
    @GetMapping("/home")
    public String home(@RequestParam(name = "name", required = false) String title,Model model) {
        List<Vacancy> vacancies = recruitmentZoneService.getActiveVacancies();
        log.info("Total Vacancies: " + vacancies.size());
        if (title!=null){
            vacancies = recruitmentZoneService.searchVacancyByTitle(title);
        }
        model.addAttribute("totalNumberOfVacancies", vacancies.size());
        model.addAttribute("vacancies", vacancies);
        model.addAttribute("title",title);
        return "home";
    }
    @GetMapping("/aboutus")
    public String aboutUs() {
        return "fragments/info/about-us";
    }

    @PostMapping("/TestSavingFiles")
    public String testingSavingFiles() {

        log.info("TestSavingFiles CONTROLLER ");
        try {
            log.info("About to try and save File");
            recruitmentZoneService.saveTempFile();
            return "home";
        } catch (Exception e) {
            log.info(e.getMessage());
            log.info("Failed to save file");
        }
        return "home";
    }
}
