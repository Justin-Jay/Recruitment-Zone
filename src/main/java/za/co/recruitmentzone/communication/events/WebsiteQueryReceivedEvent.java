package za.co.recruitmentzone.communication.events;

import za.co.recruitmentzone.communication.entity.ContactMessage;

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
