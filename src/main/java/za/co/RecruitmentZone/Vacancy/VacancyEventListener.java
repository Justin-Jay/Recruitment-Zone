package za.co.RecruitmentZone.Vacancy;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import za.co.RecruitmentZone.Events.EventPublisherService;

@Component
public class VacancyEventListener {


    private final EventPublisherService eventPublisherService;

    public VacancyEventListener(EventPublisherService eventPublisherService) {
        this.eventPublisherService = eventPublisherService;
    }

    @EventListener
    public void handleVacancyPublishedEvent(VacancyPublishedEvent event) {
        // Perform actions when a vacancy is published
        Long vacancyId = event.getVacancyId();
        // ...
    }

    @EventListener
    public void handleVacancyExpiredEvent(VacancyExpiredEvent event) {
        // Perform actions when a vacancy expires
        Long vacancyId = event.getVacancyId();
        // ...
    }




    @EventListener
    public void onVacancyPublishedEvent(VacancyPublishedEvent event) {
        // Your event handling logic

        // Upload the file using the storage controller
      //  MultipartFile file = event.get// Obtain the file to upload
        //eventPublisherService.uploadFile(file);
    }
    // Create methods to handle other custom events as needed
}
