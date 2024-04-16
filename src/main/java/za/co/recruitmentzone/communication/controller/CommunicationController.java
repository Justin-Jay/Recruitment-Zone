package za.co.recruitmentzone.communication.controller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import za.co.recruitmentzone.communication.entity.ContactMessage;
import za.co.recruitmentzone.communication.events.Email.EmailEventPublisher;
import za.co.recruitmentzone.service.RecruitmentZoneService;

import static za.co.recruitmentzone.util.Constants.ErrorMessages.INTERNAL_SERVER_ERROR;

@Controller
@RequestMapping("/Communication")
public class CommunicationController {
    private final Logger log = LoggerFactory.getLogger(CommunicationController.class);
    private final RecruitmentZoneService recruitmentZoneService;

    private final EmailEventPublisher emailEventPublisher;

    public CommunicationController(RecruitmentZoneService recruitmentZoneService, EmailEventPublisher emailEventPublisher) {
        this.recruitmentZoneService = recruitmentZoneService;
        this.emailEventPublisher = emailEventPublisher;
    }
    // contactMessage , messageSent , internalServerError
    @GetMapping("/contact-us")
    public String contactus(Model model, @ModelAttribute("messageSent") String messageSent,@ModelAttribute("internalServerError") String internalServerError) {
        log.info("<-- contactus --> ");
        model.addAttribute("contactMessage", new ContactMessage());
        model.addAttribute("messageSent", messageSent);
        model.addAttribute("internalServerError", internalServerError);
        return "fragments/info/contact-us";
    }

    @PostMapping("/send-message")
    public String sendMessage(@Valid @ModelAttribute("message") ContactMessage message, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("messageSent", Boolean.FALSE);
            log.info("<-- sendMessage --> model: \n {}", model.asMap().toString());
            return "fragments/info/contact-us";
        }
        try {
            log.info("Website message received");
            websiteQueryReceived(message);
            redirectAttributes.addFlashAttribute("messageSent", "Message has been received successfully");
        } catch (Exception e) {
            log.info(e.getMessage());
            redirectAttributes.addFlashAttribute("internalServerError", INTERNAL_SERVER_ERROR);
        }
        return "redirect:/Communication/contact-us";
    }

    // COMMUNICATION

    public void websiteQueryReceived(ContactMessage message) {
        log.info("<-- websiteQueryReceived --> message: \n {}", message);
        // send message using virtual thread
       /* try (ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()) {
            log.info("About to submit");
            executor.submit(() -> {
                // Send website query received notification
                // Perform repo IO operation
              //  communicationService.sendSimpleEmail(message);
                communicationService.sendWebsiteQuery(message);
            });
        } catch (Exception e) {
            log.info("Failed to send Email Query");
        }*/
        // Send Acknowledgment to candidate
        // publish event
        emailEventPublisher.publishWebsiteQueryReceivedEvent(message);

        log.info("emailEventPublisher published for: {}",message);

    }
}
