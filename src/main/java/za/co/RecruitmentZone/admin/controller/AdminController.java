package za.co.RecruitmentZone.admin.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import za.co.RecruitmentZone.recruitmentManager.service.RecruitmentManager;

@RestController
@RequestMapping("/admin")
@CrossOrigin("*")
public class AdminController {
    Logger log = LoggerFactory.getLogger(AdminController.class);

    @GetMapping("/")
    public String helloAdminController(){
        return "Admin access level.";
    }

    // Admin Task 1



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
    // Admin Task 2
    // Admin Tsk 3
}
