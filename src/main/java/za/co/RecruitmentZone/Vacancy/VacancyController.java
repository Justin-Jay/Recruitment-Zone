package za.co.RecruitmentZone.Vacancy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/vacancies")
public class VacancyController {

    private final VacancyService vacancyService;

    @Autowired
    public VacancyController(VacancyService vacancyService) {
        this.vacancyService = vacancyService;
    }

    @GetMapping("/add")
    public String showAddVacancyForm(Model model) {
        // Display a form to add a new vacancy
        // You can add necessary attributes to the model
        return "add-vacancy";
    }

    @PostMapping("/add")
    public String addVacancy(@ModelAttribute Vacancy vacancy) {
        // Process form submission to add a new vacancy
        vacancyService.addVacancy(vacancy);
        return "redirect:/vacancies";
    }

    @GetMapping("/list")
    public String listVacancies(Model model) {
        // Retrieve and display a list of vacancies
        List<Vacancy> vacancies = vacancyService.getAllVacancies();
        model.addAttribute("vacancies", vacancies);
        return "list-vacancies";
    }

    // Add other controller methods for vacancy management as needed
}

