package za.co.RecruitmentZone.controller.web;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import za.co.RecruitmentZone.entity.domain.ContactMessage;
import za.co.RecruitmentZone.service.RecruitmentZoneService;

@Controller
@CrossOrigin("*")
@RequestMapping("/")
public class CommunicationController {
    private final Logger log = LoggerFactory.getLogger(CommunicationController.class);

    private final RecruitmentZoneService recruitmentZoneService;

    public CommunicationController(RecruitmentZoneService recruitmentZoneService) {
        this.recruitmentZoneService = recruitmentZoneService;
    }
    @GetMapping("/contact-us")
    public String contactus(Model model,@RequestParam(name = "messageSent", defaultValue = "false")boolean messageSent) {
        model.addAttribute("contactMessage",new ContactMessage());
        if (messageSent){
            model.addAttribute("messageSent",Boolean.TRUE);
        }

        return "fragments/info/contact-us";
    }

    @PostMapping("/send-message")
    public String sendMessage(@Valid @ModelAttribute("message") ContactMessage message, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("success",Boolean.FALSE);
            return "fragments/info/contact-us";
        }
        recruitmentZoneService.websiteQueryReceived(message);
        model.addAttribute("messageSent",Boolean.TRUE);
        return "redirect:/contact-us";
    }


}
