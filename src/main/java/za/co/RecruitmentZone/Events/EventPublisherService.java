package za.co.RecruitmentZone.Events;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import za.co.RecruitmentZone.Storage.StorageController;
import za.co.RecruitmentZone.Vacancy.VacancyExpiredEvent;
import za.co.RecruitmentZone.Vacancy.VacancyPublishedEvent;

import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

@Service
public class EventPublisherService {

    private final ApplicationEventPublisher eventPublisher;
    private final StorageController storageController;

    @Autowired
    public EventPublisherService(ApplicationEventPublisher eventPublisher, StorageController storageController) {
        this.eventPublisher = eventPublisher;
        this.storageController = storageController;
    }

    public void publishVacancyPublishedEvent(Long vacancyId) {
        VacancyPublishedEvent event = new VacancyPublishedEvent(this, vacancyId);
        eventPublisher.publishEvent(event);
    }

    // Call this method to upload the file during event processing
    public void uploadFile(MultipartFile file) {
        try {
            storageController.uploadFile(file);
        } catch (IOException e) {
            // Handle the exception appropriately
        }
    }
}

