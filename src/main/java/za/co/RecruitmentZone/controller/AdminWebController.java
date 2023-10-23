package za.co.RecruitmentZone.controller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import za.co.RecruitmentZone.entity.UserDTO;
import za.co.RecruitmentZone.service.ApplicationUserService;


@Controller
@RequestMapping("/admin/web")
public class AdminWebController {

    private final Logger log = LoggerFactory.getLogger(AdminWebController.class);
    private final ApplicationUserService applicationUserService;

    public AdminWebController(ApplicationUserService applicationUserService) {
        this.applicationUserService = applicationUserService;
    }

    @PostMapping("/createUser")
    public String createUser(@Valid @ModelAttribute("user") UserDTO userDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "errorPage";
        }

        try {
            applicationUserService.createNewUser(userDto);
            return "successPage";
        } catch (Exception e) {
            log.error("Error Creating User: ", e);
            return "errorPage";
        }
    }

    @PostMapping("/createRecruiter")
    public String createRecruiter(@Valid @ModelAttribute("user") UserDTO userDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "errorPage";
        }

        try {
            applicationUserService.publishApplicationUserCreateEvent(userDto);
            return "successPage";
        } catch (Exception e) {
            log.error("Error Adding Recruiter: ", e);
            return "errorPage";
        }
    }

    @PostMapping("/updateUser")
    public String updateUser(@Valid @ModelAttribute("user") UserDTO userDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "errorPage";
        }

        try {
            applicationUserService.updateUser(userDto);
            return "successPage";
        } catch (Exception e) {
            log.error("Error Updating User: ", e);
            return "errorPage";
        }
    }
}

