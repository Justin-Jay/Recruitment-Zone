package za.co.RecruitmentZone.RecruitmentManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/recruitment-managers")
class RecruitmentManagerController {

    private final RecruitmentManagerService recruitmentManagerService;

    @Autowired
    public RecruitmentManagerController(RecruitmentManagerService recruitmentManagerService) {
        this.recruitmentManagerService = recruitmentManagerService;
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        // Display a form to add a new recruitment manager
        // You can add necessary attributes to the model
        return "add-recruitment-manager";
    }

    @PostMapping("/add")
    public String addRecruitmentManager(@ModelAttribute RecruitmentManager manager) {
        // Process form submission to add a new recruitment manager
        recruitmentManagerService.addRecruitmentManager(manager);
        return "redirect:/recruitment-managers";
    }

    // Add other controller methods for recruitment manager management as needed
}

