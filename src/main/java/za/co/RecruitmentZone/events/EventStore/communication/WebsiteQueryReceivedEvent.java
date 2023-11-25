package za.co.RecruitmentZone.events.EventStore.communication;

import za.co.RecruitmentZone.entity.domain.ContactMessage;

public class WebsiteQueryReceivedEvent {

    private ContactMessage message;

    public WebsiteQueryReceivedEvent(ContactMessage message) {
        this.message = message;
    }

    public ContactMessage getMessage() {
        return message;
    }

    public void setMessage(ContactMessage message) {
        this.message = message;
    }
}
