package za.co.RecruitmentZone.communication.controller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import za.co.RecruitmentZone.communication.entity.ContactMessage;
import za.co.RecruitmentZone.communication.Events.Email.EmailEventPublisher;
import za.co.RecruitmentZone.service.RecruitmentZoneService;

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
    @GetMapping("/contact-us")
    public String contactus(Model model,@RequestParam(name = "messageSent", defaultValue = "false")boolean messageSent) {
        model.addAttribute("contactMessage",new ContactMessage());
        if (messageSent){
            model.addAttribute("messageSent",Boolean.TRUE);
        }

        return "fragments/info/contact-us";
    }
    @PostMapping("/send-message")
    public String sendMessage(@Valid @ModelAttribute("message")ContactMessage message, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("success",Boolean.FALSE);
            return "fragments/info/contact-us";
        }
        log.info("Website message received");
        websiteQueryReceived(message);
        model.addAttribute("success",Boolean.TRUE);
        return "redirect:/Communication/contact-us";
    }

    // COMMUNICATION

    public void websiteQueryReceived(ContactMessage message) {
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

        log.info("Website Query received");
        log.info(message.toString());
    }
}
