package za.co.recruitmentzone.vacancy.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;
import za.co.recruitmentzone.util.enums.ApplicationStatus;
import za.co.recruitmentzone.util.enums.VacancyStatus;

import java.io.Serializable;

@Data
public class VacancyStatusDTO implements Serializable {

    private long vacancyID;

    private long totalApplicationCount;

    private String job_title;

    private String publish_date;

    private String end_date;

    private String created;

    private String clientName;

    private String employeeName;

    @Enumerated(EnumType.STRING)
    private VacancyStatus status;


    public VacancyStatusDTO() {
    }

    public VacancyStatusDTO(long vacancyID, VacancyStatus vacancyStatus, String job_title, String publish_date,
                            String end_date, String created, String clientName, String employeeName, long applicationSize) {
        this.vacancyID = vacancyID;
        this.status = vacancyStatus;
        this.job_title = job_title;
        this.publish_date = publish_date;
        this.end_date = end_date;
        this.created = created;
        this.clientName = clientName;
        this.employeeName = employeeName;
        this.totalApplicationCount = applicationSize;
    }


    public String printVacancyStatusDTO() {
        return "ApplicationDTO{" +
                "vacancyID=" + vacancyID +
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
