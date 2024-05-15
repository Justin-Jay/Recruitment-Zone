package za.co.recruitmentzone.communication.events;

import za.co.recruitmentzone.communication.entity.ContactMessage;

public class WebsiteMessageEvent {

    private ContactMessage message;

    public WebsiteMessageEvent(ContactMessage message) {
        this.message = message;
    }

    public ContactMessage getMessage() {
        return message;
    }

    public void setMessage(ContactMessage message) {
        this.message = message;
    }
}
