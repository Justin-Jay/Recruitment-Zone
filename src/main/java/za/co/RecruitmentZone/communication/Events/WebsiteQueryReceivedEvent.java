package za.co.RecruitmentZone.communication.Events;

import za.co.RecruitmentZone.communication.entity.ContactMessage;

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
