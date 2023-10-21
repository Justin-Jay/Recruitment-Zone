package za.co.RecruitmentZone.Recruiter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/recruiters")
class RecruiterController {

    private final RecruiterService recruiterService;

    @Autowired
    public RecruiterController(RecruiterService recruiterService) {
        this.recruiterService = recruiterService;
    }

    @GetMapping("/add")
    public String showAddRecruiterForm(Model model) {
        // Display a form to add a new recruiter
        // You can add necessary attributes to the model
        return "add-recruiter";
    }

    @PostMapping("/add")
    public String addRecruiter(@ModelAttribute Recruiter recruiter) {
        // Process form submission to add a new recruiter
        recruiterService.addRecruiter(recruiter);
        return "redirect:/recruiters";
    }

    // Add other controller methods for recruiter management as needed
}

