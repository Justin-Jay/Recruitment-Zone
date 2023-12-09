package za.co.RecruitmentZone.communication.Events.Email;

import org.springframework.context.ApplicationEvent;

public class EmailEvent extends ApplicationEvent{
    private String first_name;

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public EmailEvent(Object source) {
        super(source);
    }
}
