package za.co.recruitmentzone.communication.events;

import za.co.recruitmentzone.communication.entity.AdminContactMessage;
public class AdminMessageEvent {

    private AdminContactMessage message;

    public AdminMessageEvent(AdminContactMessage message) {
        this.message = message;
    }

    public AdminContactMessage getMessage() {
        return message;
    }

    public void setMessage(AdminContactMessage message) {
        this.message = message;
    }
}
