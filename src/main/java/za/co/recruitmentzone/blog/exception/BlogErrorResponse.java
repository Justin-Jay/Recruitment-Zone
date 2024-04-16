package za.co.recruitmentzone.blog.exception;

import java.time.LocalDateTime;

public class BlogErrorResponse {

    private int status;

    private String message;

    private long timeStamp;

    public BlogErrorResponse() {
    }

    public BlogErrorResponse(int status, String message) {
        this.status = status;
        this.message = message;
        this.timeStamp = Long.parseLong(LocalDateTime.now().toString());
    }

    public BlogErrorResponse(int status, String message, long timeStamp) {
        this.status = status;
        this.message = message;
        this.timeStamp = timeStamp;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }
}
