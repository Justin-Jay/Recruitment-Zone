package za.co.recruitmentzone.application.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

import za.co.recruitmentzone.util.enums.ApplicationStatus;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class ApplicationDTO implements Serializable {

    private Long applicationID;
    private String candidateName;
    private String vacancyName;
    private LocalDateTime created;
    private LocalDateTime date_received;
    private LocalDateTime submission_date;
    @Enumerated(EnumType.STRING)
    private ApplicationStatus status;

    public String printApplicationDTO() {
        return "ApplicationDTO{" +
                "applicationID=" + applicationID +
                ", candidateName='" + candidateName + '\'' +
                ", vacancyName='" + vacancyName + '\'' +
                ", created=" + created +
                ", date_received=" + date_received +
                ", submission_date=" + submission_date +
                ", status=" + status +
                '}';
    }

    public String printStatus() {
        switch (status) {
            case OFFER -> {
                return "OFFER";
            }
            case PENDING -> {
                return "PENDING";
            }
            case SHORT_LISTED -> {
                return "SHORT LISTED";
            }

            case INTERVIEW -> {
                return "INTERVIEW";
            }

            case REJECTED -> {
                return "REJECTED";
            }
            default -> {
                return "UNKNOWN";
            }
        }
    }
}
