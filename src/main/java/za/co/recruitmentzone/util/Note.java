package za.co.recruitmentzone.util;

import java.time.LocalDateTime;


public interface Note {
    LocalDateTime getDateCaptured();
    void setDateCaptured(LocalDateTime dateCaptured);
    String getComment();
    void setComment(String comment);
}