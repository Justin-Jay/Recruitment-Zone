package za.co.recruitmentzone.communication.controller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import za.co.recruitmentzone.communication.entity.ContactMessage;
import za.co.recruitmentzone.communication.events.publisher.EmailEventPublisher;

import static za.co.recruitmentzone.util.Constants.ErrorMessages.INTERNAL_SERVER_ERROR;

@Controller
@RequestMapping("/Communication")
public class CommunicationController {
    private final Logger log = LoggerFactory.getLogger(CommunicationController.class);

    private final EmailEventPublisher emailEventPublisher;

    public CommunicationController(EmailEventPublisher emailEventPublisher) {
        this.emailEventPublisher = emailEventPublisher;
    }

    @GetMapping("/contact-us")
    public String contactus(Model model) {
        log.info("<-- contactus --> ");
        model.addAttribute("contactMessage",new ContactMessage());
        return "fragments/info/contact-us";
    }

    @PostMapping("/send-message")
    public String sendMessage(@Valid @ModelAttribute("message") ContactMessage message, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("messageSent", Boolean.FALSE);
            return "fragments/info/contact-us";
        }
        try {
            log.info("Website message received");
            websiteQueryReceived(message);
            model.addAttribute("messageSent", "Message has been received successfully, an agent will be in touch");
            model.addAttribute("contactMessage",new ContactMessage());
        } catch (Exception e) {
            log.info(e.getMessage());
            model.addAttribute("internalServerError", INTERNAL_SERVER_ERROR);
        }
         // messageSent , internalServerError
        return "fragments/info/contact-us";
    }



    public void websiteQueryReceived(ContactMessage message) {
        log.info("<-- websiteQueryReceived --> message: \n {}", message.printContactMessage());

        // publish event
        emailEventPublisher.publishWebsiteQueryReceivedEvent(message);

        log.info("emailEventPublisher published for: {}",message.printContactMessage());

    }
}
