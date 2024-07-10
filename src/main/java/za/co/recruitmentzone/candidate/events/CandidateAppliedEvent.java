package za.co.recruitmentzone.candidate.events;

import org.springframework.context.ApplicationEvent;
import za.co.recruitmentzone.application.entity.Application;
import za.co.recruitmentzone.communication.entity.AdminContactMessage;
import za.co.recruitmentzone.communication.entity.ContactMessage;
import za.co.recruitmentzone.communication.events.WebsiteMessageEvent;

import java.time.Clock;

// Define a custom event for candidate application
public class CandidateAppliedEvent extends ApplicationEvent {
    AdminContactMessage adminContactMessage;
    ContactMessage contactMessage;
    private Application application;

    public CandidateAppliedEvent(Application application,AdminContactMessage adminContactMessage, ContactMessage contactMessage) {
        super(application);
        this.application=application;
        this.contactMessage = contactMessage;
        this.adminContactMessage=adminContactMessage;
    }

    // Getters for candidate and resumeFilePath

    public Application getApplication() {
        return application;
    }


    public AdminContactMessage getAdminContactMessage() {
        return adminContactMessage;
    }

    public ContactMessage getContactMessage() {
        return contactMessage;
    }
}


