package za.co.recruitmentzone.controller;

import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class loginController {
    private final Logger log = LoggerFactory.getLogger(loginController.class);


    @GetMapping("/log-in")
    public String loginPage() {
        return "log-in";
    }

}


