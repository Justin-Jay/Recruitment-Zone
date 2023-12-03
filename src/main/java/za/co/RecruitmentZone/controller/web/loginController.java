package za.co.RecruitmentZone.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class loginController {
    @GetMapping("/log-in")
    public String loginPage(){
        return "log-in";
    }
}
