package za.co.recruitmentzone.communication.entity;


import org.springframework.beans.factory.annotation.Value;

public class AdminContactMessage {

    @Value("${event.to.email}")
    String eventToEmail;

    private final String name = "AdminContactMessage";

    private final String messageBody;
    private final String subject;

    public AdminContactMessage(String subject,String messageBody) {
        this.subject = subject;
        this.messageBody = messageBody;

    }

    public String getEventToEmail() {
        return eventToEmail;
    }

    public String getName() {
        return name;
    }

    public String getMessageBody() {
        return messageBody;
    }

    public String getSubject() {
        return subject;
    }


    // printContactMessage

    public String printAdminContactMessage() {
        return "AdminContactMessage{" +
                ", subject='" + subject + '\'' +
                ", messageBody='" + messageBody + '\'' +
                '}';
    }
}
