package za.co.recruitmentzone.blog.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;
import za.co.recruitmentzone.util.enums.BlogStatus;
import za.co.recruitmentzone.util.enums.VacancyStatus;

import java.io.Serializable;

@Data
public class BlogStatusDTO implements Serializable {

    private long blogID;

    @Enumerated(EnumType.STRING)
    private BlogStatus status;


    public String printBlogStatusDTO() {
        return "ApplicationDTO{" +
                "blogID=" + blogID +
                ", status=" + status +
                '}';
    }

    public String printStatus() {
        switch (status) {
            case PENDING -> {
                return "PENDING";
            }
            case ACTIVE -> {
                return "ACTIVE";
            }
            case EXPIRED -> {
                return "EXPIRED";
            }
            default -> {
                return "UNKNOWN";
            }
        }
    }
}
