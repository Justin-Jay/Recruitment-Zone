package za.co.RecruitmentZone.entity.domain;

public class ContactMessage {
    private String name;

    private String email;

    private String messageBody;

    private final String toEmail = "justin.maboshego@gmail.com";
    String subject;

    public ContactMessage() {
    }

    public ContactMessage(String name, String email, String messageBody) {
        this.name = name;
        this.email = email;
        this.messageBody = messageBody;
    }
    public String getToEmail() {
        return toEmail;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMessageBody() {
        return messageBody;
    }

    public void setMessageBody(String messageBody) {
        this.messageBody = messageBody;
    }

    @Override
    public String toString() {
        return "ContactMessage{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", messageBody='" + messageBody + '\'' +
                '}';
    }
}
