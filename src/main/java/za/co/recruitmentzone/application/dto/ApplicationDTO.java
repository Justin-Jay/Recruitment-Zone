package za.co.recruitmentzone.application.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.web.multipart.MultipartFile;
import za.co.recruitmentzone.util.enums.ApplicationStatus;
import za.co.recruitmentzone.util.enums.EducationLevel;
import za.co.recruitmentzone.util.enums.Province;

import java.io.Serializable;

public class ApplicationDTO implements Serializable {

    private Long applicationID;


    @Enumerated(EnumType.STRING)
    private ApplicationStatus status;

    public ApplicationDTO() {
    }


    public Long getApplicationID() {
        return applicationID;
    }

    public ApplicationStatus getStatus() {
        return status;
    }

    public void setApplicationID(Long applicationID) {
        this.applicationID = applicationID;
    }

    public void setStatus(ApplicationStatus status) {
        this.status = status;
    }

    public String printApplicationDTO() {
        return "ApplicationDTO{" +
                "applicationID=" + applicationID +
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
