package za.co.RecruitmentZone.entity.domain;

import jakarta.persistence.*;
import org.apache.xmlbeans.impl.xb.xmlconfig.Extensionconfig;

import java.time.LocalDateTime;


public interface Note {
    LocalDateTime getDateCaptured();
    void setDateCaptured(LocalDateTime dateCaptured);
    String getComment();
    void setComment(String comment);
}