package za.co.RecruitmentZone.entity.domain;

import jakarta.persistence.*;
import za.co.RecruitmentZone.entity.Enums.ApplicationStatus;

@Entity
@Table(name = "application")
public class Application {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long applicationID;
    private String date_received;

    public Application() {
    }

    public Application(String date_received) {
        this.date_received = date_received;
    }

    public Long getApplicationID() {
        return applicationID;
    }

    public String getDate_received() {
        return date_received;
    }

    public void setDate_received(String date_received) {
        this.date_received = date_received;
    }

    @Override
    public String toString() {
        return "Application{" +
                "date_received='" + date_received + '\'' +
                '}';
    }
}


